package com.as.security.service;

import com.as.security.domain.Organization;
import com.as.security.repository.OrganizationRepository;
import com.as.security.repository.UserOrgMapRepository;
import com.as.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class XAUserOrgMapService {
    private static final Logger LOG = LoggerFactory.getLogger(XAUserOrgMapService.class);

    private final OrganizationRepository organizationRepository;
    private final UserOrgMapRepository userOrgMapRepository;
    private final UserRepository userRepository;


    public XAUserOrgMapService(OrganizationRepository organizationRepository, UserOrgMapRepository userOrgMapRepository, UserRepository userRepository) {
        this.organizationRepository = organizationRepository;
        this.userOrgMapRepository = userOrgMapRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void assignOrgs(List<Integer> userIds, List<Integer> orgIds) throws Exception {
        List<Organization> orgs;
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