package com.qc.system.service;

import com.qc.system.domain.Role;
import com.qc.system.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qc.system.utils.DataGridView;
import com.qc.system.vo.RoleVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 唐颖豪
 * @since 2020-01-07
 */
public interface IRoleService extends IService<Role> {

    public DataGridView loadAllRole(RoleVo roleVo);

    public Role addRole(Role role);

    public Role updateRole(Role role);

    /**
     * 保存角色和权限之间的关系 操作sys_role_permission
     * @param roleId
     * @param pids
     */
    public void saveRolePermission(Integer roleId, Integer[] pids);

    /**
     * 根据用户ID查询弹出层 角色数据 并且选中用户已有的
     * @param userId
     * @return
     */
    public DataGridView queryRolesByUserId(Integer userId);

    /**
     * 根据用户ID查询用户拥有的角色名称
     * @param id
     * @return
     */
    public List<String> queryRoleNamesByUserId(Integer id);


}
