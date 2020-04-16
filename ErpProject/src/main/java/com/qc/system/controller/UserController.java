package com.qc.system.controller;


import com.qc.system.constant.Constant;
import com.qc.system.domain.Dept;
import com.qc.system.domain.User;
import com.qc.system.service.IUserService;
import com.qc.system.utils.DataGridView;
import com.qc.system.utils.MD5Utils;
import com.qc.system.utils.ResultObj;
import com.qc.system.vo.UserVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
 * @since 2019-12-30
 */
@RestController
@RequestMapping("user")
public class UserController {

    private Log log = LogFactory.getLog(UserController.class);

    @Autowired
    private IUserService userService;

    /**
     * 全查询
     * @param userVo
     * @return
     */
    @RequestMapping("loadAllUser")
    public DataGridView loadAllUser(UserVo userVo){
        return userService.loadAllUser(userVo);
    }

    /**
     * 根据部门ID查询员工
     * @param deptid
     * @return
     */
    @RequestMapping("queryUserByDeptId")
    public DataGridView queryUserByDeptId(Integer deptid){
        return new DataGridView(this.userService.queryUserByDeptId(deptid));
    }

    /**
     * 加载最大排序码
     * @return
     */
    @RequestMapping("loadUserMaxOrderNum")
    public DataGridView loadUserMaxOrderNum(){
        Integer max = this.userService.loadUserMaxOrderNum();
        return new DataGridView(max + 1);
    }

    /**
     * 添加
     * @param user
     * @return
     */
    @RequestMapping("addUser")
    public ResultObj addUser(User user){
        user.setHiredate(new Date());// 设置时间
        user.setType(Constant.USER_TYPE_NORMAL);// 用户类型
        user.setImgpath(Constant.USER_DEFAULT_IMAGE);
        user.setSalt(MD5Utils.getSalt());
        user.setPwd(MD5Utils.md5(Constant.USER_DEFAULT_PWD, user.getSalt(),2));

        try{
            this.userService.addUser(user);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            log.error("添加失败");
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改
     * @param user
     * @return
     */
    @RequestMapping("updateUser")
    public ResultObj updateUser(User user){
        try{
            this.userService.updateUser(user);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            log.error("修改失败");
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 根据用户ID查询用户对象
     * @param userId
     * @return
     */
    @RequestMapping("loadUserByUserId")
    public DataGridView loadUserByUserId(Integer userId){
        User user = this.userService.getById(userId);
        return new DataGridView(user);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("deleteUser")
    public ResultObj deleteUser(Integer id){
        try{
            this.userService.removeById(id);
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
    @RequestMapping("batchDeleteUser")
    public ResultObj batchDeleteUser(Integer[] ids){
        try{
            if(null == ids || 0 == ids.length){
                log.error("参数不能为空");
                return ResultObj.DELETE_ERROR;
            }
            Collection<Serializable> idList = new ArrayList<>();
            for (Integer id : ids) {
                idList.add(id);
            }

            this.userService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 重置密码
     * @param id
     * @return
     */
    @RequestMapping("resetUserPwd")
    public ResultObj resetUserPwd(Integer id){
        try{
            this.userService.resetUserPwd(id);
            return ResultObj.RESET_SUCCESS;
        }catch (Exception e){
            log.error("重置失败");
            return ResultObj.RESET_ERROR;
        }
    }

    /**
     * 保存用户和角色之间的关系 操作sys_user_role
     * @param userId
     * @param rids
     * @return
     */
    @RequestMapping("saveUserRole")
    public ResultObj saveUserRole(Integer userId,Integer[] rids){
        try{
            this.userService.saveUserRole(userId,rids);
            return ResultObj.DISPATCH_SUCCESS;
        }catch (Exception e){
            log.error("分配失败");
            return ResultObj.DISPATCH_ERROR;
        }
    }

    /**
     * 修改个人信息
     * @param user
     * @return
     */
    @RequestMapping("changeUser")
    public ResultObj changeUser(User user){
        try{
            this.userService.changeUser(user);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            log.error("修改个人信息失败");
            return ResultObj.UPDATE_ERROR;
        }
    }
}