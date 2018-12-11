package com.as.security.service;

import com.as.base.domain.KendoTreeNode;
import com.as.security.domain.SysRole;
import com.as.security.domain.TUser;

import java.util.List;

public interface IXASysUserRoleMapService {

    /**
     * @param userIds
     * @return
     */
    List<KendoTreeNode> getCurrentRoles(List<Integer> userIds);

    /**
     * @param userId
     * @return
     */
    List<SysRole> findRolesByUserId(Integer userId);

    /**
     * @param userIds
     * @param roleIds
     */
    void assignRoles(List<Integer> userIds, List<Integer> roleIds);

    /**
     * @param userId
     * @param roleIds
     */
    void assignRoles(Integer userId, List<Integer> roleIds);

    /**
     * @param user
     */
    void deleteUser(TUser user);

    /**
     * @param role
     */
    void deleteRole(SysRole role);
}
