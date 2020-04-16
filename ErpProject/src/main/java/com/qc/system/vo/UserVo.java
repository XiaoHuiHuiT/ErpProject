package com.qc.system.vo;

import com.qc.system.domain.Role;
import com.qc.system.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserVo extends User implements Serializable {
    private static final long seriaVersionUID = 1L;
    /**
     * 分页参数
     */
    private Integer page = 1;
    private Integer limit = 10;
}
