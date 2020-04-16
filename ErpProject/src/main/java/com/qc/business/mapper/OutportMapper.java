package com.qc.business.mapper;

import com.qc.business.domain.Outport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 */
public interface OutportMapper extends BaseMapper<Outport> {

	/**
	 * 
	 * @param inportid
	 * @return
	 */
	Integer queryOutportCountByInportId(Integer inportid);

}
