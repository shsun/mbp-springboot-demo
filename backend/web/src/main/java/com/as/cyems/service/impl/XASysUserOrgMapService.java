package com.as.cyems.service.impl;

import com.as.security.domain.SysOrganization;
import com.as.cyems.mapper.SysOrganizationMapper;
import com.as.cyems.mapper.SysUserOrgMapMapper;
import com.as.cyems.mapper.SysUserMapper;
import com.as.cyems.service.IXASysUserOrgMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class XASysUserOrgMapService implements IXASysUserOrgMapService {
    private static final Logger LOG = LoggerFactory.getLogger(XASysUserOrgMapService.class);

    private final SysOrganizationMapper organizationRepository;
    private final SysUserOrgMapMapper userOrgMapRepository;
    private final SysUserMapper userRepository;


    public XASysUserOrgMapService(SysOrganizationMapper organizationRepository, SysUserOrgMapMapper userOrgMapRepository, SysUserMapper userRepository) {
        this.organizationRepository = organizationRepository;
        this.userOrgMapRepository = userOrgMapRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void assignOrgs(List<Integer> userIds, List<Integer> orgIds) throws Exception {
        List<SysOrganization> orgs;
        for (Integer orgId : orgIds) {
            orgs = organizationRepository.findByParentId(orgId);
            if (orgs != null && !orgs.isEmpty()) {
                //有子节点
                throw new Exception("只能选择没有子节点的组织机构");
            }
        }
        for (Integer userId : userIds) {
            assignOrg(userId, orgIds);
        }
    }

    @Transactional
    public void assignOrg(Integer userId, List<Integer> orgIds) {
        for (Integer orgId : orgIds) {
            userRepository.updateOrg(userId, orgId);
        }
    }


}