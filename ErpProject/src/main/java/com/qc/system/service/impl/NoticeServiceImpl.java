package com.qc.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qc.system.domain.Notice;
import com.qc.system.mapper.NoticeMapper;
import com.qc.system.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qc.system.utils.DataGridView;
import com.qc.system.vo.NoticeVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 唐颖豪
 * @since 2019-12-31
 */
@Service
@Transactional
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    private Log log = LogFactory.getLog(NoticeServiceImpl.class);

    @Autowired
    private NoticeMapper noticeMapper;

    @Transactional(readOnly = true)
    @Override
    public DataGridView loadAllNotice(NoticeVo noticeVo) {

        QueryWrapper<Notice> qw = new QueryWrapper<>();
        if(null != noticeVo){
            qw.like(StringUtils.isNotBlank(noticeVo.getTitle()), "title",noticeVo.getTitle());
            qw.ge(noticeVo.getStartTime()!=null, "createtime",noticeVo.getStartTime());
            qw.le(noticeVo.getEndTime()!=null, "createtime",noticeVo.getEndTime());
        }else{
            log.info("notiveVo为空");
        }
        qw.orderByDesc("createtime");
        Page<Notice> page = new Page<>(noticeVo.getPage(), noticeVo.getLimit());
        this.noticeMapper.selectPage(page,qw);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    @Override
    public void addNotice(Notice notice) {
        this.noticeMapper.insert(notice);
    }

    @Override
    public void updateNotice(Notice notice) {
        this.noticeMapper.updateById(notice);
    }
}