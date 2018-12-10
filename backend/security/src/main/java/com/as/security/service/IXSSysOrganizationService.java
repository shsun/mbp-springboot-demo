package com.as.security.service;

import com.as.security.domain.SysOrganization;
import com.as.security.dto.OrgDto;

import java.util.List;

public interface IXSSysOrganizationService {

    public List<SysOrganization> findAll();

    public SysOrganization findOne(Integer id);

    public List<SysOrganization> findByParentId(Integer id);

    public SysOrganization create(OrgDto orgDto);

    public SysOrganization modify(SysOrganization org);

    public void remove(int id);

    public void remove(int... ids);
}
