package com.qc.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 专用的跳转controller
 */
@Controller
@RequestMapping("system")
public class SystemController {

    /**
     * 跳转到系统主页
     * @return
     */
    @RequestMapping("index")
    public String homePage(){
        return "system/index";
    }

    /**
     * 跳转到首页工作台
     * @return
     */
    @RequestMapping("toDeskManager")
    public String toDeskManager(){
        return "system/deskManager";
    }

    /**
     * 跳转到系统公告管理页面
     * @return
     */
    @RequestMapping("toNoticeManager")
    public String toNoticeManager(){
        return "system/notice/noticeManager";
    }

    /**
     * 跳转到系统登陆日志管理页面
     * @return
     */
    @RequestMapping("toLoginfoManager")
    public String toLoginfoManager(){
        return "system/loginfo/loginfoManager";
    }

    /**
     * 跳转到部门管理页面
     * @return
     */
    @RequestMapping("toDeptManager")
    public String toDeptManager(){
        return "system/dept/deptManager";
    }

    /**
     * 跳转到部门管理左边的树页面
     * @return
     */
    @RequestMapping("toDeptLeftManager")
    public String toDeptLeftManager(){
        return "system/dept/deptLeftManager";
    }

    /**
     * 跳转到部门管理右边的数据列表页面
     * @return
     */
    @RequestMapping("toDeptRightManager")
    public String toDeptRightManager(){
        return "system/dept/deptRightManager";
    }

    /**
     * 跳转到菜单管理页面
     * @return
     */
    @RequestMapping("toMenuManager")
    public String toMenuManager(){
        return "system/menu/menuManager";
    }

    /**
     * 跳转到菜单管理左边的树页面
     * @return
     */
    @RequestMapping("toMenuLeftManager")
    public String toMenuLeftManager(){
        return "system/menu/menuLeftManager";
    }

    /**
     * 跳转到菜单管理右边的数据列表页面
     * @return
     */
    @RequestMapping("toMenuRightManager")
    public String toMenuRightManager(){
        return "system/menu/menuRightManager";
    }

    /**
     * 跳转到权限管理页面
     * @return
     */
    @RequestMapping("toPermissionManager")
    public String toPermissionManager(){
        return "system/permission/permissionManager";
    }

    /**
     * 跳转到权限管理左边的树页面
     * @return
     */
    @RequestMapping("toPermissionLeftManager")
    public String toPermissionLeftManager(){
        return "system/permission/permissionLeftManager";
    }

    /**
     * 跳转到权限管理右边的数据列表页面
     * @return
     */
    @RequestMapping("toPermissionRightManager")
    public String toPermissionRightManager(){
        return "system/permission/permissionRightManager";
    }

    /**
     * 跳转到角色管理
     * @return
     */
    @RequestMapping("toRoleManager")
    public String toRoleManager(){
        return "system/role/roleManager";
    }

    /**
     * 跳转到用户管理
     * @return
     */
    @RequestMapping("toUserManager")
    public String toUserManager(){
        return "system/user/userManager";
    }

    /**
     * 跳转到图标管理
     * @return
     */
    @RequestMapping("toIconManager")
    public String toIconManager(){
        return "system/icon/iconManager";
    }

    /**
     * 跳转到缓存管理
     * @return
     */
    @RequestMapping("toCacheManager")
    public String toCacheManager(){
        return "system/cache/cacheManager";
    }

    /**
     * 跳转到用户个人信息的页面
     * @return
     */
    @RequestMapping("toUserInfo")
    public String toUserInfo(){
        return "system/user/userInfo";
    }

    /**
     * 跳转到商品销售
     * @return
     */
    @RequestMapping("toSalesManager")
    public String toSalesManager(){
        return "business/sales/salesManager";
    }
}