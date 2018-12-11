package com.as.security.service;

import com.as.base.domain.LoginUser;
import com.as.security.domain.TUser;
import com.as.security.form.UserQueryForm;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface IXSSysUserService {

    /**
     * @param page
     * @param params
     * @return
     */
    IPage<TUser> query(com.baomidou.mybatisplus.extension.plugins.pagination.Page page, UserQueryForm params);

    /**
     * @param parentId
     * @return
     */
    List<TUser> findByOrgId(Integer parentId);

    /**
     * @param parentId
     * @return
     */
    List<TUser> findByOrg(Integer parentId);

    /**
     * @param loginName
     * @return
     */
    TUser findOne(String loginName);

    /**
     * @param username
     * @param password
     * @return
     */
    LoginUser authenticate(String username, String password);

    /**
     * @param user
     * @return
     */
    TUser create(TUser user);

    /**
     * @param user
     * @return
     */
    TUser modify(TUser user);

    /**
     * @param ids
     */
    void remove(int[] ids);

    /**
     * @param id
     */
    void remove(int id);

    /**
     * @param ids
     * @param password
     */
    void changePassword(List<Integer> ids, String password);

    /**
     * @param OrgId
     * @param userIds
     */
    void modifyOrg(Integer OrgId, List<Integer> userIds);
}
