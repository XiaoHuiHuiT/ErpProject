package com.qc.business.vo;

import com.qc.business.domain.Inport;
import com.qc.business.domain.Sales;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class SalesVo extends Sales implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer customerid;
	private Integer goodsid;

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
