package com.as.security.listener;

import com.as.base.event.XEvent;
import com.as.base.event.XEventType;
import com.as.security.domain.Permission;
import com.as.security.domain.Role;
import com.as.security.domain.User;
import com.as.security.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SecurityEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityEventListener.class);


    private final XARolePermissionMapService rolePermissionMapService;
    private final XAUserRoleMapService userRoleMapService;

    /**
     * @param rolePermissionMapService
     * @param userRoleMapService
     */
    public SecurityEventListener(XARolePermissionMapService rolePermissionMapService, XAUserRoleMapService userRoleMapService) {
        this.rolePermissionMapService = rolePermissionMapService;
        this.userRoleMapService = userRoleMapService;
    }


    @org.springframework.context.event.EventListener
    public void onRoleRemoved(XEvent<XSRoleService, Role> event) {
        if (event.getEventType().equals(XEventType.DB_DELETE)) {
            Role role = event.getData();
            LOG.info("角色{}被删除，删除其与权限的关联", role);
            this.rolePermissionMapService.deleteRole(role);
        }
    }


    @org.springframework.context.event.EventListener
    public void onPermissionRemoved(XEvent<XSPermissionService, Permission> event) {
        if (event.getEventType().equals(XEventType.DB_DELETE)) {
            Permission permission = event.getData();
            LOG.info("权限{}被删除，删除其与角色的关联", permission);
            this.rolePermissionMapService.deletePermission(permission);
        }
    }


    @org.springframework.context.event.EventListener
    public void onUserRemoved(XEvent<XSUserService, User> event) {
        User user = event.getData();
        LOG.info("用户{}被删除，删除其与角色的关联", user);
        userRoleMapService.deleteUser(user);
    }
}
