package com.as.security.service;

import com.as.base.domain.KendoTreeNode;
import com.as.security.domain.Permission;
import com.as.security.domain.Role;
import com.as.security.repository.PermissionRepository;
import com.as.security.repository.RolePermissionMapRepository;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author who
 */
@Service
public class XARolePermissionMapService {

    private static final Logger LOG = LoggerFactory.getLogger(XARolePermissionMapService.class);

    private final RolePermissionMapRepository rolePermissionMapRepository;
    private final PermissionRepository permissionRepository;

    public XARolePermissionMapService(RolePermissionMapRepository rolePermissionMapRepository, PermissionRepository permissionRepository) {
        this.rolePermissionMapRepository = rolePermissionMapRepository;
        this.permissionRepository = permissionRepository;
    }


    @Transactional(readOnly = true)
    public List<KendoTreeNode> getCurrentPermissions(List<Integer> roleIds) {
        LOG.info("获取角色{}对应权限信息", roleIds);
        List<Permission> allPermissions = this.permissionRepository.findAll();
        List<Permission> enabledPermissions = allPermissions.stream()
                .filter(p -> !p.getDisabled())
                .collect(toList());
        List<Permission> rolePermissions = Lists.newArrayList();
        if (roleIds != null && roleIds.size() == 1) {
            rolePermissions = this.rolePermissionMapRepository.findPermissionsByRoleId(roleIds.get(0));
        }
        return KendoTreeNode.toTreeNodes(enabledPermissions, rolePermissions);
    }


    @Transactional(readOnly = true)
    public List<Permission> getPermissionsByRoleIds(List<Integer> roleIds) {
        List<Permission> list;
        if (roleIds.isEmpty()) {
            list = Lists.newArrayList();
        } else {
            list = this.rolePermissionMapRepository.findPermissionsByRoleIds(roleIds);
        }
        return list;
    }

    @Transactional
    public void assignPermissions(List<Integer> roleIds, List<Integer> permissionIds) {
        for (Integer roleId : roleIds) {
            this.assignPermissions(roleId, permissionIds);
        }
    }

    @Transactional
    public void assignPermissions(Integer roleId, List<Integer> permissionIds) {
        this.rolePermissionMapRepository.deleteByRoleId(roleId);
        for (Integer permissionId : permissionIds) {
            this.rolePermissionMapRepository.insert(roleId, permissionId);
        }
    }


    public void deleteRole(Role role) {
        LOG.info("角色{}被删除，删除其与权限的关联", role);
        this.rolePermissionMapRepository.deleteByRoleId(role.getId());
    }

    public void deletePermission(Permission permission) {
        LOG.info("权限{}被删除，删除其与角色的关联", permission);
        this.rolePermissionMapRepository.deleteByPermissionId(permission.getId());
    }

    @Transactional(readOnly = true)
    public List<Permission> getPermissionsByRole(Integer roleId) {
        LOG.info("获取角色{}对应权限信息", roleId);
        return this.rolePermissionMapRepository.findPermissionsByRoleId(roleId);
    }
}
