package com.qc.system.service;

import com.qc.system.domain.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qc.system.utils.DataGridView;
import com.qc.system.utils.TreeNode;
import com.qc.system.vo.PermissionVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 唐颖豪
 * @since 2019-12-31
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 查询菜单或权限
     * @param permissionVo
     * @return
     */
    public List<Permission> queryAllPermissionForList(PermissionVo permissionVo);

    public DataGridView loadAllPermission(PermissionVo permissionVo);

    public Integer queryPermissionMaxOrderNum();

    public Permission addPermission(Permission permission);

    public Permission updatePermission(Permission permission);

    public Integer queryPermissionCountByPid(Integer id);

    public DataGridView queryRolePermissionByRoleId(Integer roleId);

    /**
     * 根据用户拥有的角色ID查询角色对应的权限
     * @param id
     * @return
     */
    public List<String> queryPermissionsByUserId(Integer id);

    /**
     * 根据用户ID查询用户拥有菜单
     * @param permissionVo
     * @param id
     * @return
     */
    public List<Permission> queryPermissionsByUserIdForList(PermissionVo permissionVo, Integer id);
}