package com.qc.business.mapper;

import com.qc.business.domain.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qc.business.domain.Inport;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 唐颖豪
 * @since 2020-01-11
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     *  加载库存不够的商品
     * @return
     */
    public List<Goods> loadGoodsWarningCount();
}
