package com.qc.business.controller;


import com.qc.business.domain.Sales;
import com.qc.business.service.SalesService;
import com.qc.business.vo.SalesVo;
import com.qc.system.domain.User;
import com.qc.system.utils.DataGridView;
import com.qc.system.utils.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 唐颖豪
 * @since 2019-11-16
 */
@RestController
@RequestMapping("sales")
public class SalesController {

	
	@Autowired
	private SalesService salesService;
	
	/**
	 * 全查询
	 */
	@RequestMapping("loadAllSales")
	public DataGridView loadAllSales(SalesVo salesVo) {
		return this.salesService.queryAllSales(salesVo);
	}
	
	/**
	 * 添加
	 */
	@RequestMapping("addSales")
	public ResultObj addSales(Sales sales,HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			sales.setOperateperson(user.getName());
			sales.setSalestime(new Date());
			this.salesService.addSales(sales);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}

	/**
	 * 修改
	 */
	@RequestMapping("updateSales")
	public ResultObj updateSales(Sales sales) {
		try {
			this.salesService.updateSales(sales);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("deleteSales")
	public ResultObj deleteSales(Integer id) {
		try {
			this.salesService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
}

