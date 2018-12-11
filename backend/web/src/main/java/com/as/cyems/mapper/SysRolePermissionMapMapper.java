package com.as.cyems.mapper;

import com.as.security.domain.SysPermission;
import com.as.security.domain.SysRole;
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
public interface SysRolePermissionMapMapper {

    void insert(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

    /**
     * 查找角色下的所有权限.
     */
    List<SysPermission> findPermissionsByRoleId(Integer roleId);

    /**
     * 查找角色下的所有权限.
     */
    List<SysPermission> findPermissionsByRoleIds(List<Integer> roleIds);

    /**
     * 删除角色下的所有权限.
     */
    void deleteByRoleId(Integer roleId);

    /**
     * 查找权限下的所有角色.
     */
    List<SysRole> findRolesByPermissionId(Integer permissionId);

    /**
     * 删除权限下的所有角色.
     */
    void deleteByPermissionId(Integer permissionId);

}
