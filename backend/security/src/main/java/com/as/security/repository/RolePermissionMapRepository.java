package com.as.security.repository;

import com.as.security.domain.Permission;
import com.as.security.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 角色-权限映射.
 *
 * @author who
 */
@Component
@Mapper
public interface RolePermissionMapRepository {

    void insert(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

    /**
     * 查找角色下的所有权限.
     */
    List<Permission> findPermissionsByRoleId(Integer roleId);

    /**
     * 查找角色下的所有权限.
     */
    List<Permission> findPermissionsByRoleIds(List<Integer> roleIds);

    /**
     * 删除角色下的所有权限.
     */
    void deleteByRoleId(Integer roleId);

    /**
     * 查找权限下的所有角色.
     */
    List<Role> findRolesByPermissionId(Integer permissionId);

    /**
     * 删除权限下的所有角色.
     */
    void deleteByPermissionId(Integer permissionId);

}
