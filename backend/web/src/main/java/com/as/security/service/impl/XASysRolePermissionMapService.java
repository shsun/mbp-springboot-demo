package com.as.security.service.impl;

import com.as.base.domain.KendoTreeNode;
import com.as.security.domain.SysPermission;
import com.as.security.domain.SysRole;
import com.as.security.mapper.SysPermissionMapper;
import com.as.security.mapper.SysRolePermissionMapMapper;
import com.as.security.service.IXASysRolePermissionMapService;
import com.as.security.service.IXSSysPermissionService;
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
public class XASysRolePermissionMapService implements IXASysRolePermissionMapService {

    private static final Logger LOG = LoggerFactory.getLogger(XASysRolePermissionMapService.class);

    private final SysRolePermissionMapMapper rolePermissionMapMapper;
    private final SysPermissionMapper permissionMapper;
    private final IXSSysPermissionService permissionService;

    public XASysRolePermissionMapService(IXSSysPermissionService permissionService, SysRolePermissionMapMapper rolePermissionMapRepository, SysPermissionMapper permissionRepository) {
        this.permissionService = permissionService;
        this.rolePermissionMapMapper = rolePermissionMapRepository;
        this.permissionMapper = permissionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<KendoTreeNode> getCurrentPermissions(List<Integer> roleIds) {
        LOG.info("获取角色{}对应权限信息", roleIds);
        // List<SysPermission> allPermissions = this.permissionMapper.findAll();

        List<SysPermission> allPermissions = this.permissionService.findAll();

        List<SysPermission> enabledPermissions = allPermissions.stream()
                .filter(p -> !p.getDisabled())
                .collect(toList());
        List<SysPermission> rolePermissions = Lists.newArrayList();
        if (roleIds != null && roleIds.size() == 1) {
            rolePermissions = this.rolePermissionMapMapper.findPermissionsByRoleId(roleIds.get(0));
        }
        return KendoTreeNode.toTreeNodes(enabledPermissions, rolePermissions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysPermission> getPermissionsByRoleIds(List<Integer> roleIds) {
        List<SysPermission> list;
        if (roleIds.isEmpty()) {
            list = Lists.newArrayList();
        } else {
            list = this.rolePermissionMapMapper.findPermissionsByRoleIds(roleIds);
        }
        return list;
    }

    @Override
    @Transactional
    public void assignPermissions(List<Integer> roleIds, List<Integer> permissionIds) {
        for (Integer roleId : roleIds) {
            this.assignPermissions(roleId, permissionIds);
        }
    }

    @Override
    @Transactional
    public void assignPermissions(Integer roleId, List<Integer> permissionIds) {
        this.rolePermissionMapMapper.deleteByRoleId(roleId);
        for (Integer permissionId : permissionIds) {
            this.rolePermissionMapMapper.insert(roleId, permissionId);
        }
    }

    @Override
    public void deleteRole(SysRole role) {
        LOG.info("角色{}被删除，删除其与权限的关联", role);
        this.rolePermissionMapMapper.deleteByRoleId(role.getId());
    }

    @Override
    public void deletePermission(SysPermission permission) {
        LOG.info("权限{}被删除，删除其与角色的关联", permission);
        this.rolePermissionMapMapper.deleteByPermissionId(permission.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysPermission> getPermissionsByRole(Integer roleId) {
        LOG.info("获取角色{}对应权限信息", roleId);
        return this.rolePermissionMapMapper.findPermissionsByRoleId(roleId);
    }
}
