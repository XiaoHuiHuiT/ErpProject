package com.qc.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qc.system.constant.Constant;
import com.qc.system.domain.Permission;
import com.qc.system.mapper.PermissionMapper;
import com.qc.system.mapper.RoleMapper;
import com.qc.system.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qc.system.utils.DataGridView;
import com.qc.system.utils.TreeNode;
import com.qc.system.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 唐颖豪
 * @since 2019-12-31
 */
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    private Log log = LogFactory.getLog(PermissionServiceImpl.class);

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Permission> queryAllPermissionForList(PermissionVo permissionVo) {
        PermissionMapper permissionMapper = this.getBaseMapper();
        QueryWrapper<Permission> qw = new QueryWrapper<>();
        if(null != permissionVo){
            qw.eq(StringUtils.isNotBlank(permissionVo.getType()), "type",permissionVo.getType());
            qw.eq(permissionVo.getAvailable()!=null, "available",permissionVo.getAvailable());
            qw.like(StringUtils.isNotBlank(permissionVo.getTitle()), "title",permissionVo.getTitle());
        }else{
            log.info("permissionVo的参数为空");
        }
        qw.orderByAsc("ordernum");
        List<Permission> list = permissionMapper.selectList(qw);
        return list;
    }

    @Override
    public DataGridView loadAllPermission(PermissionVo permissionVo) {
        Page<Permission> page = new Page<>(permissionVo.getPage(), permissionVo.getLimit());

        QueryWrapper<Permission> qw = new QueryWrapper<>();

        qw.eq(StringUtils.isNotBlank(permissionVo.getType()), "type",permissionVo.getType());
        qw.eq(permissionVo.getAvailable()!=null, "available",permissionVo.getAvailable());
        qw.like(StringUtils.isNotBlank(permissionVo.getTitle()), "title",permissionVo.getTitle());
        qw.like(StringUtils.isNotBlank(permissionVo.getPercode()), "percode",permissionVo.getPercode());

        // 里面可以并行条件 =相当于and (aaa=xxx or aaa = xxxx)
        qw.and(permissionVo.getId() != null,new Consumer<QueryWrapper<Permission>>(){
            @Override
            public void accept(QueryWrapper<Permission> t) {
                t.eq(permissionVo.getId() != null, "id", permissionVo.getId()).or().eq(permissionVo.getId() != null, "pid", permissionVo.getId());
            }
        });

        qw.orderByAsc("ordernum");

        this.baseMapper.selectPage(page,qw);

        return new DataGridView(page.getTotal(),page.getRecords());
    }

    @Override
    public Integer queryPermissionMaxOrderNum() {
        return this.baseMapper.queryPermissionMaxOrderNum();
    }

    @Override
    public Permission addPermission(Permission permission) {
        this.baseMapper.insert(permission);
        return permission;
    }

    @Override
    public Permission updatePermission(Permission permission) {
        this.baseMapper.updateById(permission);
        return permission;
    }

    @Override
    public Integer queryPermissionCountByPid(Integer id) {
        return this.baseMapper.queryPermissionCountByPid(id);
    }

    @Override
    public boolean removeById(Serializable id) {
        // 根据菜单或权限ID删除sys_role_permission里面的数据
        this.roleMapper.deleteRolePermissionByPid(id);

        return super.removeById(id);
    }

    @Override
    public DataGridView queryRolePermissionByRoleId(Integer roleId) {
        // 1.查询所有可用的权限和菜单
        QueryWrapper<Permission> qw = new QueryWrapper<>();
        qw.eq("available", Constant.AVAILABLE_TRUE);
        List<Permission> allPermission = this.baseMapper.selectList(qw);

        // 根据角色ID查询当前角色的权限ID集合
        List<Integer> permissionIds = this.baseMapper.queryPermissionIdsByRoleId(roleId);
        List<Permission> currRolePermission = null;
        // 2.根据角色ID查询当前角色拥有的权限集合
        if(permissionIds == null || permissionIds.size() == 0){
            currRolePermission = new ArrayList<>();
        }else{
            qw.in("id", permissionIds);
            currRolePermission =  this.baseMapper.selectList(qw);
        }
        List<TreeNode> nodes = new ArrayList<>();

        for (Permission p1 : allPermission) {
            String checkArr = "0";
            for (Permission p2 : currRolePermission) {
                if(p1.getId() == p2.getId()){
                    checkArr = "1";
                    break;
                }
            }
            Boolean spread = p1.getOpen() == null ? false : p1.getOpen() == 1 ? true : false;
            nodes.add(new TreeNode(p1.getId(),p1.getPid(),p1.getTitle(),spread,checkArr));
        }
        return new DataGridView(nodes);
    }

    /**
     * 根据用户拥有的角色ID查询角色对应的权限
     * @param id
     * @return
     */
    @Override
    public List<String> queryPermissionsByUserId(Integer id) {
        // 1.根据用户ID查询角色ID
        List<Integer> roleIds = this.roleMapper.selectRoleIdsByUserId(id);
        if(null == roleIds || roleIds.size() == 0){
            return null;
        }else{
            // 2.根据角色ID查询权限ID
            List<Integer> permissionIds = this.getBaseMapper().queryPermissionIdsByRoleIds(roleIds);
            if(null == permissionIds || permissionIds.size() == 0){
                return null;
            }else{
                // 3.根据用户拥有角色ID查询权限
                QueryWrapper<Permission> qw = new QueryWrapper<>();
                qw.eq("available", Constant.AVAILABLE_TRUE);// 可用
                qw.eq("type", Constant.TYPE_PERMISSION);// 查询权限编码
                qw.in("id",permissionIds);

                List<Permission> permissionsObjs = this.getBaseMapper().selectList(qw);

                // 最后返回的数据
                List<String> permissions = new ArrayList<>();
                for (Permission permission : permissionsObjs) {
                    permissions.add(permission.getPercode());
                }
                return permissions;
            }
        }
    }

    /**
     * 根据用户ID查询用户拥有菜单
     * @param permissionVo
     * @param id
     * @return
     */
    @Override
    public List<Permission> queryPermissionsByUserIdForList(PermissionVo permissionVo, Integer id) {
        // 1.根据用户ID查询角色ID
        List<Integer> roleIds = this.roleMapper.selectRoleIdsByUserId(id);
        if(null == roleIds || roleIds.size() == 0){
            return null;
        }else{
            // 2.根据角色ID查询权限ID
            List<Integer> permissionIds = this.getBaseMapper().queryPermissionIdsByRoleIds(roleIds);
            if(null == permissionIds || permissionIds.size() == 0){
                return null;
            }else{
                // 3.根据用户拥有角色ID查询权限
                QueryWrapper<Permission> qw = new QueryWrapper<>();
                qw.eq("available", Constant.AVAILABLE_TRUE);// 可用
                qw.eq("type", Constant.TYPE_MENU);// 查询菜单
                qw.in("id",permissionIds);

                List<Permission> permissionsObjs = this.getBaseMapper().selectList(qw);
                return permissionsObjs;
            }
        }
    }

}