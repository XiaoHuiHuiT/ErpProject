package com.qc.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qc.system.domain.Loginfo;
import com.qc.system.mapper.LoginfoMapper;
import com.qc.system.service.ILoginfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qc.system.utils.DataGridView;
import com.qc.system.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 唐颖豪
 * @since 2020-01-04
 */
@Service
@Transactional
public class LoginfoServiceImpl extends ServiceImpl<LoginfoMapper, Loginfo> implements ILoginfoService {

    private Log log = LogFactory.getLog(LoginfoServiceImpl.class);

    @Override
    public DataGridView loadAllLogInfo(LoginVo loginVo) {

        QueryWrapper<Loginfo> qw = new QueryWrapper<>();
        if(null != loginVo){
            qw.like(StringUtils.isNotBlank(loginVo.getLoginname()), "loginname",loginVo.getLoginname());
            qw.ge(loginVo.getStartTime()!=null, "logintime",loginVo.getStartTime());
            qw.le(loginVo.getEndTime()!=null, "logintime",loginVo.getEndTime());
        }else{
            log.info("loginVo为空");
        }
        qw.orderByDesc("logintime");
        Page<Loginfo> page = new Page<>(loginVo.getPage(), loginVo.getLimit());
        this.baseMapper.selectPage(page, qw);
        return new DataGridView(page.getTotal(),page.getRecords());
    }
}
