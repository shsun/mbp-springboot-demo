package com.as.cyems.mapper;

import com.as.cyems.SuperMapper;
import com.as.cyems.domain.User;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends SuperMapper<User> {

    /**
     * 自定义注入方法
     */
    int deleteAll();

    /**
     * 不在mapper.xml内写SQL
     *
     * @return
     */
    @Select("select test_id as testId, name, age, test_type as testType from user")
    List<User> selectListBySQL();

    /**
     * @param wrapper
     * @return
     */
    List<User> selectListByWrapper(@Param("ew") Wrapper wrapper);
}