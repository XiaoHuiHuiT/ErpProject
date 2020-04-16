package com.qc.business.service.impl;

import java.io.Serializable;

import com.qc.system.constant.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qc.business.domain.Customer;
import com.qc.business.mapper.CustomerMapper;
import com.qc.business.service.ICustomerService;
import com.qc.business.vo.CustomerVo;
import com.qc.system.utils.DataGridView;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
@Transactional
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {


	@Override
	public boolean updateById(Customer entity) {
		return super.updateById(entity);
	}

	@Override
	public boolean removeById(Serializable id) {
		return super.removeById(id);
	}
	@Override
	public Customer getById(Serializable id) {
		return super.getById(id);
	}
	@Override
	public DataGridView queryAllCustomer(CustomerVo customerVo) {
		IPage<Customer> page = new Page<>(customerVo.getPage(), customerVo.getLimit());
		QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
		queryWrapper.like(StringUtils.isNotBlank(customerVo.getCustomername()), "customername",
				customerVo.getCustomername());
		queryWrapper.like(StringUtils.isNotBlank(customerVo.getPhone()), "phone", customerVo.getPhone());
		queryWrapper.like(StringUtils.isNotBlank(customerVo.getConnectionperson()), "connectionperson",
				customerVo.getConnectionperson());
		this.baseMapper.selectPage(page, queryWrapper);
		return new DataGridView(page.getTotal(), page.getRecords());
	}

	@Override
	public Customer addCustomer(Customer customer) {
		this.getBaseMapper().insert(customer);
		return customer;
	}

	@Override
	public Customer updateCustomer(Customer customerr) {
		this.getBaseMapper().updateById(customerr);
		return customerr;
	}

	/**
	 * 查询所有可用的客户
	 * @return
	 */
	@Override
	public DataGridView loadAllAvailableCustomer() {
		QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("available", Constant.AVAILABLE_TRUE);

		return new DataGridView(this.getBaseMapper().selectList(queryWrapper));

	}

}
