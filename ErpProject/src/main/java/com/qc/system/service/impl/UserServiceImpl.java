package com.qc.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qc.system.constant.Constant;
import com.qc.system.domain.Dept;
import com.qc.system.domain.Permission;
import com.qc.system.domain.User;
import com.qc.system.mapper.DeptMapper;
import com.qc.system.mapper.RoleMapper;
import com.qc.system.mapper.UserMapper;
import com.qc.system.service.IDeptService;
import com.qc.system.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qc.system.utils.DataGridView;
import com.qc.system.utils.FileUploadAndDownUtil;
import com.qc.system.utils.MD5Utils;
import com.qc.system.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 唐颖豪
 * @since 2019-12-30
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService, ApplicationContextAware {

    /**
     * 声明日志输出对象
     */
    private Log log = LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    @Lazy
    private IDeptService deptService;

    @Autowired
    private RoleMapper roleMapper;

    @Value("${upload.upload-root-path}")
    private String uploadRootPath = "F:/upload";

    @Autowired
    private HttpServletRequest request;

    @Override
    public User queryUserByUserName(String username) {

        UserMapper userMapper = this.getBaseMapper();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        if(null == username){
            log.error("用户登陆名不能为空");
            throw new RuntimeException("用户登陆名不能为空");
        }

        queryWrapper.eq("loginname",username);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public DataGridView loadAllUser(UserVo userVo) {
        Page<User> page = new Page<>(userVo.getPage(), userVo.getLimit());

        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("type", Constant.USER_TYPE_NORMAL);
        qw.eq(userVo.getDeptid() != null, "deptid",userVo.getDeptid());
        qw.like(StringUtils.isNotBlank(userVo.getAddress()), "address",userVo.getAddress());

        qw.and(StringUtils.isNotBlank(userVo.getName()), new Consumer<QueryWrapper<User>>(){
            @Override
            public void accept(QueryWrapper<User> t) {
                t.like(StringUtils.isNotBlank(userVo.getName()), "name",userVo.getName()).or()
                .like(StringUtils.isNotBlank(userVo.getName()), "loginname",userVo.getName());
            }
        });

        qw.orderByAsc("hiredate");
        this.getBaseMapper().selectPage(page,qw);

        List<User> list = page.getRecords();
        for (User user : list) {
            Integer deptid = user.getDeptid();
            Integer mgr = user.getMgr();

            // 根据ID查询部门
            Dept dept = this.deptService.getById(deptid);
            user.setDeptname(dept.getTitle());

            // 根据领导ID查询领导名称
            if(null != mgr){
                // 如果直接使用this那么缓存切面不生效
                IUserService userService = context.getBean(IUserService.class);
                User queryUser = userService.getById(mgr);
                user.setLeadername(queryUser.getName());
            }
        }

        return new DataGridView(page.getTotal(),list);
    }

//    根据部门ID查询员工集合
    @Override
    public List<User> queryUserByDeptId(Integer deptid) {
        if(null == deptid){
            return null;
        }else{
            QueryWrapper<User> qw = new QueryWrapper<>();
            qw.eq("type", Constant.USER_TYPE_NORMAL);
            qw.eq(deptid != null, "deptid",deptid);
            return this.getBaseMapper().selectList(qw);
        }
    }

    /**
     * 加载最大排序码
     * @return
     */
    @Override
    public Integer loadUserMaxOrderNum() {
        return this.getBaseMapper().queryUserMaxOrderNum();
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Override
    public User addUser(User user) {
        this.getBaseMapper().insert(user);
        return user;
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Override
    public User updateUser(User user) {
        this.getBaseMapper().updateById(user);
        return user;
    }

    /**
     * 用户重置密码
     * @param id
     */
    @Override
    public void resetUserPwd(Integer id) {
        User user = new User();
        user.setId(id);
        user.setSalt(MD5Utils.getSalt());
        user.setPwd(MD5Utils.md5(Constant.USER_DEFAULT_PWD, user.getSalt(),2));

        this.getBaseMapper().updateById(user);
    }

    /**
     * 保存用户和角色之间的关系 操作sys_user_role
     * @param userId
     * @param rids
     */
    @Override
    public void saveUserRole(Integer userId, Integer[] rids) {
        // 1.根据用户ID删除sys_user_role里面的数据
        this.roleMapper.deleteRoleUserByUserId(userId);

        if(rids != null && rids.length > 0){
            for (Integer rid : rids) {
                this.getBaseMapper().saveUserRole(userId,rid);
            }
        }
    }

    /**
     * 修改个人信息
     * @param user
     */
    @Override
    public void changeUser(User user) {
        //1,处理图片
        String imgpath=user.getImgpath();
        if(!imgpath.equals(Constant.USER_DEFAULT_IMAGE)) {
            if(imgpath.endsWith("_temp")) {
                String path=FileUploadAndDownUtil.changeFileName(uploadRootPath,imgpath);
                user.setImgpath(path);
                //删除原来的图片
                User sessionUser = (User) request.getSession().getAttribute("user");
                FileUploadAndDownUtil.deleteFile(uploadRootPath,sessionUser.getImgpath());
            }
        }
        this.getBaseMapper().updateById(user);
    }

    /**
     * 查询一个用户
     * @param id
     * @return
     */
    @Override
    public User getById(Serializable id) {
        return super.getById(id);
    }

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public boolean removeById(Serializable id) {
        // 根据用户ID删除用户和角色的关系表的数据
        this.roleMapper.deleteRoleUserByUserId(id);

        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            // 根据用户ID删除用户和角色的关系表的数据
            this.roleMapper.deleteRoleUserByUserId(id);
        }

        return super.removeByIds(idList);
    }
}
