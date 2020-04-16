package com.qc.system.mapper;

import com.qc.system.domain.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 唐颖豪
 * @since 2020-01-04
 */
public interface DeptMapper extends BaseMapper<Dept> {

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
    public Integer queryDeptCountByPid(Integer id);
}
