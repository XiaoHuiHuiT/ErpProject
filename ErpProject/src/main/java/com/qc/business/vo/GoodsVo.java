package com.qc.business.vo;

import com.qc.business.domain.Goods;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class GoodsVo extends Goods  implements Serializable {

    private static final long seriaVersionUID = 1L;
    /**
     * 分页参数
     */
    private Integer page = 1;
    private Integer limit = 10;

}
