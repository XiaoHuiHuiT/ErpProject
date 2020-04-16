package com.qc.business.vo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.qc.business.domain.Inport;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class InportVo extends Inport implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 分页参数
	 */
	private Integer page=1;
	private Integer limit=10;
	
	/**
	 * 查询条件
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;

}
