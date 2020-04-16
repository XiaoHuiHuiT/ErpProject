package com.qc.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qc.business.domain.Goods;
import com.qc.business.service.IGoodsService;
import com.qc.business.vo.GoodsVo;
import com.qc.system.utils.DataGridView;
import com.qc.system.utils.ResultObj;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 唐颖豪
 * @since 2019-11-15
 */
@RestController
@RequestMapping("goods")
public class GoodsController {

	@Autowired
	private IGoodsService goodsService;

	/**
	 * 查询
	 */
	@RequestMapping("loadAllGoods")
	public DataGridView loadAllGoods(GoodsVo goodsVo) {
		return this.goodsService.queryAllGoods(goodsVo);
	}

	/**
	 * 添加
	 */
	@RequestMapping("addGoods")
	public ResultObj addGoods(Goods goods) {
		try {
			this.goodsService.addGoods(goods);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}

	/**
	 * 修改
	 */
	@RequestMapping("updateGoods")
	public ResultObj updateGoods(Goods goods) {
		try {
			this.goodsService.updateGoods(goods);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping("deleteGoods")
	public ResultObj deleteGoods(Integer id) {
		try {
			this.goodsService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}

	/**
	 * 批量删除
	 */
	@RequestMapping("batchDeleteGoods")
	public ResultObj batchDeleteGoods(Integer[] ids) {
		try {
			// Collection<Serializable> idList = new ArrayList<Serializable>();
			for (Integer id : ids) {
				// idList.add(id);
				this.goodsService.removeById(id);
			}
			// this.goodsService.removeByIds(idList);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}

	/**
	 * 加载可用的商品不分页
	 */
	@RequestMapping("loadAllAvailableGoods")
	public DataGridView loadAllAvailableGoods() {
		return this.goodsService.queryAllAvailableGoods();
	}

	/**
	 * 根据供应商ID查询商品
	 */
	@RequestMapping("loadGoodsByProviderid")
	public DataGridView loadGoodsByProviderid(Integer providerid) {
		return this.goodsService.queryGoodsByProviderid(providerid);
	}

	/**
	 * 加载库存不够的商品
	 * @return
	 */
	@RequestMapping("loadGoodsWarningCount")
	public Object loadGoodsWarningCount(){
		return this.goodsService.loadGoodsWarningCount();
	}

}
