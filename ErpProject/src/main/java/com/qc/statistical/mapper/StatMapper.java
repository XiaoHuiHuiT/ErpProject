package com.qc.statistical.mapper;

import com.qc.statistical.domain.BaseEntity;

import java.util.List;

public interface StatMapper {

    /**
     * 加载客户地区数据
     * @return
     */
    List<BaseEntity> queryCustomerAreaStat();

    /**
     * 加载客户年度销售额统计业绩
     * @return
     */
    List<BaseEntity> queryCustomerYearGradeStat(String year);

    /**
     * 查询客户年度月份销售额
     * @param year
     * @return
     */
    List<Double> loadCustomerMethodStat(String year);
}