package com.qc.business.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qc.business.domain.Provider;
import com.qc.business.service.IProviderService;
import com.qc.business.vo.ProviderVo;
import com.qc.system.utils.DataGridView;
import com.qc.system.utils.ResultObj;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("provider")
public class ProviderController {

	@Autowired
	private IProviderService providerService;

	/**
	 * 查询
	 */
	@RequestMapping("loadAllProvider")
	public DataGridView loadAllProvider(ProviderVo providerVo) {
		return this.providerService.queryAllProvider(providerVo);
	}

	/**
	 * 添加
	 */
	@RequestMapping("addProvider")
	public ResultObj addProvider(Provider provider) {
		try {
			this.providerService.addProvider(provider);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}

	/**
	 * 修改
	 */
	@RequestMapping("updateProvider")
	public ResultObj updateProvider(Provider provider) {
		try {
			this.providerService.updateProvider(provider);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping("deleteProvider")
	public ResultObj deleteProvider(Integer id) {
		try {
			this.providerService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}

	/**
	 * 批量删除
	 */
	@RequestMapping("batchDeleteProvider")
	public ResultObj batchDeleteProvider(Integer[] ids) {
		try {
//			Collection<Serializable> idList = new ArrayList<Serializable>();
			for (Integer id : ids) {
//				idList.add(id);
				this.providerService.removeById(id);
			}
//			this.providerService.removeByIds(idList);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
	
	/**
	 * 加载可用的供应商不分页
	 */
	@RequestMapping("loadAllAvailableProvider")
	public DataGridView loadAllAvailableProvider() {
		return this.providerService.queryAllAvailableProvider();
	}

}

