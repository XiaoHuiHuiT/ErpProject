package com.qc.statistical.service;

import com.qc.statistical.domain.BaseEntity;

import java.util.List;

/**
 * 统计分析数据服务接口
 */
public interface StatService {

    /**
     * 加载客户地区数据
     * @return
     */
    List<BaseEntity> loadCustomerAreaStatList();

    /**
     * 加载客户年度销售额统计
     * @return
     */
    List<BaseEntity> loadCustomerYearGradeStatList(String yaer);

    /**
     * 查询客户年度月份销售额
     * @param year
     * @return
     */
    List<Double> loadCustomerMethodStat(String year);
}
