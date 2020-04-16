package com.qc.system.controller;


import com.qc.system.cache.data.DataProvider;
import com.qc.system.utils.DataGridView;
import com.qc.system.utils.ResultObj;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 唐颖豪
 * @since 2020-01-04
 */
@RestController
@RequestMapping("cache")
public class CacheController {

    private Log log = LogFactory.getLog(CacheController.class);

    /**
     * 全查询
     * @return
     */
    @RequestMapping("loadAllCache")
    public DataGridView loadAllCache(){
        List<Map<String,Object>> data = new ArrayList<>();

        Set<Map.Entry<String, Object>> entries = DataProvider.DATA_CACHE.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            Map<String,Object> map = new HashMap<>();

            map.put("key",entry.getKey());
            map.put("value",entry.getValue().toString());
            data.add(map);
        }

        return new DataGridView(data);
    }

    /**
     * 删除
     * @param key
     * @return
     */
    @RequestMapping("deleteCache")
    public ResultObj deleteCache(String key){
        try{
            DataProvider.DATA_CACHE.remove(key);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 清空
     * @return
     */
    @RequestMapping("deleteAllCache")
    public ResultObj deleteAllCache(){
        try{
            DataProvider.DATA_CACHE.clear();
            return ResultObj.CLEAR_SUCCESS;
        }catch (Exception e){
            log.error("清空失败");
            return ResultObj.CLEAR_ERROR;
        }
    }

    /**
     * 批量删除
     * @param keys
     * @return
     */
    @RequestMapping("batchDeleteCache")
    public ResultObj batchDeleteCache(String[] keys){
        try{
            for (String key : keys) {
                DataProvider.DATA_CACHE.remove(key);
            }
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }
    }

}