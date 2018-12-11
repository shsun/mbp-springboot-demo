package com.as.security.listener;

import com.as.base.event.XEvent;
import com.as.base.event.XEventType;
import com.as.security.domain.SysPermission;
import com.as.security.domain.SysRole;
import com.as.security.domain.TUser;
import com.as.security.service.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SecurityEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityEventListener.class);


    private final XASysRolePermissionMapService rolePermissionMapService;
    private final XASysUserRoleMapService userRoleMapService;

    /**
     * @param rolePermissionMapService
     * @param userRoleMapService
     */
    public SecurityEventListener(XASysRolePermissionMapService rolePermissionMapService, XASysUserRoleMapService userRoleMapService) {
        this.rolePermissionMapService = rolePermissionMapService;
        this.userRoleMapService = userRoleMapService;
    }


    @org.springframework.context.event.EventListener
    public void onRoleRemoved(XEvent<XSSysRoleService, SysRole> event) {
        if (event.getEventType().equals(XEventType.DB_DELETE)) {
            SysRole role = event.getData();
            LOG.info("角色{}被删除，删除其与权限的关联", role);
            this.rolePermissionMapService.deleteRole(role);
        }
    }


    @org.springframework.context.event.EventListener
    public void onPermissionRemoved(XEvent<XSSysPermissionService, SysPermission> event) {
        if (event.getEventType().equals(XEventType.DB_DELETE)) {
            SysPermission permission = event.getData();
            LOG.info("权限{}被删除，删除其与角色的关联", permission);
            this.rolePermissionMapService.deletePermission(permission);
        }
    }


    @org.springframework.context.event.EventListener
    public void onUserRemoved(XEvent<XSSysUserService, TUser> event) {
        TUser user = event.getData();
        LOG.info("用户{}被删除，删除其与角色的关联", user);
        userRoleMapService.deleteUser(user);
    }
}
