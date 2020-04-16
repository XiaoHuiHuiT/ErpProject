package com.qc.business.controller;


import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qc.business.domain.Outport;
import com.qc.business.service.IOutportService;
import com.qc.business.vo.OutportVo;
import com.qc.system.domain.User;
import com.qc.system.utils.DataGridView;
import com.qc.system.utils.ResultObj;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@RestController
@RequestMapping("outport")
public class OutportController {
	
	
	@Autowired
	private IOutportService outportService;
	
	/**
	 * 保存退货信息
	 */
	@RequestMapping("addOutport")
	public ResultObj addOutport(Outport outport,HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			outport.setOperateperson(user.getName());
			outport.setOutporttime(new Date());
			this.outportService.addOutport(outport);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}
	
	
	/**
	 * 根据进货单id查询退货单的数量 
	 */
	@RequestMapping("loadOutportByInportId")
	public DataGridView loadOutportByInportId(Integer inportid) {
		Integer count=this.outportService.queryOutportCountByInportId(inportid);
		return new DataGridView(count);
	}
	
	
	/**
	 * 全查询退货信息
	 */
	@RequestMapping("loadAllOutport")
	public DataGridView loadAllOutport(OutportVo outportVo) {
		return this.outportService.queryAllOutport(outportVo);
	}
	

}

