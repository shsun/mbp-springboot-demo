package com.as.security.web;

import com.as.base.domain.KendoTreeNode;
import com.as.security.domain.SysOrganization;
import com.as.security.domain.SysUser;
import com.as.security.dto.OrgUserDto;
import com.as.security.form.AssignForm;
import com.as.security.form.ChangePasswordForm;
import com.as.security.form.UserQueryForm;
import com.as.security.service.impl.XASysUserOrgMapService;
import com.as.security.service.impl.XASysUserRoleMapService;
import com.as.security.service.impl.XSSysOrganizationService;
import com.as.security.service.impl.XSSysUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Api(tags = "SysUser的增删改查")
@RestController
@RequestMapping("/security/users")
public class SysUserController extends ApiController {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(SysUserController.class);
    /**
     * service 构造函数注入
     */
    private final XSSysUserService userService;
    private final XASysUserRoleMapService userRoleMapService;
    private final XASysUserOrgMapService userOrgMapService;
    private final XSSysOrganizationService organizationService;

    public SysUserController(XSSysUserService userService, XASysUserRoleMapService userRoleMapService, XASysUserOrgMapService userOrgMapService, XSSysOrganizationService organizationService) {
        this.userService = userService;
        this.userRoleMapService = userRoleMapService;
        this.userOrgMapService = userOrgMapService;
        this.organizationService = organizationService;
    }

    /**
     * 查询
     *
     * @param page 分页
     * @param form 查询条件
     * @return
     */
    @GetMapping
    public IPage<SysUser> query(com.baomidou.mybatisplus.extension.plugins.pagination.Page page, UserQueryForm form) {
        LOG.info("查询用户:{}.{}", page, form);
//        LocalDate
//                LocalDateTime
        return userService.query(page, form);
    }

    /**
     * 创建
     *
     * @param user 入参
     * @return
     */
    @PostMapping
    public SysUser create(@Validated @RequestBody SysUser user) {
        LOG.info("创建用户{}", user);
        userService.create(user);
        return user;
    }

    /**
     * 修改
     *
     * @param user 入参
     * @return
     */
    @PutMapping
    public SysUser modify(@Validated @RequestBody SysUser user) {
        LOG.info("修改用户{}", user);
        userService.modify(user);
        return user;
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> remove(@RequestBody int[] ids) {
        LOG.info("批量删除用户{}", ids);
        userService.remove(ids);
        return R.ok("success");
    }

    /**
     * 修改密码
     *
     * @param form
     * @return
     */
    @PutMapping("/password")
    public R<String> changePassword(@RequestBody ChangePasswordForm form) {
        LOG.info("修改用户密码{}", form);
        userService.changePassword(form.getIds(), form.getPassword());
        return R.ok("success");
    }

    /**
     * 获取当前用户角色
     *
     * @param userIds
     * @return
     */
    @GetMapping("/roles")
    public Map<String, Object> getCurrentRoles(@RequestParam(required = false, name = "ids") List<Integer> userIds) {
        LOG.info("获取用户{}角色信息", userIds);
        List<KendoTreeNode> currentRoles = userRoleMapService.getCurrentRoles(userIds);
        return Collections.singletonMap("content", currentRoles);
    }

    /**
     * 修改当前用户角色
     *
     * @param form
     * @return
     */
    @PostMapping("/roles")
    public R<String> assignRolesToUsers(@Validated @RequestBody AssignForm form) {
        LOG.info("用户角色分配{}", form);
        userRoleMapService.assignRoles(form.getAssigneeIds(), form.getSelectedIds());
        return R.ok("success");
    }

    /**
     * 修改当前用户所属组织
     *
     * @param form
     * @return
     * @throws Exception
     */
    @PostMapping("/orgs")
    public R<String> assignOrgsToUsers(@Validated @RequestBody AssignForm form) throws Exception {
        LOG.info("用户角色分配{}", form);
        try {
            userOrgMapService.assignOrgs(form.getAssigneeIds(), form.getSelectedIds());
        } catch (Exception e) {
            LOG.error("用户角色分配 出错", e);
            throw new Exception(e.getMessage());
        }
        return R.ok("success");
    }

    @GetMapping("/orgs")
    public R<List<OrgUserDto>> query(final @RequestParam(name = "id", required = false) Integer parentId) {
        LOG.info("查询父节点[{}]下的组织机构数据", parentId);

        List<SysOrganization> orgs = organizationService.findByParentId(parentId);
        List<OrgUserDto> dtos = new ArrayList<>(0);
        if (!orgs.isEmpty()) {
            // 子节点是组织
            dtos = orgs.stream()
                    .map((SysOrganization o) -> {
                        OrgUserDto dto = new OrgUserDto("o", true);
                        BeanUtils.copyProperties(o, dto);
                        return dto;
                    }).collect(toList());
        }
        return R.ok(dtos);
    }

}
