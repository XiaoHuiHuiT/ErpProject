package com.qc.system.controller;


import com.qc.system.domain.Notice;
import com.qc.system.domain.User;
import com.qc.system.service.INoticeService;
import com.qc.system.utils.DataGridView;
import com.qc.system.utils.ResultObj;
import com.qc.system.vo.NoticeVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 唐颖豪
 * @since 2019-12-31
 */
@RestController
@RequestMapping("notice")
public class NoticeController {

    private Log log = LogFactory.getLog(NoticeController.class);

    @Autowired
    private INoticeService noticeService;

    /**
     * 全查询
     * @param noticeVo
     * @return
     */
    @RequestMapping("loadAllNotice")
    public DataGridView loadAllNotice(NoticeVo noticeVo){
        return this.noticeService.loadAllNotice(noticeVo);
    }

    /**
     * 添加
     * @param notice
     * @return
     */
    @RequestMapping("addNotice")
    public ResultObj addNotice(Notice notice, HttpSession session){
        User user = (User)session.getAttribute("user");

        notice.setOpername(user.getName());// 设置发布人
        notice.setCreatetime(new Date());// 设置时间
        try{
            this.noticeService.addNotice(notice);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            log.error("添加失败");
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改
     * @param notice
     * @return
     */
    @RequestMapping("updateNotice")
    public ResultObj updateNotice(Notice notice){
        try{
            this.noticeService.updateNotice(notice);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            log.error("修改失败");
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("deleteNotice")
    public ResultObj deleteNotice(Integer id){
        try{
            this.noticeService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("batchDeleteNotice")
    public ResultObj batchDeleteNotice(Integer[] ids){
        try{
            if(null == ids || 0 == ids.length){
                log.error("参数不能为空");
                return ResultObj.DELETE_ERROR;
            }
            Collection<Serializable> idList = new ArrayList<>();
            for (Integer id : ids) {
                idList.add(id);
            }

            this.noticeService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }
    }

    @RequestMapping("loadNoticeById")
    public DataGridView loadNoticeById(Integer id){
        return new DataGridView(this.noticeService.getById(id));
    }
}