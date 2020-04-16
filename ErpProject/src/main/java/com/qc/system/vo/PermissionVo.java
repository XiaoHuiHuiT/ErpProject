package com.qc.system.vo;

import com.qc.system.domain.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionVo extends Permission {

    private static final long seriaVersionUID = 1L;

    /**
     * 分页参数
     */
    private Integer page = 1;
    private Integer limit = 10;
}