package com.qc.system.vo;

import com.qc.system.domain.Dept;
import com.qc.system.domain.Loginfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeptVo extends Dept implements Serializable {
    private static final long seriaVersionUID = 1L;
    /**
     * 分页参数
     */
    private Integer page = 1;
    private Integer limit = 10;
}
