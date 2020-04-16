package com.qc.business.vo;

import com.qc.business.domain.Outport;
import com.qc.business.domain.Salesback;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class SalesbackVo extends Salesback implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 分页参数
	 */
	private Integer page=1;
	private Integer limit=10;

	private Integer customerid;
	private Integer goodsid;
	
	/**
	 * 查询条件
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;

}
