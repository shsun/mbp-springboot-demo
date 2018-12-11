package com.as.security.service;

import com.as.security.domain.SysOrganization;
import com.as.security.dto.OrgDto;

import java.util.List;

public interface IXSSysOrganizationService {

     List<SysOrganization> findAll();

     SysOrganization findOne(Integer id);

     List<SysOrganization> findByParentId(Integer id);

     SysOrganization create(OrgDto orgDto);

     SysOrganization modify(SysOrganization org);

     void remove(int id);

     void remove(int... ids);
}
