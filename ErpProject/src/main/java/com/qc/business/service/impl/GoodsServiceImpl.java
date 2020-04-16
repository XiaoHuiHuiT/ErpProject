package com.qc.business.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.qc.business.domain.Inport;
import com.qc.business.mapper.InportMapper;
import com.qc.business.service.IInportService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qc.business.domain.Goods;
import com.qc.business.domain.Provider;
import com.qc.business.mapper.GoodsMapper;
import com.qc.business.service.IGoodsService;
import com.qc.business.service.IProviderService;
import com.qc.business.vo.GoodsVo;
import com.qc.system.constant.Constant;
import com.qc.system.utils.DataGridView;
import com.qc.system.utils.FileUploadAndDownUtil;
import com.qc.business.utils.WebAppUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 唐颖豪
 * @since
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService{

	@SuppressWarnings("unused")
	private Log log=LogFactory.getLog(GoodsServiceImpl.class);

	@Autowired
	private InportMapper inportMapper;
	
	@Override
	public DataGridView queryAllGoods(GoodsVo goodsVo) {
		
		Page<Goods> page=new Page<>(goodsVo.getPage(), goodsVo.getLimit());
		
		QueryWrapper<Goods> qw=new QueryWrapper<>();
		qw.eq(goodsVo.getProviderid()!=null, "providerid", goodsVo.getProviderid());
		qw.like(goodsVo.getGoodsname()!=null, "goodsname", goodsVo.getGoodsname());
		qw.like(goodsVo.getProductcode()!=null, "productcode", goodsVo.getProductcode());
		qw.like(goodsVo.getPromitcode()!=null, "promitcode", goodsVo.getPromitcode());
		qw.like(goodsVo.getDescription()!=null, "description", goodsVo.getDescription());
		qw.like(goodsVo.getSize()!=null, "size", goodsVo.getSize());
		this.getBaseMapper().selectPage(page, qw);
		List<Goods> list = page.getRecords();
		for (Goods goods : list) {
			Integer prodiverid=goods.getProviderid();
			if(null!=prodiverid) {
				Provider provider = WebAppUtils.getContext().getBean(IProviderService.class).getById(prodiverid);
				goods.setProvidername(provider.getProvidername());
			}	
		}
		return new DataGridView(page.getTotal(), list);
	}

	@Override
	public Goods getById(Serializable id) {
		return super.getById(id);
	}
	
	
	@Value("${upload.upload-root-path}")
	private String uploadRootPath="E:/upload";
	
	@Override
	public Goods addGoods(Goods goods) {
		//1,处理图片
		String imgpath=goods.getGoodsimg();
		if(!imgpath.equals(Constant.GOODS_DEFAULT_IMAGE)) {
			if(imgpath.endsWith("_temp")) {
				String path=FileUploadAndDownUtil.changeFileName(uploadRootPath,imgpath);
				goods.setGoodsimg(path);
			}
		}
		this.getBaseMapper().insert(goods);
		return goods;
	}

	@Override
	public Goods updateGoods(Goods goods) {
		//1,处理图片
		String imgpath=goods.getGoodsimg();
		if(!imgpath.equals(Constant.GOODS_DEFAULT_IMAGE)) {
			if(imgpath.endsWith("_temp")) {
				String path=FileUploadAndDownUtil.changeFileName(uploadRootPath,imgpath);
				goods.setGoodsimg(path);
				//删除老图片
				Goods oldGoods=this.getBaseMapper().selectById(goods.getId());
				FileUploadAndDownUtil.deleteFile(uploadRootPath,oldGoods.getGoodsimg());
			}
		}
		this.getBaseMapper().updateById(goods);
		return goods;
	}

	
	@Override
	public boolean removeById(Serializable id) {
		//删除图片
		Goods goods=this.getBaseMapper().selectById(id);
		String imgpath=goods.getGoodsimg();
		if(!imgpath.equals(Constant.GOODS_DEFAULT_IMAGE)) {
			//删除图片
			FileUploadAndDownUtil.deleteFile(uploadRootPath,goods.getGoodsimg());
		}
		return super.removeById(id);
	}

	@Override
	public DataGridView queryAllAvailableGoods() {
		QueryWrapper<Goods> qw=new QueryWrapper<>();
		qw.eq("available", Constant.AVAILABLE_TRUE);
		return new DataGridView(this.getBaseMapper().selectList(qw));
	}

	@Override
	public DataGridView queryGoodsByProviderid(Integer providerid) {
		QueryWrapper<Goods> qw=new QueryWrapper<>();
		qw.eq("available", Constant.AVAILABLE_TRUE);
		qw.eq("providerid", providerid);
		return new DataGridView(this.getBaseMapper().selectList(qw));
	}

	/***
	 * 加载库存不够的商品
	 * @return
	 */
	@Override
	public DataGridView loadGoodsWarningCount() {
		return new DataGridView(this.getBaseMapper().loadGoodsWarningCount());
	}


}
