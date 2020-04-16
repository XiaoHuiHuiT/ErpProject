package com.qc.system.controller;


import com.qc.system.constant.Constant;
import com.qc.system.domain.Dept;
import com.qc.system.domain.Notice;
import com.qc.system.domain.User;
import com.qc.system.service.IDeptService;
import com.qc.system.utils.DataGridView;
import com.qc.system.utils.ResultObj;
import com.qc.system.utils.TreeNode;
import com.qc.system.vo.DeptVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
 * @since 2020-01-04
 */
@RestController
@RequestMapping("dept")
public class DeptController {

    private Log log = LogFactory.getLog(DeptController.class);

    @Autowired
    private IDeptService deptService;

    /**
     * 全查询
     * @param deptVo
     * @return
     */
    @RequestMapping("loadAllDept")
    public DataGridView loadAllDept(DeptVo deptVo){
        return deptService.loadAllDept(deptVo);
    }

    /**
     * 生成json树
     * @return
     */
    @RequestMapping("loadAllDeptTreeJson")
    public DataGridView loadAllDeptTreeJson(DeptVo deptVo){
        deptVo.setAvailable(Constant.AVAILABLE_TRUE); // 只查可用的
        List<Dept> allDept = this.deptService.queryAllDeptForList(deptVo);

        List<TreeNode> treeNodes = new ArrayList<>();
        for (Dept dept : allDept) {
            Integer id = dept.getId();
            Integer pid = dept.getPid();
            String title = dept.getTitle();
            Boolean spread = dept.getOpen() == 1 ? true : false;
            treeNodes.add(new TreeNode(id, pid, title, spread));
        }

        return new DataGridView(treeNodes);
    }


    /**
     * 添加
     * @param dept
     * @return
     */
    @RequestMapping("addDept")
    public ResultObj addDept(Dept dept){
        dept.setCreatetime(new Date());// 设置时间

        try{
            this.deptService.addDept(dept);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            log.error("添加失败");
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改
     * @param dept
     * @return
     */
    @RequestMapping("updateDept")
    public ResultObj updateDept(Dept dept){
        try{
            this.deptService.updateDept(dept);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            log.error("修改失败");
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("deleteDept")
    public ResultObj deleteDept(Integer id){
        try{
            this.deptService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 加载部门最大的排序码
     * @return
     */
    @RequestMapping("loadDeptMaxOrderNum")
    public DataGridView loadDeptMaxOrderNum(){
        Integer maxOrderNumber = this.deptService.queryDeptMaxOrderNum();

        return new DataGridView(maxOrderNumber+1);
    }

    /**
     * 查询当前部门下面是否有子部门
     * @param id    部门id
     * @return
     */
    @RequestMapping("checkCurrentDeptHasChild")
    public DataGridView checkCurrentDeptHasChild(Integer id){
        Integer count = this.deptService.queryDeptCountByPid(id);
        return new DataGridView(count);
    }

}