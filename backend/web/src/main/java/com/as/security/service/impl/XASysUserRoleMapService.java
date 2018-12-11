package com.as.security.service.impl;

import com.as.base.domain.KendoTreeNode;
import com.as.security.domain.SysRole;
import com.as.security.domain.TUser;
import com.as.security.mapper.SysRoleMapper;
import com.as.security.mapper.SysUserRoleMapMapper;
import com.as.security.service.IXASysUserRoleMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 维护用户-角色关系.
 *
 * @author who
 */
@Service
public class XASysUserRoleMapService implements IXASysUserRoleMapService {

    private static final Logger LOG = LoggerFactory.getLogger(XASysUserRoleMapService.class);
    private final SysUserRoleMapMapper userRoleMapRepository;
    private final SysRoleMapper roleRepository;

    public XASysUserRoleMapService(SysUserRoleMapMapper userRoleMapRepository, SysRoleMapper roleRepository) {
        this.userRoleMapRepository = userRoleMapRepository;
        this.roleRepository = roleRepository;
    }


    @Transactional(readOnly = true)
    public List<KendoTreeNode> getCurrentRoles(List<Integer> userIds) {
        LOG.info("获取用户{}对应角色信息", userIds);
        List<SysRole> allRoless = roleRepository.findAll();
        List<SysRole> enabledRoles = allRoless.stream()
                .filter(r -> !r.getDisabled())
                .collect(toList());
        List<SysRole> userRoles = new ArrayList<>();
        if (userIds != null && userIds.size() == 1) {
            userRoles = userRoleMapRepository.findRolesByUserId(userIds.get(0));
        }
        return KendoTreeNode.toTreeNodes(enabledRoles, userRoles);
    }


    @Transactional(readOnly = true)
    public List<SysRole> findRolesByUserId(Integer userId) {
        return userRoleMapRepository.findRolesByUserId(userId);
    }

    @Transactional
    public void assignRoles(List<Integer> userIds, List<Integer> roleIds) {
        for (Integer userId : userIds) {
            assignRoles(userId, roleIds);
        }
    }

    @Transactional
    public void assignRoles(Integer userId, List<Integer> roleIds) {
        userRoleMapRepository.deleteByUserId(userId);
        for (Integer roleId : roleIds) {
            userRoleMapRepository.insert(userId, roleId);
        }
    }

    public void deleteUser(TUser user) {
        LOG.info("用户{}被删除，删除其与角色的关联", user);
        userRoleMapRepository.deleteByUserId(user.getId());
    }


    public void deleteRole(SysRole role) {
        LOG.info("角色{}被删除，删除其与角色的关联", role);
        userRoleMapRepository.deleteByRoleId(role.getId());
    }
}
