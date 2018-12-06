package com.as.cyems.service.impl;

import java.util.List;

import com.as.cyems.domain.BakUser;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.as.cyems.mapper.BakUserMapper;
import com.as.cyems.service.IBakUserService;

/**
 *
 * BakUser 表数据服务层接口实现类
 *
 */
@Service
public class BakUserServiceImpl extends ServiceImpl<BakUserMapper, BakUser> implements IBakUserService {

	@Override
	public boolean deleteAll() {
		return retBool(baseMapper.deleteAll());
	}

	@Override
	public List<BakUser> selectListBySQL() {
		return baseMapper.selectListBySQL();
	}

	@Override
	public List<BakUser> selectListByWrapper(Wrapper wrapper) {
		return baseMapper.selectListByWrapper(wrapper);
	}
}