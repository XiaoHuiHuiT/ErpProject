package com.qc.system.cache.aspect;

import com.qc.system.cache.data.DataProvider;
import com.qc.system.domain.Dept;
import com.qc.system.domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 用户缓存处理
 */
@Component
@Aspect
@EnableAspectJAutoProxy //默认开启的,加不加都可以
public class UserCacheAspect {

    private Log log = LogFactory.getLog(UserCacheAspect.class);

    // 声明切入点
    private  static  final String PONITCUT_USER_ADD = "execution(* com.qc.system.service.impl.UserServiceImpl.addUser(..))";
    private  static  final String PONITCUT_USER_UPDATE = "execution(* com.qc.system.service.impl.UserServiceImpl.updateUser(..))";
    private  static  final String PONITCUT_USER_GETONE = "execution(* com.qc.system.service.impl.UserServiceImpl.getById(..))";
    private  static  final String PONITCUT_USER_DELETE = "execution(* com.qc.system.service.impl.UserServiceImpl.removeById(..))";

    private static final String KEY_PROFIX = "user:";

    /**
     * 添加的缓存切面
     * @param joinPoint
     * @return
     */
    @Around(value = PONITCUT_USER_ADD)
    public Object cacheAddDept(ProceedingJoinPoint joinPoint){
        try {
            Dept user = (Dept) joinPoint.proceed();// 放行去做添加
            // 把添加完成的用户对象放到缓存里面
            DataProvider.DATA_CACHE.put(KEY_PROFIX + user.getId(),user);
            log.info("用户对象缓存添加成功:" + KEY_PROFIX + user.getId());
            return user;
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("用户对象缓存添加异常"+e.getMessage());
        }
        return null;
    }

    /**
     * 修改的缓存切面
     * @param joinPoint
     * @return
     */
    @Around(value = PONITCUT_USER_UPDATE)
    public Object cacheUpdateDept(ProceedingJoinPoint joinPoint){
        try {
            Dept user = (Dept) joinPoint.proceed();// 放行去做修改
            // 把修改完成的用户对象放到缓存里面
            DataProvider.DATA_CACHE.put(KEY_PROFIX + user.getId(),user);
            log.info("用户对象缓存更新成功:" + KEY_PROFIX + user.getId());
            return user;
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("用户对象缓存更新异常"+e.getMessage());
        }
        return null;
    }

    /**
     * 删除用户的缓存切面
     * @param joinPoint
     * @return
     */
    @Around(value = PONITCUT_USER_DELETE)
    public Object cacheDeleteDept(ProceedingJoinPoint joinPoint){
        try {
            // 先根据ID去查询缓存
            Serializable id = (Serializable)joinPoint.getArgs()[0];
            log.info("删除缓存:" + KEY_PROFIX + id);
            DataProvider.DATA_CACHE.remove(KEY_PROFIX + id);

            return joinPoint.proceed();// 放行去做数据库的删除用户
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("用户对象缓存删除异常"+e.getMessage());
        }
        return false;
    }

    /**
     * 查询一个用户的缓存切面
     * @param joinPoint
     * @return
     */
    @Around(value = PONITCUT_USER_GETONE)
    public Object cacheGetOneUser(ProceedingJoinPoint joinPoint){
        try {
            // 先根据ID去查询缓存
            Serializable id = (Serializable)joinPoint.getArgs()[0];

            User cacheUser = (User) DataProvider.DATA_CACHE.get(KEY_PROFIX + id);
            if(null == cacheUser){
                User user = (User) joinPoint.proceed();// 放行去做查询一个用户信息
                if(null != user){
                    log.info("缓存里面没有用户对象，去数据库查并放入缓存:" + KEY_PROFIX + user.getId());

                    // 把查询完成的用户对象放到缓存里面
                    DataProvider.DATA_CACHE.put(KEY_PROFIX + user.getId(),user);
                    return user;
                }
            }else{
                log.info("缓存里面找到用户对象:" + KEY_PROFIX + cacheUser.getId());
                return cacheUser;
            }
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("用户对象缓存查询异常"+e.getMessage());
        }
        return null;
    }
}