package com.as.security.web;

import com.as.security.domain.Organization;
import com.as.security.domain.User;
import com.as.security.dto.OrgDto;
import com.as.security.dto.OrgUserDto;
import com.as.security.service.XSOrganizationService;
import com.as.security.service.XSUserService;
import com.baomidou.mybatisplus.extension.api.R;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/security/orgs")
public class OrganizationController {

    private static final Logger LOG = LoggerFactory.getLogger(OrganizationController.class);
    private final XSOrganizationService organizationService;
    private final XSUserService userService;

    public OrganizationController(XSOrganizationService organizationService, XSUserService userService) {
        this.organizationService = organizationService;
        this.userService = userService;
    }

//    @GetMapping("/view")
//    public String index(){
//        return "/security/organizations";
//    }

    @GetMapping
    public R<List<OrgUserDto>> query(final @RequestParam(name = "id", required = false) Integer parentId) {
        LOG.info("查询父节点[{}]下的组织机构数据", parentId);
        List<Organization> orgs = organizationService.findByParentId(parentId);
        List<OrgUserDto> dtos = Lists.newArrayList();
        if (orgs.isEmpty()) {
            // 子节点是人
            List<User> users = userService.findByOrgId(parentId);
            dtos = users.stream()
                    .map((User o) -> {
                        OrgUserDto dto = new OrgUserDto("u", false);
                        dto.setParentId(parentId);
                        BeanUtils.copyProperties(o, dto);
                        dto.setId(dto.getId() + 1000000000);
                        return dto;
                    }).collect(toList());

        } else {
            // 子节点是组织
            dtos = orgs.stream()
                    .map((Organization o) -> {
                        OrgUserDto dto = new OrgUserDto("o", true);
                        BeanUtils.copyProperties(o, dto);
                        return dto;
                    }).collect(toList());
        }
        return R.ok(dtos);
    }

    @PostMapping
    public R<Organization> create(@Validated @RequestBody OrgDto orgDto) throws Exception {
        LOG.info("创建组织机构{}", orgDto);
        List<User> users = userService.findByOrgId(orgDto.getParentId());
        if (!users.isEmpty()) {
            throw new Exception("当前组织机构下有用户，不能添加子组织机构");
        }
        Organization organization = organizationService.create(orgDto);
        return R.ok(organization);
    }

    @PutMapping
    public R<Organization> modify(@Validated @RequestBody Organization org) {
        LOG.info("修改组织机构{}", org);
        organizationService.modify(org);
        return R.ok(org);
    }

    @DeleteMapping
    public R<String> remove(@RequestBody int[] ids) throws Exception {
        LOG.info("删除组织机构{}", ids);
        //ids只有一个元素，当前只支持单节点删除
        List<Organization> orgs = organizationService.findByParentId(ids[0]);

        if (orgs.isEmpty()) {
            //如果没有子组织结构，删除组织结构时，需要删除人员与组织机构之间的关系
            //首先获取当前节点下的所有用户，
            List<User> users = userService.findByOrgId(ids[0]);
            List<Integer> userIds = users.stream()
                    .map((User o) -> {
                        return o.getId();
                    }).collect(toList());
            userService.modifyOrg(null, userIds);
            //删除组织结构
            organizationService.remove(ids);

        } else {
            //有子组织结构，提示用户需要删除所有子组织机构
            throw new Exception("改组织机构节点下还有未删除的组织机构，请删除！");
        }
        // return SUCCESS_MAP;
        R<String> r = R.ok("success");
        return r;
    }

}
