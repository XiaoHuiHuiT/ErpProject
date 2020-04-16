package com.qc.business.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 唐颖豪
 * @since 2019-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_inport")
public class Inport implements Serializable {
    private static final long serialVersionUID=1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String paytype;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date inporttime;
    private String operateperson;
    private Integer number;
    private String remark;
    private Double inportprice;
    private Integer providerid;
    private Integer goodsid;
    @TableField(exist=false)
    private String providername;
    @TableField(exist=false)
    private String goodsname;
    @TableField(exist=false)
    private String size;
}