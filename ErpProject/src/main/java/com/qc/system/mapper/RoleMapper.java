package com.qc.system.mapper;

import com.qc.system.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 唐颖豪
 * @since 2020-01-07
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据角色ID删除角色和权限中间表的数据
     * @param id
     */
    public void deleteRolePermissionByRid(Serializable id);

    /**
     * 根据菜单或权限ID删除sys_role_permission里面的数据
     * @param id
     */
    public void deleteRolePermissionByPid(Serializable id);

    /**
     * 保存角色和菜单的关系
     * @param roleId
     * @param pid
     */
    public void saveRolePermission(@Param("roleId") Integer roleId, @Param("pid") Integer pid);

    /**
     * 根据用户ID删除用户和角色的关系表的数据
     * @param id
     */
    public void deleteRoleUserByUserId(Serializable id);

    /**
     * 根据角色ID删除用户和角色的关系
     * @param id
     */
    public void deleteRoleUserByRoleId(Serializable id);

    /**
     * 查询当前用户已有的角色
     * @param userId
     * @return
     */
    public List<Integer> selectRoleIdsByUserId(Integer userId);
}
