package com.qc.business.cache;

import com.qc.business.domain.Goods;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.qc.system.cache.data.DataProvider;

import java.io.Serializable;

/**
 * 商品缓存管理
 */
@Component
@Aspect
public class GoodsCacheAspect {

    private Log log = LogFactory.getLog(GoodsCacheAspect.class);

    // 声明切入点
    private  static  final String PONITCUT_GOODS_ADD = "execution(* com.qc.business.service.impl.GoodsServiceImpl.addGoods(..))";
    private  static  final String PONITCUT_GOODS_UPDATE = "execution(* com.qc.business.service.impl.GoodsServiceImpl.updateGoods(..))";
    private  static  final String PONITCUT_GOODS_GETONE = "execution(* com.qc.business.service.impl.GoodsServiceImpl.getById(..))";
    private  static  final String PONITCUT_GOODS_DELETE = "execution(* com.qc.business.service.impl.GoodsServiceImpl.removeById(..))";

    private static final String KEY_PROFIX = "goods:";

    /**
     * 添加的缓存切面
     * @param joinPoint
     * @return
     */
    @Around(value = PONITCUT_GOODS_ADD)
    public Object cacheAddGoods(ProceedingJoinPoint joinPoint){
        try {
            Goods goods = (Goods) joinPoint.proceed();// 放行去做添加
            // 把添加完成的商品对象放到缓存里面
            DataProvider.DATA_CACHE.put(KEY_PROFIX + goods.getId(),goods);
            log.info("商品对象缓存添加成功:" + KEY_PROFIX + goods.getId());
            return goods;
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("商品对象缓存添加异常"+e.getMessage());
        }
        return null;
    }

    /**
     * 修改的缓存切面
     * @param joinPoint
     * @return
     */
    @Around(value = PONITCUT_GOODS_UPDATE)
    public Object cacheUpdateGoods(ProceedingJoinPoint joinPoint){
        try {
            Goods goods = (Goods) joinPoint.proceed();// 放行去做修改
            // 把修改完成的商品对象放到缓存里面
            DataProvider.DATA_CACHE.put(KEY_PROFIX + goods.getId(),goods);
            log.info("商品对象缓存更新成功:" + KEY_PROFIX + goods.getId());
            return goods;
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("商品对象缓存更新异常"+e.getMessage());
        }
        return null;
    }

    /**
     * 查询一个商品的缓存切面
     * @param joinPoint
     * @return
     */
    @Around(value = PONITCUT_GOODS_GETONE)
    public Object cacheGetOneGoods(ProceedingJoinPoint joinPoint){
        try {
            // 先根据ID去查询缓存
            Serializable id = (Serializable)joinPoint.getArgs()[0];

            Goods cacheGoods = (Goods) DataProvider.DATA_CACHE.get(KEY_PROFIX + id);
            if(null == cacheGoods){
                Goods goods = (Goods) joinPoint.proceed();// 放行去做查询一个商品信息
                log.info("缓存里面没有商品对象，去数据库查并放入缓存:" + KEY_PROFIX + goods.getId());

                // 把查询完成的商品对象放到缓存里面
                DataProvider.DATA_CACHE.put(KEY_PROFIX + goods.getId(),goods);
                return goods;
            }else{
                log.info("缓存里面找到商品对象:" + KEY_PROFIX + cacheGoods.getId());
                return cacheGoods;
            }
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("商品对象缓存查询异常"+e.getMessage());
        }
        return null;
    }

    /**
     * 删除商品的缓存切面
     * @param joinPoint
     * @return
     */
    @Around(value = PONITCUT_GOODS_DELETE)
    public Object cacheDeleteGoods(ProceedingJoinPoint joinPoint){
        try {
            // 先根据ID去查询缓存
            Serializable id = (Serializable)joinPoint.getArgs()[0];
            log.info("删除缓存:" + KEY_PROFIX + id);
            DataProvider.DATA_CACHE.remove(KEY_PROFIX + id);

            return joinPoint.proceed();// 放行去做数据库的删除商品
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("商品对象缓存删除异常"+e.getMessage());
        }
        return false;
    }

}
