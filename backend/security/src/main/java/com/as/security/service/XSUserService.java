package com.as.security.service;

import com.as.base.domain.LoginUser;
import com.as.base.event.XEvent;
import com.as.base.event.XEventType;
import com.as.security.domain.User;
import com.as.security.form.UserQueryForm;
import com.as.security.repository.UserRepository;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * 用户服务.
 *
 * @author who
 */
@Service
public class XSUserService extends ServiceImpl<UserRepository, User> {

    private static final Logger LOG = LoggerFactory.getLogger(XSUserService.class);
    private final UserRepository userRepository;
    private final PasswordService passwordService = new DefaultPasswordService();

    private final ApplicationEventPublisher publisher;

    @Value("${password.default}")
    private String defaultPassword;

    public XSUserService(UserRepository userRepository, ApplicationEventPublisher publisher) {
        this.userRepository = userRepository;
        this.publisher = publisher;
    }

    @Transactional(readOnly = true)
    public IPage<User> query(com.baomidou.mybatisplus.extension.plugins.pagination.Page page, UserQueryForm params) {
        /*
        if (!isNullOrEmpty(params.getKeyword())) {
            params.setKeyword("%" + params.getKeyword() + "%");
        }
        PageUtils.startPage(pageRequest); //设置分页
        List<User> permissions = userRepository.findByParams(params);
        return PageUtils.toPage(permissions);
        */

        Wrapper<User> wrapper = new QueryWrapper<User>()
                .lambda().like(User::getDescription, params.getKeyword())
                .or(e -> e.like(User::getName, params.getKeyword()))
                .or(e -> e.like(User::getLoginName, params.getKeyword()))
                .eq(User::getDisabled, params.getDisabled());
        IPage<User> p = this.page(page, wrapper);
        return p;

    }

    @Transactional(readOnly = true)
    public List<User> findByOrgId(Integer parentId) {
        return userRepository.findByOrgId(parentId);
    }

    @Transactional(readOnly = true)
    public List<User> findByOrg(Integer parentId) {
        return userRepository.findByOrg(parentId);
    }

    @Transactional(readOnly = true)
    public User findOne(String loginName) {
        return userRepository.findByLoginName(loginName);
    }

    @Transactional(readOnly = true)
    public LoginUser authenticate(String username, String password) {
        User user = userRepository.findByLoginName(username);
        if (user == null) return null;
        boolean matched = passwordService.passwordsMatch(password, user.getPassword());
        if (!matched) return null;
        return new LoginUser(user.getId(), user.getLoginName(), user.getName());
    }

    @Transactional
    public User create(User user) {
        LOG.info("新增用户{}", user.getLoginName());
        User oldUser = userRepository.findByLoginName(user.getLoginName());
        checkState(oldUser == null, "登录名为%s的用户已存在", user.getLoginName());
        String password = isNullOrEmpty(user.getPassword()) ? defaultPassword : user.getPassword();
        String encryptPassword = this.passwordService.encryptPassword(password);
        user.setPassword(encryptPassword);
        userRepository.insert(user);
        return user;
    }

    @Transactional
    public User modify(User user) {
        User oldUser = userRepository.findOne(user.getId());
        checkNotNull(oldUser, "ID为%s的用户不存在，无法修改", user.getId());

        //如果修改了标识符，校验标识符是否已存在
        if (!Objects.equals(oldUser.getLoginName(), user.getLoginName())) {
            User user1 = userRepository.findByLoginName(user.getLoginName());
            checkState(user1 == null, "标识符为%s的用户已存在", user.getLoginName());
        }
        // 不修改密码
        user.setPassword(oldUser.getPassword());
        userRepository.update(user);
        return user;
    }

    @Transactional
    public void remove(int[] ids) {
        for (int id : ids) {
            this.remove(id);
        }
    }

    @Transactional
    public void remove(int id) {
        User user = userRepository.findOne(id);
        checkNotNull(user, "未找到ID为%s的用户", id);
        LOG.info("删除用户{}", user);
        userRepository.delete(id);
        // FIXME
        // publisher.publishEvent(new RemovedEvent<>(user));

        XEvent evt = new XEvent<XSUserService, User>(this, XEventType.DB_DELETE, user);
        publisher.publishEvent(evt);
    }

    @Transactional
    public void changePassword(List<Integer> ids, String password) {
        for (int id : ids) {
            User user = userRepository.findOne(id);
            checkNotNull(user, "未找到ID为%s的用户", id);
            String encryptPassword = passwordService.encryptPassword(password);
            user.setPassword(encryptPassword);
            userRepository.update(user);
        }
    }

    @Transactional
    public void modifyOrg(Integer OrgId, List<Integer> userIds) {
        // userRepository.updateOrg().deleteByUserId(userId);
        userRepository.updateOrgNull(OrgId);
        for (Integer userId : userIds) {
            userRepository.updateOrg(userId, OrgId);
        }
    }

}
