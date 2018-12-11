package com.as.security.service.impl;

import com.as.security.domain.SysOrganization;
import com.as.security.mapper.SysOrganizationMapper;
import com.as.security.mapper.SysUserMapper;
import com.as.security.mapper.SysUserOrgMapMapper;
import com.as.security.service.IXASysUserOrgMapService;
import com.as.security.service.IXSSysOrganizationService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class XASysUserOrgMapService implements IXASysUserOrgMapService {
    private static final Logger LOG = LoggerFactory.getLogger(XASysUserOrgMapService.class);

    private final SysOrganizationMapper organizationMapper;
    private final SysUserOrgMapMapper userOrgMapMapper;
    private final SysUserMapper userMapper;

    private final IXSSysOrganizationService organizationService;


    public XASysUserOrgMapService(IXSSysOrganizationService organizationService, SysOrganizationMapper organizationRepository, SysUserOrgMapMapper userOrgMapRepository, SysUserMapper userRepository) {
        this.organizationService = organizationService;
        this.organizationMapper = organizationRepository;
        this.userOrgMapMapper = userOrgMapRepository;
        this.userMapper = userRepository;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void assignOrgs(List<Integer> userIds, List<Integer> orgIds) throws Exception {
        List<SysOrganization> orgs = Lists.newArrayList();
        for (Integer orgId : orgIds) {
            // orgs = organizationMapper.findByParentId(orgId);
            orgs = this.organizationService.findByParentId(orgId);
            if (orgs != null && !orgs.isEmpty()) {
                // 有子节点
                throw new Exception("只能选择没有子节点的组织机构");
            }
        }
        for (Integer userId : userIds) {
            this.assignOrg(userId, orgIds);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void assignOrg(Integer userId, List<Integer> orgIds) {
        for (Integer orgId : orgIds) {
            userMapper.updateOrg(userId, orgId);
        }
    }
}