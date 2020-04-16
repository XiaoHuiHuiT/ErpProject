package com.qc.business.controller;


import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qc.business.domain.Inport;
import com.qc.business.service.IInportService;
import com.qc.business.vo.InportVo;
import com.qc.system.domain.User;
import com.qc.system.utils.DataGridView;
import com.qc.system.utils.ResultObj;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 唐颖豪
 * @since 2019-11-16
 */
@RestController
@RequestMapping("inport")
public class InportController {

	
	@Autowired
	private IInportService inportService;
	
	/**
	 * 全查询
	 */
	@RequestMapping("loadAllInport")
	public DataGridView loadAllInport(InportVo inportVo) {
		return this.inportService.queryAllInport(inportVo);
	}
	
	/**
	 * 添加
	 */
	@RequestMapping("addInport")
	public ResultObj addInport(Inport inport,HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			inport.setOperateperson(user.getName());
			inport.setInporttime(new Date());
			this.inportService.addInport(inport);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}

	/**
	 * 修改
	 */
	@RequestMapping("updateInport")
	public ResultObj updateInport(Inport inport) {
		try {
			this.inportService.updateInport(inport);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("deleteInport")
	public ResultObj deleteInport(Integer id) {
		try {
			this.inportService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
}

