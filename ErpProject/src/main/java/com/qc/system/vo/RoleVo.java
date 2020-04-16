package com.qc.system.vo;

import com.qc.system.domain.Dept;
import com.qc.system.domain.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleVo extends Role implements Serializable {
    private static final long seriaVersionUID = 1L;
    /**
     * 分页参数
     */
    private Integer page = 1;
    private Integer limit = 10;
}
