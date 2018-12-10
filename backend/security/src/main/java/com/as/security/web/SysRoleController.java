package com.as.security.web;

import com.as.base.domain.KendoTreeNode;
import com.as.security.domain.SysRole;
import com.as.security.form.AssignForm;
import com.as.security.form.RoleQueryForm;
import com.as.security.service.impl.XASysRolePermissionMapService;
import com.as.security.service.impl.XSSysRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Api(tags = "SysRole的增删改查")
@Controller
@RequestMapping("/security/roles")
public class SysRoleController extends ApiController {

    private static final Logger LOG = LoggerFactory.getLogger(SysRoleController.class);

    private final XSSysRoleService roleService;

    private final XASysRolePermissionMapService rolePermissionMapService;

    public SysRoleController(XSSysRoleService roleService, XASysRolePermissionMapService rolePermissionMapService) {
        this.roleService = roleService;
        this.rolePermissionMapService = rolePermissionMapService;
    }

//    @RequestMapping("/view")
//    public String index(){
//        return "/security/roles";
//    }

    @GetMapping
    @ResponseBody
    public R<IPage<SysRole>> query(com.baomidou.mybatisplus.extension.plugins.pagination.Page page, RoleQueryForm form) {
        LOG.info("查询角色:{}:{}", page, form);
        IPage<SysRole> p = roleService.query(page, form);
        return R.ok(p);
    }

    @PostMapping
    @ResponseBody
    public R<SysRole> create(@Validated @RequestBody SysRole role) {
        LOG.info("创建角色:{}", role);
        SysRole r = roleService.create(role);
        return R.ok(r);
    }

    @PutMapping
    @ResponseBody
    public R<SysRole> modify(@Validated @RequestBody SysRole role) {
        LOG.info("修改角色:{}", role);
        SysRole r = roleService.modify(role);
        return R.ok(r);
    }

    @DeleteMapping
    @ResponseBody
    public void remove(@RequestBody int[] ids) {
        LOG.info("删除角色:{}", ids);
        roleService.remove(ids);
    }

    @GetMapping("/permissions")
    @ResponseBody
    public Map<String, Object> getCurrentPermissions(@RequestParam(required = false, name = "ids") List<Integer> roleIds) {
        LOG.info("获取角色{}对应权限信息", roleIds);
        List<KendoTreeNode> permissions = rolePermissionMapService.getCurrentPermissions(roleIds);
        return Collections.singletonMap("content", permissions);
    }

    @PostMapping("/permissions")
    @ResponseBody
    public R<String> assignPermissionsToRoles(@Validated @RequestBody AssignForm form) {
        LOG.info("为角色分配权限{}", form);
        rolePermissionMapService.assignPermissions(form.getAssigneeIds(), form.getSelectedIds());
        return R.ok("success");
    }

    @GetMapping("/all")
    @ResponseBody
    public R<List<SysRole>> getAll() {
        List<SysRole> roles = roleService.getAll();
        // return Collections.singletonMap("content", roles);
        return R.ok(roles);
    }
}
