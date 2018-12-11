package com.as.security.service.impl;

import com.as.security.domain.SysOrganization;
import com.as.security.mapper.SysOrganizationMapper;
import com.as.security.mapper.SysUserMapper;
import com.as.security.mapper.SysUserOrgMapMapper;
import com.as.security.service.IXASysUserOrgMapService;
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


    public XASysUserOrgMapService(SysOrganizationMapper organizationRepository, SysUserOrgMapMapper userOrgMapRepository, SysUserMapper userRepository) {
        this.organizationMapper = organizationRepository;
        this.userOrgMapMapper = userOrgMapRepository;
        this.userMapper = userRepository;
    }

    @Override
    @Transactional
    public void assignOrgs(List<Integer> userIds, List<Integer> orgIds) throws Exception {
        List<SysOrganization> orgs;
        for (Integer orgId : orgIds) {
            orgs = organizationMapper.findByParentId(orgId);
            if (orgs != null && !orgs.isEmpty()) {
                //有子节点
                throw new Exception("只能选择没有子节点的组织机构");
            }
        }
        for (Integer userId : userIds) {
            assignOrg(userId, orgIds);
        }
    }

    @Override
    @Transactional
    public void assignOrg(Integer userId, List<Integer> orgIds) {
        for (Integer orgId : orgIds) {
            userMapper.updateOrg(userId, orgId);
        }
    }


}