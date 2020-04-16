package com.qc.system.service;

import com.qc.system.domain.Loginfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qc.system.utils.DataGridView;
import com.qc.system.vo.LoginVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 唐颖豪
 * @since 2020-01-04
 */
public interface ILoginfoService extends IService<Loginfo> {

    DataGridView loadAllLogInfo(LoginVo loginVo);

}
