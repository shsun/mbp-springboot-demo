package com.as.cyems.service.impl;

import com.as.base.domain.KendoTreeNode;
import com.as.security.domain.SysPermission;
import com.as.security.domain.SysRole;
import com.as.cyems.mapper.SysPermissionMapper;
import com.as.cyems.mapper.SysRolePermissionMapMapper;
import com.as.cyems.service.IXASysRolePermissionMapService;
import com.as.cyems.service.IXSSysPermissionService;
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

    private final SysRolePermissionMapMapper rolePermissionMapRepository;
    private final SysPermissionMapper permissionRepository;
    private final IXSSysPermissionService permissionService;

    public XASysRolePermissionMapService(IXSSysPermissionService permissionService,SysRolePermissionMapMapper rolePermissionMapRepository, SysPermissionMapper permissionRepository) {
        this.permissionService = permissionService;
        this.rolePermissionMapRepository = rolePermissionMapRepository;
        this.permissionRepository = permissionRepository;
    }


    @Transactional(readOnly = true)
    public List<KendoTreeNode> getCurrentPermissions(List<Integer> roleIds) {
        LOG.info("获取角色{}对应权限信息", roleIds);
        //List<SysPermission> allPermissions = this.permissionRepository.findAll();

        List<SysPermission> allPermissions = this.permissionService.findAll();

        List<SysPermission> enabledPermissions = allPermissions.stream()
                .filter(p -> !p.getDisabled())
                .collect(toList());
        List<SysPermission> rolePermissions = Lists.newArrayList();
        if (roleIds != null && roleIds.size() == 1) {
            rolePermissions = this.rolePermissionMapRepository.findPermissionsByRoleId(roleIds.get(0));
        }
        return KendoTreeNode.toTreeNodes(enabledPermissions, rolePermissions);
    }


    @Transactional(readOnly = true)
    public List<SysPermission> getPermissionsByRoleIds(List<Integer> roleIds) {
        List<SysPermission> list;
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


    public void deleteRole(SysRole role) {
        LOG.info("角色{}被删除，删除其与权限的关联", role);
        this.rolePermissionMapRepository.deleteByRoleId(role.getId());
    }

    public void deletePermission(SysPermission permission) {
        LOG.info("权限{}被删除，删除其与角色的关联", permission);
        this.rolePermissionMapRepository.deleteByPermissionId(permission.getId());
    }

    @Transactional(readOnly = true)
    public List<SysPermission> getPermissionsByRole(Integer roleId) {
        LOG.info("获取角色{}对应权限信息", roleId);
        return this.rolePermissionMapRepository.findPermissionsByRoleId(roleId);
    }
}
