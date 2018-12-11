package com.as.security.mapper;

import com.as.security.domain.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 角色DAO.
 *
 * @author who
 */
@Component
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

//    SysRole findOne(Integer id);
//
//    List<SysRole> findAll();

    // void insert(SysRole role);
//
//    void update(SysRole role);
//
//    void delete(Integer id);
//
//    List<SysRole> findByParams(RoleQueryForm from);
//
//    SysRole findByIdentifier(String identifier);
}
