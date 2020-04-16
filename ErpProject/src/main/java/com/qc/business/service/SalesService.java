package com.qc.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qc.business.domain.Sales;
import com.qc.business.vo.SalesVo;
import com.qc.system.utils.DataGridView;

public interface SalesService extends IService<Sales> {


    DataGridView queryAllSales(SalesVo salesVo);

    Sales addSales(Sales sales);

    Sales updateSales(Sales sales);

}

