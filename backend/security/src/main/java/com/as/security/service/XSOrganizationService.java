package com.as.security.service;

import com.as.security.domain.Organization;
import com.as.security.dto.OrgDto;
import com.as.security.repository.OrganizationRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 组织机构service.
 *
 * @author who
 */
@Service
public class XSOrganizationService extends ServiceImpl<OrganizationRepository, Organization> {

    private final OrganizationRepository organizationRepository;

    public XSOrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Transactional(readOnly = true)
    public List<Organization> findAll() {
        List<Organization> list = organizationRepository.findAll();
        return list;
    }

    @Transactional(readOnly = true)
    public Organization findOne(Integer id) {
        Organization organization = organizationRepository.findOne(id);
        return organization;
    }

    @Transactional(readOnly = true)
    public List<Organization> findByParentId(Integer id) {
        List<Organization> list = organizationRepository.findByParentId(id);
        return list;
    }

    @Transactional
    public Organization create(OrgDto orgDto) {
        Organization parent = null;
        // orgDto.setParentId(orgDto.getPareId());
        if (orgDto.getParentId() != null && orgDto.getParentId() != 0) {
            parent = organizationRepository.findOne(orgDto.getParentId());
            checkNotNull(parent, "ID为%s的父节点不存在", orgDto.getParentId());
        }
        Organization org = Organization.of(parent, orgDto.getName(), orgDto.getDescription());
        organizationRepository.insert(org);
        return org;
    }

    @Transactional
    public Organization modify(Organization org) {
        organizationRepository.update(org);
        return org;
    }

    @Transactional
    public void remove(int id) {
        organizationRepository.delete(id);
    }

    @Transactional
    public void remove(int... ids) {
        for (int id : ids) {
            remove(id);
        }
    }
}
