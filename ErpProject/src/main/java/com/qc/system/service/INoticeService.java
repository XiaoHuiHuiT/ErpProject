package com.qc.system.service;

import com.qc.system.domain.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qc.system.utils.DataGridView;
import com.qc.system.vo.NoticeVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 唐颖豪
 * @since 2019-12-31
 */
public interface INoticeService extends IService<Notice> {

    public DataGridView loadAllNotice(NoticeVo noticeVo);

    public void addNotice(Notice notice);

    public void updateNotice(Notice notice);
}
