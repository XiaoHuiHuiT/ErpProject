package com.qc.business.controller;


import com.qc.business.domain.Salesback;
import com.qc.business.service.SalesbackService;
import com.qc.business.vo.SalesbackVo;
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
 */
@RestController
@RequestMapping("salesback")
public class SalesBackController {
	
	
	@Autowired
	private SalesbackService salesbackService;
	
	/**
	 * 保存退货信息
	 */
	@RequestMapping("addSalesback")
	public ResultObj addSalesback(Salesback salesback,HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			salesback.setOperateperson(user.getName());
			salesback.setSalesbacktime(new Date());
			this.salesbackService.addSalesback(salesback);
			return ResultObj.REFUND_TRUE;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.REFUND_ERROR;
		}
	}

	/**
	 * 全查询退货信息
	 */
	@RequestMapping("loadAllSalesback")
	public DataGridView loadAllSalesback(SalesbackVo salesbackVo) {
		return this.salesbackService.queryAllSalesback(salesbackVo);
	}
	

}

