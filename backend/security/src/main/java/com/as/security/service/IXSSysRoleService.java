package com.as.security.service;

import com.as.security.domain.SysRole;
import com.as.security.form.RoleQueryForm;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;


public interface IXSSysRoleService {
    public IPage<SysRole> query(com.baomidou.mybatisplus.extension.plugins.pagination.Page page, RoleQueryForm params);

    public SysRole create(SysRole role);

    public SysRole modify(SysRole role);

    public void remove(int id);

    public void remove(int... ids);

    public List<SysRole> getAll();
}
