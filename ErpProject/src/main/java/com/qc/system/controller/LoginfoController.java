package com.qc.system.controller;


import com.qc.system.service.ILoginfoService;
import com.qc.system.utils.DataGridView;
import com.qc.system.utils.ResultObj;
import com.qc.system.vo.LoginVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 唐颖豪
 * @since 2020-01-04
 */
@RestController
@RequestMapping("loginfo")
public class LoginfoController {

    private Log log = LogFactory.getLog(LoginfoController.class);

    @Autowired
    private ILoginfoService loginfoService;


    /**
     * 全查询
     * @param LoginfoVo
     * @return
     */
    @RequestMapping("loadAllLoginfo")
    public DataGridView loadAllLoginfo(LoginVo LoginfoVo){
        return this.loginfoService.loadAllLogInfo(LoginfoVo);
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("batchDeleteLoginfo")
    public ResultObj batchDeleteLoginfo(Integer[] ids){
        try{
            if(null == ids || 0 == ids.length){
                log.error("参数不能为空");
                return ResultObj.DELETE_ERROR;
            }
            Collection<Serializable> idList = new ArrayList<>();
            for (Integer id : ids) {
                idList.add(id);
            }

            this.loginfoService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }
    }

    @RequestMapping("loadLoginfoById")
    public DataGridView loadLoginfoById(Integer id){
        return new DataGridView(this.loginfoService.getById(id));
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("deleteLoginfo")
    public ResultObj deleteLoginfo(Integer id){
        try{
            this.loginfoService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }
    }



}

