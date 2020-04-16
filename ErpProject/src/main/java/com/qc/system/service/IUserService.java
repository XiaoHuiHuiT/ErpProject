package com.qc.system.service;

import com.qc.system.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qc.system.utils.DataGridView;
import com.qc.system.vo.UserVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 唐颖豪
 * @since 2019-12-30
 */
public interface IUserService extends IService<User> {

    /**
     * 根据登陆名查询用户对象
     * @param username
     * @return
     */
    public User queryUserByUserName(String username);

    public DataGridView loadAllUser(UserVo userVo);

    /**
     * 根据部门ID查询员工集合
     * @param deptid
     * @return
     */
    public List<User> queryUserByDeptId(Integer deptid);

    /**
     * 加载最大排序码
     * @return
     */
    public Integer loadUserMaxOrderNum();

    /**
     * 添加用户
     * @param user
     * @return
     */
    public User addUser(User user);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public User updateUser(User user);

    /**
     * 用户重置密码
     * @param id
     */
    public void resetUserPwd(Integer id);

    /**
     * 保存用户和角色之间的关系 操作sys_user_role
     * @param userId
     * @param rids
     */
    public void saveUserRole(Integer userId, Integer[] rids);

    /**
     * 修改个人信息
     * @param user
     */
    public void changeUser(User user);

}