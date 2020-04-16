package com.qc.system.controller;


import com.qc.system.constant.Constant;
import com.qc.system.domain.Loginfo;
import com.qc.system.domain.Permission;
import com.qc.system.domain.User;
import com.qc.system.service.ILoginfoService;
import com.qc.system.service.IPermissionService;
import com.qc.system.utils.ActiverUser;
import com.qc.system.utils.ResultObj;
import com.qc.system.utils.TreeNode;
import com.qc.system.utils.TreeNodeBuilder;
import com.qc.system.vo.PermissionVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 唐颖豪
 * @since 2019-12-30
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    private Log log = LogFactory.getLog(LoginController.class);

    @Autowired
    private ILoginfoService loginfoService;

    /**
     * 用户登陆
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("/login")
    public ResultObj login(String username, String password, HttpSession session, HttpServletRequest request){
        try {
            Subject subject = SecurityUtils.getSubject();// 得到主体
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);

            subject.login(token);
            ActiverUser activerUser = (ActiverUser)subject.getPrincipal();
            User user = activerUser.getUser();
            session.setAttribute("user", user);

            // 写入登陆日志
            Loginfo entity = new Loginfo();
            entity.setLoginname(user.getName() + "-" + user.getLoginname());
            entity.setLoginip(request.getRemoteAddr());
            entity.setLogintime(new Date());

            this.loginfoService.save(entity);

            // 说明登陆成功
            return ResultObj.LOGIN_SUCCESS;
        }catch (Exception e){
            log.error("登陆失败...用户名或密码不正确");
            return ResultObj.LOGIN_ERROR;
        }
    }

    @Autowired
    private IPermissionService permissionService;

    /**
     * 登陆成功之后加载左边的导航菜单
     * @param permissionVo
     * @return
     */
    @RequestMapping("loadIndexLeftMenuTreeJson")
    public List<TreeNode> loadIndexLeftMenuTreeJson(PermissionVo permissionVo,HttpSession session){
        // 1.查询出所有可用的菜单
        permissionVo.setType(Constant.TYPE_MENU);
        permissionVo.setAvailable(Constant.AVAILABLE_TRUE);
        User user = (User) session.getAttribute("user");
        List<Permission> permissions = null;
        if(user.getType() == Constant.USER_TYPE_SUPER){// 如果是超级管理员
            permissions = this.permissionService.queryAllPermissionForList(permissionVo);
        }else{
            permissions = this.permissionService.queryPermissionsByUserIdForList(permissionVo,user.getId());
        }
        ArrayList<TreeNode> treeNodes = new ArrayList<>();
        for (Permission p : permissions) {
            Integer id = p.getId();
            Integer pid = p.getPid();

            String title = p.getTitle();
            String href = p.getHref();
            String icon = p .getIcon();
            Boolean spread = p.getOpen() == 1 ? true: false;
            treeNodes.add(new TreeNode(id,pid,title,href,icon,spread));
        }

        return TreeNodeBuilder.build(treeNodes,1);
    }

    /**
     * 退出
     */
    @RequestMapping("logout")
    public ResultObj logout(HttpSession session){
        try {
            session.removeAttribute("user");

            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return ResultObj.OUT_SUCCESS;
        }catch (Exception e){
            e.getMessage();
            return ResultObj.OUT_ERROR;
        }
    }
}
