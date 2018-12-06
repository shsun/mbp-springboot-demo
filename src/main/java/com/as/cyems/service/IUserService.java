package com.as.cyems.service;

import com.as.cyems.domain.User;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * BakUser 表数据服务层接口
 */
public interface IUserService extends IService<User> {

    boolean deleteAll();

    List<User> selectListBySQL();

    List<User> selectListByWrapper(Wrapper wrapper);
}