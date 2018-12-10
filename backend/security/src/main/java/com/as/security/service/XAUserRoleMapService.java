package com.as.security.service;

import com.as.base.domain.KendoTreeNode;
import com.as.security.domain.Role;
import com.as.security.domain.User;
import com.as.security.repository.RoleRepository;
import com.as.security.repository.UserRoleMapRepository;
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
public class XAUserRoleMapService {

    private static final Logger LOG = LoggerFactory.getLogger(XAUserRoleMapService.class);
    private final UserRoleMapRepository userRoleMapRepository;
    private final RoleRepository roleRepository;

    public XAUserRoleMapService(UserRoleMapRepository userRoleMapRepository, RoleRepository roleRepository) {
        this.userRoleMapRepository = userRoleMapRepository;
        this.roleRepository = roleRepository;
    }


    @Transactional(readOnly = true)
    public List<KendoTreeNode> getCurrentRoles(List<Integer> userIds) {
        LOG.info("获取用户{}对应角色信息", userIds);
        List<Role> allRoless = roleRepository.findAll();
        List<Role> enabledRoles = allRoless.stream()
                .filter(r -> !r.getDisabled())
                .collect(toList());
        List<Role> userRoles = new ArrayList<>();
        if (userIds != null && userIds.size() == 1) {
            userRoles = userRoleMapRepository.findRolesByUserId(userIds.get(0));
        }
        return KendoTreeNode.toTreeNodes(enabledRoles, userRoles);
    }


    @Transactional(readOnly = true)
    public List<Role> findRolesByUserId(Integer userId) {
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

    public void deleteUser(User user) {
        LOG.info("用户{}被删除，删除其与角色的关联", user);
        userRoleMapRepository.deleteByUserId(user.getId());
    }


    public void deleteRole(Role role) {
        LOG.info("角色{}被删除，删除其与角色的关联", role);
        userRoleMapRepository.deleteByRoleId(role.getId());
    }
}
