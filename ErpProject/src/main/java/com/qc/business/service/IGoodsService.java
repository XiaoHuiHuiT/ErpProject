package com.qc.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qc.business.domain.Goods;
import com.qc.business.vo.GoodsVo;
import com.qc.system.utils.DataGridView;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 唐颖豪
 * @since 
 */
public interface IGoodsService extends IService<Goods> {

	/**
	 * 查询商品
	 */
	public DataGridView queryAllGoods(GoodsVo goodsVo);

	public Goods addGoods(Goods goods);

	public Goods updateGoods(Goods goods);

	/**
	 * 加载可用的商品
	 * @return
	 */
	public DataGridView queryAllAvailableGoods();

	/**
	 * 根据供应商ID查询商品
	 * @param providerid
	 * @return
	 */
	public DataGridView queryGoodsByProviderid(Integer providerid);

	/***
	 * 加载库存不够的商品
	 * @return
	 */
    DataGridView loadGoodsWarningCount();

}
