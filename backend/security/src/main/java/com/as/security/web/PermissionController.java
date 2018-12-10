package com.as.security.web;

import com.as.security.domain.Permission;
import com.as.security.form.PermissionQueryForm;
import com.as.security.service.XARolePermissionMapService;
import com.as.security.service.XSPermissionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "SysPermission的增删改查")
@RestController
@RequestMapping("/security/permissions")
public class PermissionController extends ApiController {

    private static final Logger LOG = LoggerFactory.getLogger(PermissionController.class);
    private final XSPermissionService permissionService;
    private final XARolePermissionMapService rolePermissionMapService;

    public PermissionController(XSPermissionService permissionService, XARolePermissionMapService rolePermissionMapService) {
        this.permissionService = permissionService;
        this.rolePermissionMapService = rolePermissionMapService;
    }

//    @RequestMapping("/view")
//    public String index(){
//        return "/security/permissions";
//    }


    /*
    @GetMapping
    public R<Page<Permission>> query(Pageable pageRequest, PermissionQueryForm params) {
        LOG.info("查询权限数据[{}:{}]", pageRequest, params);
        Page<Permission> page = permissionService.query(pageRequest, params);
        return R.ok(page);
    }
    */

    @GetMapping()
    public R<IPage> query(com.baomidou.mybatisplus.extension.plugins.pagination.Page page, PermissionQueryForm params) {
        IPage<Permission> p = permissionService.query(page, params);
        return R.ok(p);
    }

    @PostMapping
    public R<Permission> create(@Validated @RequestBody Permission permission) {
        LOG.info("创建权限{}", permission);
        Permission p = permissionService.create(permission);
        return R.ok(p);
    }

    @PutMapping
    public R<Permission> modify(@Validated @RequestBody Permission permission) {
        LOG.info("修改权限{}", permission);
        Permission p = permissionService.modify(permission);
        return R.ok(p);
    }

    @DeleteMapping
    public R<String> remove(@RequestBody int[] ids) {
        LOG.info("删除权限{}", ids);
        permissionService.remove(ids);
        // return SUCCESS_MAP;
        R<String> r = R.ok("success");
        return r;
    }

    @GetMapping("/filterByRole")
    public R<List<Permission>> filterByRole(@RequestParam("filter[filters][0][value]") Integer roleId) {
        LOG.info("根据角色ID{}进行过滤", roleId);
        List<Permission> permissions = rolePermissionMapService.getPermissionsByRole(roleId);
        // return Collections.singletonMap("content", permissions);
        R<List<Permission>> r = R.ok(permissions);
        return r;
    }

    /*
    @PostMapping("/excel")
    public List<PermissionExcel> Excel(MultipartFile excel) throws Exception {
        LOG.info("导入权限数据[{}]", excel.getOriginalFilename());
        ExcelReader excelReader = new ExcelReader(excel.getInputStream(), 0);
        return excelReader.read(PermissionExcel.class);
    }
    */

    /*
    @PostMapping("/upload")
    public R<String> upload(@Validated @RequestBody ValidList<PermissionExcel> permissionExcels) throws Exception {
        LOG.info("创建权限{}", permissionExcels);
        permissionService.check(permissionExcels);
        permissionService.createcPermissions(permissionExcels);
        // return SUCCESS_MAP;
        R<String> r = R.ok("success");
        return r;
    }
    */

    /*
    @GetMapping("/excel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, PermissionQueryForm form) throws Exception {
        LOG.info("导出权限[{}]", form.toString());
        List<PermissionExcel> permissionList = permissionService.queryExcel(form);
        Workbook workbook = ExcelWriter.wirteData(permissionList, PermissionExcel.class);
        Servlets.setFileDownloadHeader(request, response, "权限" + LocalDate.now() + ".xls");
        workbook.save(response.getOutputStream(), SaveFormat.EXCEL_97_TO_2003);
    }
    */

}
