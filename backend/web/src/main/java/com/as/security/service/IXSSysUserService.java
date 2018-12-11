package com.as.security.service;

import com.as.base.domain.LoginUser;
import com.as.security.domain.SysUser;
import com.as.security.form.UserQueryForm;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface IXSSysUserService {

    public IPage<SysUser> query(com.baomidou.mybatisplus.extension.plugins.pagination.Page page, UserQueryForm params);

    public List<SysUser> findByOrgId(Integer parentId);

    public List<SysUser> findByOrg(Integer parentId);

    public SysUser findOne(String loginName);

    public LoginUser authenticate(String username, String password);

    public SysUser create(SysUser user);

    public SysUser modify(SysUser user);

    public void remove(int[] ids);

    public void remove(int id);

    public void changePassword(List<Integer> ids, String password);

    public void modifyOrg(Integer OrgId, List<Integer> userIds);
}
