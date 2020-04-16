package com.qc.statistical.controller;

import com.qc.business.domain.Customer;
import com.qc.business.service.ICustomerService;
import com.qc.statistical.domain.BaseEntity;
import com.qc.statistical.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计分析
 */
@RequestMapping("stat")
@Controller
public class StatisticalController {

    @Autowired
    private StatService statService;

    @Autowired
    private ICustomerService customerService;

    /**
     * 加载客户地区数据
     * @return
     */
    @RequestMapping("loadCustomerAreaStatJson")
    @ResponseBody
    public List<BaseEntity> loadCustomerAreaStatJson(){
        return this.statService.loadCustomerAreaStatList();
    }

    /**
     * 加载客户年度销售额统计
     * @return
     */
    @RequestMapping("loadCustomerYearGradeStat")
    @ResponseBody
    public Map<String,Object> loadCustomerYearGradeStat(String yaer){
        List<BaseEntity> entities = this.statService.loadCustomerYearGradeStatList(yaer);
        List<BaseEntity> list = new ArrayList<>();

        for (BaseEntity entity : entities) {
            Customer customer = this.customerService.getBaseMapper().selectById(entity.getName());
            entity.setName(customer.getCustomername());
            list.add(entity);
        }

        Map<String,Object> map = new HashMap<>();

        List<String> names = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        for (BaseEntity entity : list) {
            names.add(entity.getName());
            values.add(entity.getValue());
        }

        map.put("name", names);
        map.put("value", values);

        return map;
    }

    /**
     * 查询客户年度月份销售额
     * @param year
     * @return
     */
    @RequestMapping("loadCustomerMethodStat")
    @ResponseBody
    public List<Double> loadCustomerMethodStat(String year){
        return this.statService.loadCustomerMethodStat(year);
    }

}