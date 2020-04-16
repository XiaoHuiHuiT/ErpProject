package com.qc.system.mapper;

import com.qc.system.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 唐颖豪
 * @since 2019-12-30
 */
public interface UserMapper extends BaseMapper<User> {

    public Integer queryUserMaxOrderNum();

    /**
     * 保存用户和角色之间的关系 操作sys_user_role
     * @param userId
     * @param rid
     */
    public void saveUserRole(@Param("userId") Integer userId, @Param("rid") Integer rid);
}
