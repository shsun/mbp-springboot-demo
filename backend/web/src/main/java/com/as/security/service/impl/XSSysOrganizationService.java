package com.as.security.service.impl;

import com.as.security.domain.SysOrganization;
import com.as.security.dto.OrgDto;
import com.as.security.mapper.SysOrganizationMapper;
import com.as.security.service.IXSSysOrganizationService;
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
public class XSSysOrganizationService extends ServiceImpl<SysOrganizationMapper, SysOrganization> implements IXSSysOrganizationService {

    private final SysOrganizationMapper organizationRepository;

    public XSSysOrganizationService(SysOrganizationMapper organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysOrganization> findAll() {
        List<SysOrganization> list = organizationRepository.findAll();
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public SysOrganization findOne(Integer id) {
        SysOrganization organization = organizationRepository.findOne(id);
        return organization;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysOrganization> findByParentId(Integer id) {
        List<SysOrganization> list = organizationRepository.findByParentId(id);
        return list;
    }

    @Override
    @Transactional
    public SysOrganization create(OrgDto orgDto) {
        SysOrganization parent = null;
        // orgDto.setParentId(orgDto.getPareId());
        if (orgDto.getParentId() != null && orgDto.getParentId() != 0) {
            parent = organizationRepository.findOne(orgDto.getParentId());
            checkNotNull(parent, "ID为%s的父节点不存在", orgDto.getParentId());
        }
        SysOrganization org = SysOrganization.of(parent, orgDto.getName(), orgDto.getDescription());
        organizationRepository.insert(org);
        return org;
    }

    @Override
    @Transactional
    public SysOrganization modify(SysOrganization org) {
        organizationRepository.update(org);
        return org;
    }

    @Override
    @Transactional
    public void remove(int id) {
        organizationRepository.delete(id);
    }

    @Override
    @Transactional
    public void remove(int... ids) {
        for (int id : ids) {
            remove(id);
        }
    }
}
