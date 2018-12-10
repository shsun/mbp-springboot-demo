package com.as.cyems.service;

import com.as.cyems.domain.User;
import com.as.cyems.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * SysUser 表数据服务层接口
 */
public interface IUserService extends IService<User> {

    /**
     * 删除全部记录
     *
     * @return
     */
    boolean deleteAll();

    /**
     * 该示例示范了不在mapping内填写SQL, 直接把SQL写到mapper内的用法
     *
     * @return
     * @see UserMapper#selectListBySQL()
     */
    List<User> selectListBySQL();

    /**
     * 根据指定条件查找
     *
     * @param wrapper 查询条件
     * @return
     * @see Wrapper
     */
    List<User> selectListByWrapper(Wrapper wrapper);
}