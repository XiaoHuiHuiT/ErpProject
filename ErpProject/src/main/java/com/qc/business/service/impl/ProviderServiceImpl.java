package com.qc.business.service.impl;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qc.business.domain.Provider;
import com.qc.business.mapper.ProviderMapper;
import com.qc.business.service.IProviderService;
import com.qc.business.vo.ProviderVo;
import com.qc.system.constant.Constant;
import com.qc.system.utils.DataGridView;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 唐颖豪
 * @since 2019-11-15
 */
@Service
@Transactional
public class ProviderServiceImpl extends ServiceImpl<ProviderMapper, Provider> implements IProviderService {
	
	@Override
	public boolean removeById(Serializable id) {
		return super.removeById(id);
	}
	
	@Override
	public Provider getById(Serializable id) {
		return super.getById(id);
	}
	
	@Override
	public boolean removeByIds(Collection<? extends Serializable> idList) {
		return super.removeByIds(idList);
	}
	@Override
	public DataGridView queryAllProvider(ProviderVo providerVo) {
		IPage<Provider> page = new Page<>(providerVo.getPage(), providerVo.getLimit());
		QueryWrapper<Provider> queryWrapper = new QueryWrapper<>();
		queryWrapper.like(StringUtils.isNotBlank(providerVo.getProvidername()), "providername",
				providerVo.getProvidername());
		queryWrapper.like(StringUtils.isNotBlank(providerVo.getPhone()), "phone", providerVo.getPhone());
		queryWrapper.like(StringUtils.isNotBlank(providerVo.getConnectionperson()), "connectionperson",
				providerVo.getConnectionperson());
		this.getBaseMapper().selectPage(page, queryWrapper);
		return new DataGridView(page.getTotal(), page.getRecords());
	}

	@Override
	public Provider addProvider(Provider provider) {
		this.getBaseMapper().insert(provider);
		return provider;
	}

	@Override
	public Provider updateProvider(Provider provider) {
		this.getBaseMapper().updateById(provider);
		return provider;
	}

	@Override
	public DataGridView queryAllAvailableProvider() {
		QueryWrapper<Provider> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("available", Constant.AVAILABLE_TRUE);
		return new DataGridView(this.getBaseMapper().selectList(queryWrapper));
	}
	
}
