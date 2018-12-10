package com.as.cyems.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.as.cyems.SuperMapper;
import bak.BakUser;
import org.springframework.stereotype.Component;

/**
 * BakUser 表数据库控制层接口
 */
@Component
@Mapper
public interface BakUserMapper extends SuperMapper<BakUser> {

    /**
     * 自定义注入方法
     */
    int deleteAll();

    @Select("select test_id as id, name, age, test_type from user")
    List<BakUser> selectListBySQL();

    List<BakUser> selectListByWrapper(@Param("ew") Wrapper wrapper);

}