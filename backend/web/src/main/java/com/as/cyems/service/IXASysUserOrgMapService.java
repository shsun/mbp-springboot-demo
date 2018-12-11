package com.as.cyems.service;

import java.util.List;

public interface IXASysUserOrgMapService {

    public void assignOrgs(List<Integer> userIds, List<Integer> orgIds) throws Exception;
    public void assignOrg(Integer userId, List<Integer> orgIds);
}
