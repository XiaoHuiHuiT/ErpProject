package com.qc.system.constant;

public interface Constant {

    /**
     * 菜单类型
     */
    String TYPE_MENU = "menu";
    String TYPE_PERMISSION = "permission";

    /**
     * 可用状态
     */
    Integer AVAILABLE_TRUE = 1;
    Integer AVAILABLE_FALSE = 0;

    /**
     * 用户类型
     */
    Integer USER_TYPE_NORMAL = 1;
    Integer USER_TYPE_SUPER = 0;

    /**
     * 用户默认头像
     */
    String USER_DEFAULT_IMAGE = "/resources/images/defaultusertitle.jpg";

    /**
     * 用户默认密码
     */
    String USER_DEFAULT_PWD = "123456";

    /**
     * 商品默认图片
     */
    String GOODS_DEFAULT_IMAGE = "/resources/images/goodsDefault.png";;
}