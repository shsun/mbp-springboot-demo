package com.as.security.service;

import java.util.List;

public interface IXASysUserOrgMapService {

    /**
     *
     * @param userIds
     * @param orgIds
     * @throws Exception
     */
    void assignOrgs(List<Integer> userIds, List<Integer> orgIds) throws Exception;

    /**
     *
     * @param userId
     * @param orgIds
     */
    void assignOrg(Integer userId, List<Integer> orgIds);
}
