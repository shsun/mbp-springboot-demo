package com.as.security.repository;

import com.as.security.domain.Permission;
import com.as.security.domain.Role;
import com.as.security.form.RoleQueryForm;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 角色DAO.
 *
 * @author who
 */
@Component
@Mapper
public interface RoleRepository extends BaseMapper<Role> {

    Role findOne(Integer id);

    List<Role> findAll();

    // void insert(Role role);

    void update(Role role);

    void delete(Integer id);

    List<Role> findByParams(RoleQueryForm from);

    Role findByIdentifier(String identifier);
}
