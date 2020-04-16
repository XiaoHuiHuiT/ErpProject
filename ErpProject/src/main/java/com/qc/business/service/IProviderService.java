package com.qc.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qc.business.domain.Provider;
import com.qc.business.vo.ProviderVo;
import com.qc.system.utils.DataGridView;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 唐颖豪
 * @since 2019-11-15
 */
public interface IProviderService extends IService<Provider> {

	DataGridView queryAllProvider(ProviderVo providerVo);

	Provider addProvider(Provider provider);

	Provider updateProvider(Provider provider);

	/**
	 * 查询所有可用的供应商
	 * @return
	 */
	DataGridView queryAllAvailableProvider();

}
