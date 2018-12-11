package com.as.security.service;

import com.as.security.domain.SysPermission;
import com.as.security.domain.SysRole;
import org.springframework.data.util.Pair;

import java.util.List;

public interface IXASysAuthorizationService {
    /**
     * @param userId
     * @return
     */
    Pair<List<SysRole>, List<SysPermission>> getRolesAndPermissions(final Integer userId);

    /**
     * @param userId
     * @return
     */
    Pair<List<String>, List<String>> getStringRolesAndPermissions(final Integer userId);
}
