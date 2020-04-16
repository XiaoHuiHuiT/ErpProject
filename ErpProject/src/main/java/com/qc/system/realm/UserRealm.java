package com.qc.system.realm;

import com.qc.system.constant.Constant;
import com.qc.system.domain.User;
import com.qc.system.service.IPermissionService;
import com.qc.system.service.IRoleService;
import com.qc.system.service.IUserService;
import com.qc.system.utils.ActiverUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private IUserService userService;

    @Autowired
    @Lazy
    private IRoleService roleService;

    @Autowired
    @Lazy
    private IPermissionService permissionService;

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        /*查询用户信息*/
        User user = this.userService.queryUserByUserName(token.getPrincipal().toString());
        /*查询不为空*/
        if(null != user){
            // 创建ActiveUser
            ActiverUser activerUser = new ActiverUser();
            activerUser.setUser(user);
            if(user.getType() == Constant.USER_TYPE_NORMAL){
                // 说明是普通用户
                List<String> roles = this.roleService.queryRoleNamesByUserId(user.getId());
                activerUser.setRoles(roles);

                // 查询权限编码
                List<String> permissions = this.permissionService.queryPermissionsByUserId(user.getId());
                activerUser.setPermissions(permissions);

            }

            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());// 转换盐
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activerUser, user.getPwd(), credentialsSalt, getName());
            return info;
        }

        return null;
    }

    /*授权*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        ActiverUser activerUser = (ActiverUser) principals.getPrimaryPrincipal();
        if(activerUser.getUser().getType() == Constant.USER_TYPE_SUPER){
            info.addStringPermission("*:*");
        }else{
            List<String> roles = activerUser.getRoles();
            List<String> permissions = activerUser.getPermissions();

            if(roles != null && roles.size() > 0){
                info.addRoles(roles);
            }

            if(permissions != null && permissions.size() > 0){
                info.addStringPermissions(permissions);
            }
        }

        return info;
    }
}