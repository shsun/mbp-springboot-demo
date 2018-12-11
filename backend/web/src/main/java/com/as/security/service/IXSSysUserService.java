package com.as.security.service;

import com.as.base.domain.LoginUser;
import com.as.security.domain.TUser;
import com.as.security.form.UserQueryForm;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface IXSSysUserService {

    public IPage<TUser> query(com.baomidou.mybatisplus.extension.plugins.pagination.Page page, UserQueryForm params);

    public List<TUser> findByOrgId(Integer parentId);

    public List<TUser> findByOrg(Integer parentId);

    public TUser findOne(String loginName);

    public LoginUser authenticate(String username, String password);

    public TUser create(TUser user);

    public TUser modify(TUser user);

    public void remove(int[] ids);

    public void remove(int id);

    public void changePassword(List<Integer> ids, String password);

    public void modifyOrg(Integer OrgId, List<Integer> userIds);
}
