package com.as.cyems.service;

import com.as.security.domain.SysPermission;
import com.as.security.form.PermissionQueryForm;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface IXSSysPermissionService {

    public IPage<SysPermission> query(com.baomidou.mybatisplus.extension.plugins.pagination.Page page, PermissionQueryForm params);

    public List<SysPermission> findAll();

    public SysPermission create(SysPermission permission);

    public SysPermission modify(SysPermission permission);

    public void remove(int... ids);

    public void remove(int id);

}
