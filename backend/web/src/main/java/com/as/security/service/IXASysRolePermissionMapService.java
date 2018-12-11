package com.as.security.service;

import com.as.base.domain.KendoTreeNode;
import com.as.security.domain.SysPermission;
import com.as.security.domain.SysRole;

import java.util.List;

public interface IXASysRolePermissionMapService {
    /**
     * @param roleIds
     * @return
     */
    List<KendoTreeNode> getCurrentPermissions(List<Integer> roleIds);

    /**
     * @param roleIds
     * @return
     */
    List<SysPermission> getPermissionsByRoleIds(List<Integer> roleIds);

    /**
     * @param roleIds
     * @param permissionIds
     */
    void assignPermissions(List<Integer> roleIds, List<Integer> permissionIds);

    /**
     * @param roleId
     * @param permissionIds
     */
    void assignPermissions(Integer roleId, List<Integer> permissionIds);

    /**
     * @param role
     */
    void deleteRole(SysRole role);

    /**
     * @param permission
     */
    void deletePermission(SysPermission permission);

    /**
     * @param roleId
     * @return
     */
    List<SysPermission> getPermissionsByRole(Integer roleId);
}
