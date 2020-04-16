package com.qc.system.mapper;

import com.qc.system.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 唐颖豪
 * @since 2019-12-31
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    public Integer queryPermissionMaxOrderNum();

    public Integer queryPermissionCountByPid(Integer id);

    /**
     * 根据角色ID查询当前角色的权限ID集合
     * @param roleId
     * @return
     */
    public List<Integer> queryPermissionIdsByRoleId(@Param("roleId") Integer roleId);

    /**
     * 根据角色ID查询权限ID
     * @param roleIds
     * @return
     */
    public List<Integer> queryPermissionIdsByRoleIds(@Param("roleIds") List<Integer> roleIds);
}
