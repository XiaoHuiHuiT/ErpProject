package com.qc.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qc.business.domain.Customer;
import com.qc.business.vo.CustomerVo;
import com.qc.system.utils.DataGridView;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface ICustomerService extends IService<Customer> {

	DataGridView queryAllCustomer(CustomerVo customerVo);

	Customer addCustomer(Customer customer);

	Customer updateCustomer(Customer customerr);

    DataGridView loadAllAvailableCustomer();

}
