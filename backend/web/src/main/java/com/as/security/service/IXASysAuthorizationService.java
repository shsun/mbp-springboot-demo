package com.as.security.service;

import com.as.security.domain.SysPermission;
import com.as.security.domain.SysRole;
import org.springframework.data.util.Pair;

import java.util.List;

public interface IXASysAuthorizationService {
    public Pair<List<SysRole>, List<SysPermission>> getRolesAndPermissions(final Integer userId);

    public Pair<List<String>, List<String>> getStringRolesAndPermissions(final Integer userId);
}
