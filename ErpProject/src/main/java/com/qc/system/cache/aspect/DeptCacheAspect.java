package com.qc.system.cache.aspect;

import com.qc.system.cache.data.DataProvider;
import com.qc.system.domain.Dept;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 部门缓存处理
 */
@Component
@Aspect
@EnableAspectJAutoProxy //默认开启的,加不加都可以
public class DeptCacheAspect {

    private Log log = LogFactory.getLog(DeptCacheAspect.class);

    // 声明切入点
    private  static  final String PONITCUT_DEPT_ADD = "execution(* com.qc.system.service.impl.DeptServiceImpl.addDept(..))";
    private  static  final String PONITCUT_DEPT_UPDATE = "execution(* com.qc.system.service.impl.DeptServiceImpl.updateDept(..))";
    private  static  final String PONITCUT_DEPT_GETONE = "execution(* com.qc.system.service.impl.DeptServiceImpl.getById(..))";
    private  static  final String PONITCUT_DEPT_DELETE = "execution(* com.qc.system.service.impl.DeptServiceImpl.removeById(..))";

    private static final String KEY_PROFIX = "dept:";

    /**
     * 添加的缓存切面
     * @param joinPoint
     * @return
     */
    @Around(value = PONITCUT_DEPT_ADD)
    public Object cacheAddDept(ProceedingJoinPoint joinPoint){
        try {
            Dept dept = (Dept) joinPoint.proceed();// 放行去做添加
            // 把添加完成的部门对象放到缓存里面
            DataProvider.DATA_CACHE.put(KEY_PROFIX + dept.getId(),dept);
            log.info("部门对象缓存添加成功:" + KEY_PROFIX + dept.getId());
            return dept;
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("部门对象缓存添加异常"+e.getMessage());
        }
        return null;
    }

    /**
     * 修改的缓存切面
     * @param joinPoint
     * @return
     */
    @Around(value = PONITCUT_DEPT_UPDATE)
    public Object cacheUpdateDept(ProceedingJoinPoint joinPoint){
        try {
            Dept dept = (Dept) joinPoint.proceed();// 放行去做修改
            // 把修改完成的部门对象放到缓存里面
            DataProvider.DATA_CACHE.put(KEY_PROFIX + dept.getId(),dept);
            log.info("部门对象缓存更新成功:" + KEY_PROFIX + dept.getId());
            return dept;
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("部门对象缓存更新异常"+e.getMessage());
        }
        return null;
    }

    /**
     * 查询一个部门的缓存切面
     * @param joinPoint
     * @return
     */
    @Around(value = PONITCUT_DEPT_GETONE)
    public Object cacheGetOneDept(ProceedingJoinPoint joinPoint){
        try {
            // 先根据ID去查询缓存
            Serializable id = (Serializable)joinPoint.getArgs()[0];

            Dept cacheDept = (Dept) DataProvider.DATA_CACHE.get(KEY_PROFIX + id);
            if(null == cacheDept){
                Dept dept = (Dept) joinPoint.proceed();// 放行去做查询一个部门信息
                log.info("缓存里面没有部门对象，去数据库查并放入缓存:" + KEY_PROFIX + dept.getId());

                // 把查询完成的部门对象放到缓存里面
                DataProvider.DATA_CACHE.put(KEY_PROFIX + dept.getId(),dept);
                return dept;
            }else{
                log.info("缓存里面找到部门对象:" + KEY_PROFIX + cacheDept.getId());
                return cacheDept;
            }
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("部门对象缓存查询异常"+e.getMessage());
        }
        return null;
    }

    /**
     * 删除部门的缓存切面
     * @param joinPoint
     * @return
     */
    @Around(value = PONITCUT_DEPT_DELETE)
    public Object cacheDeleteDept(ProceedingJoinPoint joinPoint){
        try {
            // 先根据ID去查询缓存
            Serializable id = (Serializable)joinPoint.getArgs()[0];
            log.info("删除缓存:" + KEY_PROFIX + id);
            DataProvider.DATA_CACHE.remove(KEY_PROFIX + id);

            return joinPoint.proceed();// 放行去做数据库的删除部门
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("部门对象缓存删除异常"+e.getMessage());
        }
        return false;
    }

}