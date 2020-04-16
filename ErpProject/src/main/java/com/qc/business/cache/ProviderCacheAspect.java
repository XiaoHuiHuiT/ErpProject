package com.qc.business.cache;

import com.qc.system.cache.data.DataProvider;
import com.qc.business.domain.Provider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 供应商缓存管理
 */
@Component
@Aspect
public class ProviderCacheAspect {

    private Log log = LogFactory.getLog(ProviderCacheAspect.class);

    // 声明切入点
    private  static  final String PONITCUT_PROVIDER_ADD = "execution(* com.qc.business.service.impl.ProviderServiceImpl.addProvider(..))";
    private  static  final String PONITCUT_PROVIDER_UPDATE = "execution(* com.qc.business.service.impl.ProviderServiceImpl.updateProvider(..))";
    private  static  final String PONITCUT_PROVIDER_GETONE = "execution(* com.qc.business.service.impl.ProviderServiceImpl.getById(..))";
    private  static  final String PONITCUT_PROVIDER_DELETE = "execution(* com.qc.business.service.impl.ProviderServiceImpl.removeById(..))";

    private static final String KEY_PROFIX = "provider:";

    /**
     * 添加的缓存切面
     * @param joinPoint
     * @return
     */
    @Around(value = PONITCUT_PROVIDER_ADD)
    public Object cacheAddProvider(ProceedingJoinPoint joinPoint){
        try {
            Provider provider = (Provider) joinPoint.proceed();// 放行去做添加
            // 把添加完成的供应商对象放到缓存里面
            DataProvider.DATA_CACHE.put(KEY_PROFIX + provider.getId(),provider);
            log.info("供应商对象缓存添加成功:" + KEY_PROFIX + provider.getId());
            return provider;
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("供应商对象缓存添加异常"+e.getMessage());
        }
        return null;
    }

    /**
     * 修改的缓存切面
     * @param joinPoint
     * @return
     */
    @Around(value = PONITCUT_PROVIDER_UPDATE)
    public Object cacheUpdateProvider(ProceedingJoinPoint joinPoint){
        try {
            Provider provider = (Provider) joinPoint.proceed();// 放行去做修改
            // 把修改完成的供应商对象放到缓存里面
            DataProvider.DATA_CACHE.put(KEY_PROFIX + provider.getId(),provider);
            log.info("供应商对象缓存更新成功:" + KEY_PROFIX + provider.getId());
            return provider;
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("供应商对象缓存更新异常"+e.getMessage());
        }
        return null;
    }

    /**
     * 查询一个供应商的缓存切面
     * @param joinPoint
     * @return
     */
    @Around(value = PONITCUT_PROVIDER_GETONE)
    public Object cacheGetOneProvider(ProceedingJoinPoint joinPoint){
        try {
            // 先根据ID去查询缓存
            Serializable id = (Serializable)joinPoint.getArgs()[0];

            Provider cacheProvider = (Provider) DataProvider.DATA_CACHE.get(KEY_PROFIX + id);
            if(null == cacheProvider){
                Provider provider = (Provider) joinPoint.proceed();// 放行去做查询一个供应商信息
                log.info("缓存里面没有供应商对象，去数据库查并放入缓存:" + KEY_PROFIX + provider.getId());

                // 把查询完成的供应商对象放到缓存里面
                DataProvider.DATA_CACHE.put(KEY_PROFIX + provider.getId(),provider);
                return provider;
            }else{
                log.info("缓存里面找到供应商对象:" + KEY_PROFIX + cacheProvider.getId());
                return cacheProvider;
            }
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("供应商对象缓存查询异常"+e.getMessage());
        }
        return null;
    }

    /**
     * 删除供应商的缓存切面
     * @param joinPoint
     * @return
     */
    @Around(value = PONITCUT_PROVIDER_DELETE)
    public Object cacheDeleteProvider(ProceedingJoinPoint joinPoint){
        try {
            // 先根据ID去查询缓存
            Serializable id = (Serializable)joinPoint.getArgs()[0];
            log.info("删除缓存:" + KEY_PROFIX + id);
            DataProvider.DATA_CACHE.remove(KEY_PROFIX + id);

            return joinPoint.proceed();// 放行去做数据库的删除供应商
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("供应商对象缓存删除异常"+e.getMessage());
        }
        return false;
    }

}
