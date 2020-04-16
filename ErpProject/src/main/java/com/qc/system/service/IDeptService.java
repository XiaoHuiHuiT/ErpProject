package com.qc.system.service;

import com.qc.system.domain.Dept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qc.system.utils.DataGridView;
import com.qc.system.vo.DeptVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 唐颖豪
 * @since 2020-01-04
 */
public interface IDeptService extends IService<Dept> {

    /**
     * 全查询
     * @param deptVo
     * @return
     */
    public DataGridView loadAllDept(DeptVo deptVo);

    /**
     * 全查询返回集合
     * @param deptVo
     * @return
     */
    public List<Dept> queryAllDeptForList(DeptVo deptVo);

    /**
     * 修改
     * @param dept
     * @return
     */
    public Dept updateDept(Dept dept);

    /**
     * 添加
     * @param dept
     * @return
     */
    public Dept addDept(Dept dept);

    /**
     * 加载部门最大的排序码
     * @return
     */
    public Integer queryDeptMaxOrderNum();

    /**
     * 查询当前部门下面是否有子部门
     * @param id    部门id
     * @return
     */
    Integer queryDeptCountByPid(Integer id);
}
