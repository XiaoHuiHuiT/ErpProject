package com.qc.statistical.service.Impl;

import com.qc.statistical.domain.BaseEntity;
import com.qc.statistical.mapper.StatMapper;
import com.qc.statistical.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StatServiceImpl implements StatService {

    @Autowired
    private StatMapper statMapper;

    /**
     * 加载客户地区数据
     * @return
     */
    @Override
    public List<BaseEntity> loadCustomerAreaStatList() {
        return this.statMapper.queryCustomerAreaStat();
    }

    /**
     * 加载客户年度销售额统计
     * @param yaer
     * @return
     */
    @Override
    public List<BaseEntity> loadCustomerYearGradeStatList(String yaer) {
        return this.statMapper.queryCustomerYearGradeStat(yaer);
    }

    /**
     * 查询客户年度月份销售额
     * @param year
     * @return
     */
    @Override
    public List<Double> loadCustomerMethodStat(String year) {
        List<Double> list = this.statMapper.loadCustomerMethodStat(year);

        for (int i = 0; i < list.size(); i++){
            if(null == list.get(i)){
                list.set(i, 0.0);
            }
        }
        return list;
    }

}
