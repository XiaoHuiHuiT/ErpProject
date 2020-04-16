package com.qc.business.service;

import com.qc.business.domain.Salesback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qc.business.vo.SalesbackVo;
import com.qc.system.utils.DataGridView;

public interface SalesbackService extends IService<Salesback>{


    Salesback addSalesback(Salesback salesback);

    DataGridView queryAllSalesback(SalesbackVo salesbackVo);

}
