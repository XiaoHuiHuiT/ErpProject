package com.qc.business.service.impl;

import com.qc.business.domain.Goods;
import com.qc.business.domain.Inport;
import com.qc.business.domain.Outport;
import com.qc.business.mapper.GoodsMapper;
import com.qc.business.mapper.InportMapper;
import com.qc.business.mapper.OutportMapper;
import com.qc.business.service.IGoodsService;
import com.qc.business.service.IOutportService;
import com.qc.business.service.IProviderService;
import com.qc.business.vo.OutportVo;
import com.qc.system.utils.DataGridView;
import com.qc.business.utils.WebAppUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
public class OutportServiceImpl extends ServiceImpl<OutportMapper, Outport> implements IOutportService {

	@Autowired
	private InportMapper inportMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Override
	public void addOutport(Outport outport) {
		Integer inportid=outport.getInportid();
		//根据进货单ID查询进货信息
		Inport inport=inportMapper.selectById(inportid);
		//向outport里面封装数据
		outport.setGoodsid(inport.getGoodsid());
		outport.setOutportprice(inport.getInportprice());
		outport.setPaytype(inport.getPaytype());
		outport.setProviderid(inport.getProviderid());
		//保存退货数据
		this.getBaseMapper().insert(outport);
		
		//更新进货单 的数据
		inport.setNumber(inport.getNumber()-outport.getNumber());
		inportMapper.updateById(inport);
		
		//更新库存
		Goods goods = goodsMapper.selectById(inport.getGoodsid());
		goods.setNumber(goods.getNumber()-outport.getNumber());
		this.goodsMapper.updateById(goods);
	}

	@Override
	public Integer queryOutportCountByInportId(Integer inportid) {
		return this.getBaseMapper().queryOutportCountByInportId(inportid);
	}

	@Override
	public DataGridView queryAllOutport(OutportVo outportVo) {
		Page<Outport> page=new Page<>(outportVo.getPage(), outportVo.getLimit());
		
		QueryWrapper<Outport> qw=new QueryWrapper<>();
		qw.eq(outportVo.getProviderid()!=null, "providerid", outportVo.getProviderid());
		qw.eq(outportVo.getGoodsid()!=null, "goodsid", outportVo.getGoodsid());
		qw.ge(null!=outportVo.getStartTime(), "outporttime", outportVo.getStartTime());
		qw.le(null!=outportVo.getEndTime(), "outporttime", outportVo.getEndTime());
		qw.orderByDesc("outporttime");
		this.getBaseMapper().selectPage(page, qw);
		List<Outport> list = page.getRecords();
		for (Outport outport : list) {
			Integer providerid = outport.getProviderid();
			Integer goodsid = outport.getGoodsid();
			if(null!=providerid) {
				outport.setProvidername(WebAppUtils.getContext().getBean(IProviderService.class).getById(providerid).getProvidername());
			}
			if(null!=goodsid) {
				Goods goods = WebAppUtils.getContext().getBean(IGoodsService.class).getById(goodsid);
				outport.setGoodsname(goods.getGoodsname());
				outport.setSize(goods.getSize());
			}
		}
		return new DataGridView(page.getTotal(), list);
	}

}
