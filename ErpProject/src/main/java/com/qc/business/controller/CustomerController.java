package com.qc.business.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qc.business.domain.Customer;
import com.qc.business.service.ICustomerService;
import com.qc.business.vo.CustomerVo;
import com.qc.system.utils.DataGridView;
import com.qc.system.utils.ResultObj;

/**
 * <p>
 * 前端控制器
 * </p>
 */
@RestController
@RequestMapping("customer")
public class CustomerController {

	@Autowired
	private ICustomerService customerService;

	/**
	 * 查询
	 */
	@RequestMapping("loadAllCustomer")
	public DataGridView loadAllCustomer(CustomerVo customerVo) {
		return this.customerService.queryAllCustomer(customerVo);
	}

	/**
	 * 添加
	 */
	@RequestMapping("addCustomer")
	public ResultObj addCustomer(Customer customer) {
		try {
			this.customerService.addCustomer(customer);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}

	/**
	 * 修改
	 */
	@RequestMapping("updateCustomer")
	public ResultObj updateCustomer(Customer customer) {
		try {
			this.customerService.updateCustomer(customer);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping("deleteCustomer")
	public ResultObj deleteCustomer(Integer id) {
		try {
			this.customerService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}

	/**
	 * 批量删除
	 */
	@RequestMapping("batchDeleteCustomer")
	public ResultObj batchDeleteCustomer(Integer[] ids) {
		try {
			Collection<Serializable> idList = new ArrayList<Serializable>();
			for (Integer id : ids) {
				idList.add(id);
			}
			this.customerService.removeByIds(idList);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}

	/**
	 * 查询所有可用的客户
	 * @return
	 */
	@RequestMapping("loadAllAvailableCustomer")
	public Object loadAllAvailableCustomer(){
		return this.customerService.loadAllAvailableCustomer();
	}

}
