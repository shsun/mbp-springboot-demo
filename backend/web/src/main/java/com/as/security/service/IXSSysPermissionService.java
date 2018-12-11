package com.as.security.service;

import com.as.security.domain.SysPermission;
import com.as.security.form.PermissionQueryForm;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface IXSSysPermissionService {

    /**
     * @param page
     * @param params
     * @return
     */
    IPage<SysPermission> query(com.baomidou.mybatisplus.extension.plugins.pagination.Page page, PermissionQueryForm params);

    /**
     * @return
     */
    List<SysPermission> findAll();

    /**
     * @param permission
     * @return
     */
    SysPermission create(SysPermission permission);

    /**
     * @param permission
     * @return
     */
    SysPermission modify(SysPermission permission);

    /**
     * @param ids
     */
    void remove(int... ids);

    /**
     * @param id
     */
    void remove(int id);
}
