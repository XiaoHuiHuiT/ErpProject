package com.qc.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qc.system.domain.Dept;
import com.qc.system.mapper.DeptMapper;
import com.qc.system.service.IDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qc.system.utils.DataGridView;
import com.qc.system.vo.DeptVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 唐颖豪
 * @since 2020-01-04
 */
@Service
@Transactional
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

    private Log log = LogFactory.getLog(DeptServiceImpl.class);

    @Override
    public DataGridView loadAllDept(DeptVo deptVo) {
        DeptMapper deptMapper = this.getBaseMapper();

        // 分页
        Page<Dept> page = new Page<>(deptVo.getPage(), deptVo.getLimit());
        QueryWrapper<Dept> qw = new QueryWrapper<>();

        // 设置模糊查询条件
        qw.like(StringUtils.isNotBlank(deptVo.getTitle()), "title",deptVo.getTitle());
        qw.like(StringUtils.isNotBlank(deptVo.getAddress()), "address",deptVo.getAddress());
        qw.like(StringUtils.isNotBlank(deptVo.getRemark()), "remark",deptVo.getRemark());
        qw.eq(deptVo.getId() != null, "id",deptVo.getId()).or().eq(deptVo.getId() != null, "pid",deptVo.getId());

        qw.orderByAsc("ordernum");
        deptMapper.selectPage(page,qw);

        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Override
    public List<Dept> queryAllDeptForList(DeptVo deptVo) {
        QueryWrapper<Dept> qw = new QueryWrapper<>();
        qw.eq(deptVo.getAvailable() != null, "available",deptVo.getAvailable());

        return this.baseMapper.selectList(qw);
    }

    @Override
    public Dept updateDept(Dept dept) {
        this.baseMapper.updateById(dept);
        return dept;
    }

    @Override
    public Dept addDept(Dept dept) {
        this.baseMapper.insert(dept);
        return dept;
    }

    @Override
    public Integer queryDeptMaxOrderNum() {
        return this.getBaseMapper().queryDeptMaxOrderNum();
    }

    /**
     * 查询当前部门下面是否有子部门
     * @param id    部门id
     * @return
     */
    @Override
    public Integer queryDeptCountByPid(Integer id) {
        return this.getBaseMapper().queryDeptCountByPid(id);
    }

    @Override
    public Dept getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}
