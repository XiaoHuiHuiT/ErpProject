package com.qc.business.service;

import com.qc.business.domain.Outport;
import com.qc.business.vo.OutportVo;
import com.qc.system.utils.DataGridView;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface IOutportService extends IService<Outport> {

	/**
	 * 保存退货信息
	 * @param outport
	 */
	void addOutport(Outport outport);

	/**
	 * 根据进货单id查询退货单的数量 
	 * @param inportid
	 * @return
	 */
	Integer queryOutportCountByInportId(Integer inportid);

	/**
	 * 查询退货信息
	 * @param outportVo
	 * @return
	 */
	DataGridView queryAllOutport(OutportVo outportVo);

}
