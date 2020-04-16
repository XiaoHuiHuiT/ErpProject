package com.qc.business.service;

import com.qc.business.domain.Inport;
import com.qc.business.vo.InportVo;
import com.qc.system.utils.DataGridView;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 唐颖豪
 * @since 2019-11-16
 */
public interface IInportService extends IService<Inport> {

	
	/**
	 * 全查询进货信息
	 */
	public DataGridView queryAllInport(InportVo inportVo);
	
	/**
	 * 添加
	 * @param inport
	 */
	public Inport addInport(Inport inport);
	
	/**
	 * 修改
	 * @param inport
	 */
	public Inport updateInport(Inport inport);
}
