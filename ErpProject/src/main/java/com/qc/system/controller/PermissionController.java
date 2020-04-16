package com.qc.system.controller;

import com.qc.system.constant.Constant;
import com.qc.system.domain.Permission;
import com.qc.system.service.IPermissionService;
import com.qc.system.utils.DataGridView;
import com.qc.system.utils.ResultObj;
import com.qc.system.vo.PermissionVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  权限管理前端控制器
 * </p>
 *
 * @author 唐颖豪
 * @since 2019-12-31
 */
@RestController
@RequestMapping("permission")
public class PermissionController {


    private Log log = LogFactory.getLog(PermissionController.class);

    @Autowired
    private IPermissionService permissionService;

    /**
     * 全查询
     * @param permissionVo
     * @return
     */
    @RequestMapping("loadAllPermission")
    public DataGridView loadAllPermission(PermissionVo permissionVo){
        permissionVo.setType(Constant.TYPE_PERMISSION);// 只查权限
        return permissionService.loadAllPermission(permissionVo);
    }

    /**
     * 添加
     * @param permission
     * @return
     */
    @RequestMapping("addPermission")
    public ResultObj addPermission(Permission permission){
        permission.setType(Constant.TYPE_PERMISSION);
        permission.setOpen(0);

        try{
            this.permissionService.addPermission(permission);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            log.error("添加失败");
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改
     * @param permission
     * @return
     */
    @RequestMapping("updatePermission")
    public ResultObj updatePermission(Permission permission){
        try{
            this.permissionService.updatePermission(permission);
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
    @RequestMapping("deletePermission")
    public ResultObj deletePermission(Integer id){
        try{
            this.permissionService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 加载菜单最大的排序码
     * @return
     */
    @RequestMapping("loadPermissionMaxOrderNum")
    public DataGridView loadPermissionMaxOrderNum(){
        Integer maxOrderNumber = this.permissionService.queryPermissionMaxOrderNum();

        return new DataGridView(maxOrderNumber+1);
    }

    /**
     * 查询当前菜单下面是否有子菜单
     * @param id    菜单id
     * @return
     */
    @RequestMapping("checkCurrentPermissionHasChild")
    public DataGridView checkCurrentPermissionHasChild(Integer id){
        Integer count = this.permissionService.queryPermissionCountByPid(id);
        return new DataGridView(count);
    }

}

