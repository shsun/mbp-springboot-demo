package com.as.cyems.service.impl;

import com.as.security.domain.SysPermission;
import com.as.security.domain.SysRole;
import com.as.cyems.service.IXASysAuthorizationService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sh
 */
@Service
public class XASysAuthorizationService implements IXASysAuthorizationService {

    private static final Logger LOG = LoggerFactory.getLogger(XASysAuthorizationService.class);


    private final XASysUserRoleMapService userRoleMapService;
    private final XASysRolePermissionMapService rolePermissionMapService;

    /**
     * @param userRoleMapService
     * @param rolePermissionMapService
     */
    public XASysAuthorizationService(XASysUserRoleMapService userRoleMapService, XASysRolePermissionMapService rolePermissionMapService) {
        this.userRoleMapService = userRoleMapService;
        this.rolePermissionMapService = rolePermissionMapService;
    }

    /**
     * @param userId
     * @return
     */
    public Pair<List<SysRole>, List<SysPermission>> getRolesAndPermissions(final Integer userId) {
        List<SysRole> roles = userRoleMapService.findRolesByUserId(userId);
        List<Integer> roleIds = Lists.transform(roles, SysRole::getId);
        List<SysPermission> permissions = rolePermissionMapService.getPermissionsByRoleIds(roleIds);
        return Pair.of(roles, permissions);
    }

    /**
     * @param userId
     * @return
     */
    public Pair<List<String>, List<String>> getStringRolesAndPermissions(final Integer userId) {
        Pair<List<SysRole>, List<SysPermission>> rolesAndPermissions = getRolesAndPermissions(userId);
        List<String> roleStrings = Lists.transform(rolesAndPermissions.getFirst(), SysRole::getIdentifier);
        List<String> permissionStrings = Lists.transform(rolesAndPermissions.getSecond(), SysPermission::getIdentifier);

        return Pair.of(roleStrings, permissionStrings);
    }

}
