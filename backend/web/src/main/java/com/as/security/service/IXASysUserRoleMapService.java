package com.as.security.service;

import com.as.base.domain.KendoTreeNode;
import com.as.security.domain.SysRole;
import com.as.security.domain.TUser;

import java.util.List;

public interface IXASysUserRoleMapService {


    public List<KendoTreeNode> getCurrentRoles(List<Integer> userIds);

    public List<SysRole> findRolesByUserId(Integer userId);

    public void assignRoles(List<Integer> userIds, List<Integer> roleIds);

    public void assignRoles(Integer userId, List<Integer> roleIds);

    public void deleteUser(TUser user);

    public void deleteRole(SysRole role);
}
