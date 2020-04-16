package com.qc.system.controller;

import com.qc.system.domain.Role;
import com.qc.system.service.IPermissionService;
import com.qc.system.service.IRoleService;
import com.qc.system.utils.DataGridView;
import com.qc.system.utils.ResultObj;
import com.qc.system.vo.RoleVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
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
 * @since 2020-01-07
 */
@RestController
@RequestMapping("role")
public class RoleController {

    private Log log = LogFactory.getLog(RoleController.class);

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    /**
     * 全查询
     * @param roleVo
     * @return
     */
    @RequestMapping("loadAllRole")
    public DataGridView loadAllRole(RoleVo roleVo){
        return this.roleService.loadAllRole(roleVo);
    }

    /**
     * 添加
     * @param role
     * @return
     */
    @RequestMapping("addRole")
    public ResultObj addRole(Role role){
        role.setCreatetime(new Date());// 设置时间
        try{
            this.roleService.addRole(role);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            log.error("添加失败");
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改
     * @param role
     * @return
     */
    @RequestMapping("updateRole")
    public ResultObj updateRole(Role role){
        try{
            this.roleService.updateRole(role);
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
    @RequestMapping("deleteRole")
    public ResultObj deleteRole(Integer id){
        try{
            this.roleService.removeById(id);
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
    @RequestMapping("batchDeleteRole")
    public ResultObj batchDeleteRole(Integer[] ids){
        try{
            if(null == ids || 0 == ids.length){
                log.error("参数不能为空");
                return ResultObj.DELETE_ERROR;
            }
            Collection<Serializable> idList = new ArrayList<>();
            for (Integer id : ids) {
                idList.add(id);
            }

            this.roleService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 根据角色ID加载并选中权限和菜单
     * @param roleId
     * @return
     */
    @RequestMapping("loadRolePermission")
    public DataGridView loadRolePermission(@RequestParam("id")Integer roleId){
        return this.permissionService.queryRolePermissionByRoleId(roleId);
    }

    /**
     * 保存角色和权限之间的关系 操作sys_role_permission
     * @param roleId
     * @param pids
     * @return
     */
    @RequestMapping("saveRolePermission")
    public ResultObj saveRolePermission(Integer roleId,Integer[] pids){
        try{
            this.roleService.saveRolePermission(roleId,pids);
            return ResultObj.DISPATCH_SUCCESS;
        }catch (Exception e){
            log.error("分配失败");
            return ResultObj.DISPATCH_ERROR;
        }
    }

    /**
     * 根据用户ID查询弹出层 角色数据 并且选中用户已有的
     * @param userId
     * @return
     */
    @RequestMapping("loadRolesByUserId")
    public DataGridView loadRolesByUserId(@RequestParam("userId") Integer userId){
        return this.roleService.queryRolesByUserId(userId);
    }

}