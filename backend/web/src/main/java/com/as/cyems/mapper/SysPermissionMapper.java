package com.as.cyems.mapper;


import com.as.security.domain.SysPermission;
// import com.as.security.excel.PermissionExcel;
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
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    // SysPermission findOne(Integer id);

    //List<SysPermission> findAll();

    //void insert(SysPermission permission);

    //void update(SysPermission permission);

    void delete(Integer id);

    //SysPermission findByIdentifier(String identifier);

    //List<SysPermission> findByParams(PermissionQueryForm params);

    // List<PermissionExcel> findByParamsExcel(PermissionQueryForm params);

    // List<SysPermission> findByRoleId(Integer roleId);
}
