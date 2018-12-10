package com.as.security.shiro;

import com.as.base.domain.LoginUser;

import com.as.security.domain.User;
import com.as.security.service.XAAuthorizationService;
import com.as.security.service.XSUserService;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author who
 */
@Component
public class ShiroDbRealm extends AuthorizingRealm {

    private final XSUserService userService;
    private final XAAuthorizationService authorizationService;

    public ShiroDbRealm(XSUserService userService,
                        XAAuthorizationService authorizationService) {
        setCredentialsMatcher(new PasswordMatcher());
        this.authorizationService = authorizationService;
        this.userService = userService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LoginUser user = (LoginUser) principals.getPrimaryPrincipal();
        Pair<List<String>, List<String>> pair = authorizationService.getStringRolesAndPermissions(user.getId());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(pair.getFirst());
        authorizationInfo.addStringPermissions(pair.getSecond());
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken upToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) upToken;
        User user = userService.findOne(token.getUsername());
        if (user == null) {
            throw new UnknownAccountException(token.getUsername() + "不存在");
        }

        if (user.getDisabled()) {
            throw new LockedAccountException(token.getUsername() + "被禁用");
        }

        LoginUser loginUser = new LoginUser(user.getId(), user.getLoginName(), user.getName());
        return new SimpleAuthenticationInfo(loginUser, user.getPassword(), user.getName());
    }
}
