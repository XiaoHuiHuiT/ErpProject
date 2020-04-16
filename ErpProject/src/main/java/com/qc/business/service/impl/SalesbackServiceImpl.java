package com.qc.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qc.business.domain.*;
import com.qc.business.mapper.SalesMapper;
import com.qc.business.service.ICustomerService;
import com.qc.business.service.IGoodsService;
import com.qc.business.service.IProviderService;
import com.qc.business.utils.WebAppUtils;
import com.qc.business.vo.SalesbackVo;
import com.qc.system.utils.ActiverUser;
import com.qc.system.utils.DataGridView;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qc.business.mapper.SalesbackMapper;
import com.qc.business.service.SalesbackService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SalesbackServiceImpl extends ServiceImpl<SalesbackMapper, Salesback> implements SalesbackService{

    @Autowired
    private SalesMapper salesMapper;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private SalesbackMapper salesbackMapper;

    @Override
    public Salesback addSalesback(Salesback salesback) {
        Integer salesid= salesback.getSalesid();

        //根据进货单ID查询进货信息
        Sales sales = this.salesMapper.selectById(salesid);

        //向outport里面封装数据
        salesback.setGoodsid(sales.getGoodsid());// 商品id
        salesback.setPaytype(sales.getPaytype());// 支付类型
        ActiverUser activerUser = (ActiverUser)SecurityUtils.getSubject().getPrincipal();

        salesback.setSalesbacktime(new Date());
        salesback.setSalebackprice(sales.getSaleprice());// 价格
        salesback.setOperateperson(activerUser.getUser().getName());// 操作人
        salesback.setCustomerid(sales.getCustomerid());// 客户id

        //保存退货数据
        this.salesbackMapper.insert(salesback);

        //添加库存
        Goods goods = this.goodsService.getById(sales.getGoodsid());
        goods.setNumber(goods.getNumber() + salesback.getNumber());
        this.goodsService.updateById(goods);

        // 更新销售单信息  (原来购买的数量 - 退货的数量)
        sales.setNumber(sales.getNumber() - salesback.getNumber());
        this.salesMapper.updateById(sales);

        return salesback;
    }

    @Override
    public DataGridView queryAllSalesback(SalesbackVo salesbackVo) {
        Page<Salesback> page=new Page<>(salesbackVo.getPage(), salesbackVo.getLimit());

        QueryWrapper<Salesback> qw=new QueryWrapper<>();
        qw.eq(salesbackVo.getCustomerid()!=null, "customerid", salesbackVo.getCustomerid());
        qw.eq(salesbackVo.getGoodsid()!=null, "goodsid", salesbackVo.getGoodsid());

        qw.ge(null!=salesbackVo.getStartTime(), "salesbacktime", salesbackVo.getStartTime());
        qw.le(null!=salesbackVo.getEndTime(), "salesbacktime", salesbackVo.getEndTime());

        qw.orderByDesc("salesbacktime");

        this.salesbackMapper.selectPage(page, qw);

        List<Salesback> list = page.getRecords();
        for (Salesback salesback : list) {
            Integer customerid = salesback.getCustomerid();
            Integer goodsid = salesback.getGoodsid();
            if(null!=customerid) {
                salesback.setCustomername(WebAppUtils.getContext().getBean(ICustomerService.class).getById(customerid).getCustomername());
            }
            if(null!=goodsid) {
                Goods goods = WebAppUtils.getContext().getBean(IGoodsService.class).getById(goodsid);
                salesback.setGoodsname(goods.getGoodsname());
                salesback.setSize(goods.getSize());
            }
        }
        return new DataGridView(page.getTotal(), list);
    }
}
