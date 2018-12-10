package com.as.security.repository;


import com.as.security.domain.Permission;
// import com.as.security.excel.PermissionExcel;
import com.as.security.form.PermissionQueryForm;
import com.as.security.domain.Permission;
import com.as.security.form.PermissionQueryForm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 权限DAO.
 *
 * @author who
 */
@Component
@Mapper
public interface PermissionRepository extends BaseMapper<Permission> {

    Permission findOne(Integer id);

    List<Permission> findAll();

    //void insert(Permission permission);

    void update(Permission permission);

    void delete(Integer id);

    Permission findByIdentifier(String identifier);

    List<Permission> findByParams(PermissionQueryForm params);

    // List<PermissionExcel> findByParamsExcel(PermissionQueryForm params);

    List<Permission> findByRoleId(Integer roleId);
}
