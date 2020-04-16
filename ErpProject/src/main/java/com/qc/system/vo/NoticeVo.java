package com.qc.system.vo;

import com.qc.system.domain.Notice;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeVo extends Notice implements Serializable {
    private static final long seriaVersionUID = 1L;

    /**
     * 分页参数
     */
    private Integer page = 1;
    private Integer limit = 10;

    /**
     * 查询条件
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}