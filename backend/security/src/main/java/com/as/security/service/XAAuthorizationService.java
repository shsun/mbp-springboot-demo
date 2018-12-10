package com.as.security.service;

import com.as.security.domain.Permission;
import com.as.security.domain.Role;
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
public class XAAuthorizationService {

    private static final Logger LOG = LoggerFactory.getLogger(XAAuthorizationService.class);


    private final XAUserRoleMapService userRoleMapService;
    private final XARolePermissionMapService rolePermissionMapService;

    /**
     * @param userRoleMapService
     * @param rolePermissionMapService
     */
    public XAAuthorizationService(XAUserRoleMapService userRoleMapService, XARolePermissionMapService rolePermissionMapService) {
        this.userRoleMapService = userRoleMapService;
        this.rolePermissionMapService = rolePermissionMapService;
    }

    /**
     * @param userId
     * @return
     */
    public Pair<List<Role>, List<Permission>> getRolesAndPermissions(final Integer userId) {
        List<Role> roles = userRoleMapService.findRolesByUserId(userId);
        List<Integer> roleIds = Lists.transform(roles, Role::getId);
        List<Permission> permissions = rolePermissionMapService.getPermissionsByRoleIds(roleIds);
        return Pair.of(roles, permissions);
    }

    /**
     * @param userId
     * @return
     */
    public Pair<List<String>, List<String>> getStringRolesAndPermissions(final Integer userId) {
        Pair<List<Role>, List<Permission>> rolesAndPermissions = getRolesAndPermissions(userId);
        List<String> roleStrings = Lists.transform(rolesAndPermissions.getFirst(), Role::getIdentifier);
        List<String> permissionStrings = Lists.transform(rolesAndPermissions.getSecond(), Permission::getIdentifier);

        return Pair.of(roleStrings, permissionStrings);
    }

}
