package com.as.security.service;

import com.as.security.domain.SysRole;
import com.as.security.form.RoleQueryForm;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;


public interface IXSSysRoleService {
    /**
     * @param page
     * @param params
     * @return
     */
    IPage<SysRole> query(com.baomidou.mybatisplus.extension.plugins.pagination.Page page, RoleQueryForm params);

    /**
     * @param role
     * @return
     */
    SysRole create(SysRole role);

    /**
     * @param role
     * @return
     */
    SysRole modify(SysRole role);

    /**
     * @param id
     */
    void remove(int id);

    /**
     * @param ids
     */
    void remove(int... ids);

    /**
     * @return
     */
    List<SysRole> getAll();
}
