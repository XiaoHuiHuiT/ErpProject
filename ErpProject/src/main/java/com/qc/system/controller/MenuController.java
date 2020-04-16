package com.qc.system.controller;

import com.qc.system.constant.Constant;
import com.qc.system.domain.Permission;
import com.qc.system.service.IPermissionService;
import com.qc.system.utils.DataGridView;
import com.qc.system.utils.ResultObj;
import com.qc.system.utils.TreeNode;
import com.qc.system.vo.PermissionVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  菜单管理前端控制器
 * </p>
 *
 * @author 唐颖豪
 * @since 2020-01-04
 */
@RestController
@RequestMapping("menu")
public class MenuController {

    private Log log = LogFactory.getLog(MenuController.class);

    @Autowired
    private IPermissionService permissionService;

    /**
     * 全查询
     * @param permissionVo
     * @return
     */
    @RequestMapping("loadAllMenu")
    public DataGridView loadAllMenu(PermissionVo permissionVo){
        permissionVo.setType(Constant.TYPE_MENU);// 只查菜单
        return permissionService.loadAllPermission(permissionVo);
    }

    /**
     * 生成json树
     * @return
     */
    @RequestMapping("loadAllMenuTreeJson")
    public DataGridView loadAllMenuTreeJson(PermissionVo permissionVo){
        permissionVo.setAvailable(Constant.AVAILABLE_TRUE); // 只查可用的
        permissionVo.setType(Constant.TYPE_MENU);// 只查菜单

        List<Permission> allMenu = this.permissionService.queryAllPermissionForList(permissionVo);

        List<TreeNode> treeNodes = new ArrayList<>();
        for (Permission p : allMenu) {
            Integer id = p.getId();
            Integer pid = p.getPid();
            String title = p.getTitle();
            Boolean spread = p.getOpen() == 1 ? true : false;
            treeNodes.add(new TreeNode(id, pid, title, spread));
        }

        return new DataGridView(treeNodes);
    }


    /**
     * 添加
     * @param permission
     * @return
     */
    @RequestMapping("addMenu")
    public ResultObj addMenu(Permission permission){
        permission.setType(Constant.TYPE_MENU);

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
    @RequestMapping("updateMenu")
    public ResultObj updateMenu(Permission permission){
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
    @RequestMapping("deleteMenu")
    public ResultObj deleteMenu(Integer id){
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
    @RequestMapping("checkCurrentMenuHasChild")
    public DataGridView checkCurrentMenuHasChild(Integer id){
        Integer count = this.permissionService.queryPermissionCountByPid(id);
        return new DataGridView(count);
    }

}