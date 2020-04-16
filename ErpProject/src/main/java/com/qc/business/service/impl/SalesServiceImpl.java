package com.qc.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qc.business.domain.Customer;
import com.qc.business.domain.Goods;
import com.qc.business.domain.Sales;
import com.qc.business.service.ICustomerService;
import com.qc.business.service.IGoodsService;
import com.qc.business.service.IProviderService;
import com.qc.business.utils.WebAppUtils;
import com.qc.business.vo.SalesVo;
import com.qc.system.utils.DataGridView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qc.business.mapper.SalesMapper;
import com.qc.business.domain.Sales;
import com.qc.business.service.SalesService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional
public class SalesServiceImpl extends ServiceImpl<SalesMapper, Sales> implements SalesService {

    @Autowired
    private IGoodsService iGoodsService;

    @Autowired
    private ICustomerService iCustomerService;

    @Override
    public DataGridView queryAllSales(SalesVo salesVo) {
        Page<Sales> page=new Page<>(salesVo.getPage(), salesVo.getLimit());

        QueryWrapper<Sales> qw=new QueryWrapper<>();

        qw.eq(salesVo.getGoodsid()!=null, "goodsid", salesVo.getGoodsid());
        qw.eq(salesVo.getCustomerid()!=null, "customerid", salesVo.getCustomerid());

        qw.ge(null!=salesVo.getStartTime(), "salestime", salesVo.getStartTime());
        qw.le(null!=salesVo.getEndTime(), "salestime", salesVo.getEndTime());

        qw.orderByDesc("salestime");

        this.getBaseMapper().selectPage(page, qw);
        List<Sales> list = page.getRecords();
        for (Sales sales : list) {
           if(null != sales.getGoodsid()){
               Goods goods = this.iGoodsService.getById(sales.getGoodsid());
               sales.setGoodsname(goods.getGoodsname());
               sales.setSize(goods.getSize());
           }
           if(null != sales.getCustomerid()){
               Customer customer = this.iCustomerService.getById(sales.getCustomerid());
               sales.setCustomername(customer.getCustomername());
           }
        }

        return new DataGridView(page.getTotal(), list);
    }

    @Override
    public Sales addSales(Sales sales) {
        //添加进货单
        this.baseMapper.insert(sales);
        //增加商品库存
        IGoodsService iGoodsService = WebAppUtils.getContext().getBean(IGoodsService.class);
        Goods goods=iGoodsService.getById(sales.getGoodsid());
        goods.setNumber(goods.getNumber() - sales.getNumber());
        iGoodsService.updateById(goods);
        return sales;
    }

    @Override
    public Sales updateSales(Sales sales) {
        //老进货单位      //修改之后的进货单      //商品原来的库存0
//		100                              100 X
//		100Y				20            20Z                Z=100-100+20
        Sales oldSales=this.getBaseMapper().selectById(sales.getId());
        IGoodsService iGoodsService = WebAppUtils.getContext().getBean(IGoodsService.class);
        Goods goods=iGoodsService.getById(sales.getGoodsid());
        //总库存-之前添加的库存 +新修改之后的数量
        goods.setNumber(goods.getNumber()+oldSales.getNumber()-sales.getNumber());
        iGoodsService.updateById(goods);

        this.getBaseMapper().updateById(sales);

        return sales;
    }

    @Override
    public boolean removeById(Serializable id) {
        Sales sales=this.getBaseMapper().selectById(id);
        IGoodsService iGoodsService = WebAppUtils.getContext().getBean(IGoodsService.class);
        Goods goods=iGoodsService.getById(sales.getGoodsid());
        goods.setNumber(goods.getNumber()+sales.getNumber());
        iGoodsService.updateById(goods);
        return super.removeById(id);
    }

}

