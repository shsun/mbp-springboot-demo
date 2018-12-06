package com.as.cyems.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.as.cyems.domain.BakUser;

/**
 *
 * BakUser 表数据服务层接口
 *
 */
public interface IBakUserService extends IService<BakUser> {

	boolean deleteAll();

	public List<BakUser> selectListBySQL();

	public List<BakUser> selectListByWrapper(Wrapper wrapper);
}