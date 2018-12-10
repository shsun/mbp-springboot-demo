package com.as.security.service;

import com.as.base.domain.KendoTreeNode;
import com.as.security.domain.SysPermission;
import com.as.security.domain.SysRole;

import java.util.List;

public interface IXASysRolePermissionMapService {
    public List<KendoTreeNode> getCurrentPermissions(List<Integer> roleIds);
    public List<SysPermission> getPermissionsByRoleIds(List<Integer> roleIds);
    public void assignPermissions(List<Integer> roleIds, List<Integer> permissionIds);
    public void assignPermissions(Integer roleId, List<Integer> permissionIds);
    public void deleteRole(SysRole role);
    public void deletePermission(SysPermission permission);
    public List<SysPermission> getPermissionsByRole(Integer roleId);
}
