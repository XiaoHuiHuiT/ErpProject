package com.qc.system.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 树节点的封装类
 */

@Data
@NoArgsConstructor
public class TreeNode {

    private Integer id;
    @JsonProperty(value = "parentId")
    private Integer pid;

    private String title;
    private String href;
    private String icon;
    private Boolean spread;

    private List<TreeNode> children = new ArrayList<>();

    // 复选
    private String checkArr = "0";//默认不选中

    /**
     * dtree复选树使用
     * @param id
     * @param pid
     * @param title
     * @param spread
     * @param checkArr
     */
    public TreeNode(Integer id, Integer pid, String title, Boolean spread, String checkArr) {
        super();
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.spread = spread;
        this.checkArr = checkArr;
    }

    /**
     * 首页左边导航菜单树的Json串的构造方法
     * @param id
     * @param pid
     * @param title
     * @param href
     * @param icon
     * @param spread
     */
    public TreeNode(Integer id, Integer pid, String title, String href, String icon, Boolean spread) {
        super();
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.href = href;
        this.icon = icon;
        this.spread = spread;
    }

    /**
     * dtree树的构造器
     * @param id
     * @param pid
     * @param title
     * @param spread
     */
    public TreeNode(Integer id, Integer pid, String title, Boolean spread) {
        super();
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.spread = spread;
    }
}