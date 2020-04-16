package com.qc.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qc.system.constant.Constant;
import com.qc.system.domain.Role;
import com.qc.system.mapper.RoleMapper;
import com.qc.system.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qc.system.utils.DataGridView;
import com.qc.system.vo.RoleVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 唐颖豪
 * @since 2020-01-07
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private Log log = LogFactory.getLog(RoleServiceImpl.class);

    @Autowired
    private RoleMapper roleMapper;

    @Transactional(readOnly = true)
    @Override
    public DataGridView loadAllRole(RoleVo roleVo) {

        QueryWrapper<Role> qw = new QueryWrapper<>();
        if(null != roleVo){
            qw.like(StringUtils.isNotBlank(roleVo.getName()), "name",roleVo.getName());
            qw.like(StringUtils.isNotBlank(roleVo.getRemark()), "remark",roleVo.getRemark());
        }else{
            log.info("RoleVo为空");
        }
//        qw.orderByDesc("createtime");
        Page<Role> page = new Page<>(roleVo.getPage(), roleVo.getLimit());
        this.roleMapper.selectPage(page,qw);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    @Override
    public Role addRole(Role role) {
        this.roleMapper.insert(role);
        return role;
    }

    @Override
    public Role updateRole(Role role) {
        this.roleMapper.updateById(role);
        return role;
    }

    /**
     * 保存角色和权限之间的关系 操作sys_role_permission
     * @param roleId
     * @param pids
     */
    @Override
    public void saveRolePermission(Integer roleId, Integer[] pids) {
        // 1.根据roleId删除sys_role_permission里面的数据
        this.getBaseMapper().deleteRolePermissionByRid(roleId);

        // 2.保存关系
        if(null!=pids && pids.length > 0){
            for (Integer pid : pids) {
                this.getBaseMapper().saveRolePermission(roleId,pid);
            }
        }
    }

    /**
     * 根据用户ID查询弹出层 角色数据 并且选中用户已有的
     * @param userId
     * @return
     */
    @Override
    public DataGridView queryRolesByUserId(Integer userId) {
        // 1.查询所有可用角色
        QueryWrapper<Role> qw = new QueryWrapper<>();
        qw.eq("available", Constant.AVAILABLE_TRUE);
        List<Role> roles = this.getBaseMapper().selectList(qw);

        // 2.查询当前用户已有的角色
        List<Integer> roleIds = this.getBaseMapper().selectRoleIdsByUserId(userId);

        List<Role> currentUserRoles = new ArrayList<>();
        if(roleIds != null && roleIds.size() != 0){
            qw.in("id",roleIds);
            currentUserRoles = this.getBaseMapper().selectList(qw);
        }

        List<Map<String,Object>> res =new ArrayList<>();
        for (Role r1 : roles) {
            Boolean LAY_CHECKED = false;
            for (Role r2 : currentUserRoles) {
                if(r1.getId() == r2.getId()){
                    LAY_CHECKED = true;
                    break;
                }
            }

            Map<String, Object> map = new HashMap<>();
            map.put("id",r1.getId());
            map.put("name",r1.getName());
            map.put("remark",r1.getRemark());
            map.put("LAY_CHECKED",LAY_CHECKED);

            res.add(map);
        }

        return new DataGridView(res);
    }

    /**
     * 根据用户ID查询用户拥有的角色名称
     * @param id
     * @return
     */
    @Override
    public List<String> queryRoleNamesByUserId(Integer id) {
        // 1.查询当前用户已有的角色ID
        List<Integer> roleIds = this.getBaseMapper().selectRoleIdsByUserId(id);

        // 1.查询所有可用角色
        QueryWrapper<Role> qw = new QueryWrapper<>();
        qw.eq("available", Constant.AVAILABLE_TRUE);

        List<Role> currentUserRoles = new ArrayList<>();
        if(roleIds != null && roleIds.size() != 0){
            qw.in("id",roleIds);
            currentUserRoles = this.getBaseMapper().selectList(qw);
        }

        List<String> roles = new ArrayList<>();
        for (Role role : currentUserRoles) {
            roles.add(role.getName());
        }
        return roles;
    }

    /**
     * 单个删除
     * @param id
     * @return
     */
    @Override
    public boolean removeById(Serializable id) {
        // 根据角色ID删除角色和权限中间表的数据
        this.getBaseMapper().deleteRolePermissionByRid(id);
        this.getBaseMapper().deleteRoleUserByRoleId(id);

        return super.removeById(id);// 删除角色
    }

    /**
     * 批量删除
     * @param idList
     * @return
     */
    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {

        if(idList.size() > 0){
            for (Serializable id : idList) {
                // 根据角色ID删除角色和权限中间表的数据
                this.getBaseMapper().deleteRolePermissionByRid(id);
                this.getBaseMapper().deleteRoleUserByRoleId(id);
            }
        }

        return super.removeByIds(idList);
    }
}
