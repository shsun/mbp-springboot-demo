package com.as.security.service.impl;

import com.as.base.domain.LoginUser;
import com.as.base.event.XEvent;
import com.as.base.event.XEventType;
import com.as.security.domain.SysUser;
import com.as.security.form.UserQueryForm;
import com.as.security.mapper.SysUserMapper;
import com.as.security.service.IXSSysUserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class XSSysUserService extends ServiceImpl<SysUserMapper, SysUser> implements IXSSysUserService {

    private static final Logger LOG = LoggerFactory.getLogger(XSSysUserService.class);
    private final SysUserMapper userRepository;
    private final PasswordService passwordService = new DefaultPasswordService();

    private final ApplicationEventPublisher publisher;

    // @Value("${password.default}")
    private String defaultPassword;

    public XSSysUserService(SysUserMapper userRepository, ApplicationEventPublisher publisher) {
        this.userRepository = userRepository;
        this.publisher = publisher;
    }

    @Transactional(readOnly = true)
    public IPage<SysUser> query(com.baomidou.mybatisplus.extension.plugins.pagination.Page page, UserQueryForm params) {
        /*
        if (!isNullOrEmpty(params.getKeyword())) {
            params.setKeyword("%" + params.getKeyword() + "%");
        }
        PageUtils.startPage(pageRequest); //设置分页
        List<SysUser> permissions = userRepository.findByParams(params);
        return PageUtils.toPage(permissions);
        */

        Wrapper<SysUser> wrapper = new QueryWrapper<SysUser>()
                .lambda().like(SysUser::getDescription, params.getKeyword())
                .or(e -> e.like(SysUser::getName, params.getKeyword()))
                .or(e -> e.like(SysUser::getLoginName, params.getKeyword()))
                .eq(SysUser::getDisabled, params.getDisabled());
        IPage<SysUser> p = this.page(page, wrapper);
        return p;

    }

    @Transactional(readOnly = true)
    public List<SysUser> findByOrgId(Integer parentId) {
        return userRepository.findByOrgId(parentId);
    }

    @Transactional(readOnly = true)
    public List<SysUser> findByOrg(Integer parentId) {
        return userRepository.findByOrg(parentId);
    }

    @Transactional(readOnly = true)
    public SysUser findOne(String loginName) {
        return userRepository.findByLoginName(loginName);
    }

    @Transactional(readOnly = true)
    public LoginUser authenticate(String username, String password) {
        SysUser user = userRepository.findByLoginName(username);
        if (user == null) return null;
        boolean matched = passwordService.passwordsMatch(password, user.getPassword());
        if (!matched) return null;
        return new LoginUser(user.getId(), user.getLoginName(), user.getName());
    }

    @Transactional
    public SysUser create(SysUser user) {
        LOG.info("新增用户{}", user.getLoginName());
        SysUser oldUser = userRepository.findByLoginName(user.getLoginName());
        checkState(oldUser == null, "登录名为%s的用户已存在", user.getLoginName());
        String password = isNullOrEmpty(user.getPassword()) ? defaultPassword : user.getPassword();
        String encryptPassword = this.passwordService.encryptPassword(password);
        user.setPassword(encryptPassword);
        userRepository.insert(user);
        return user;
    }

    @Transactional
    public SysUser modify(SysUser user) {
        SysUser oldUser = userRepository.findOne(user.getId());
        checkNotNull(oldUser, "ID为%s的用户不存在，无法修改", user.getId());

        //如果修改了标识符，校验标识符是否已存在
        if (!Objects.equals(oldUser.getLoginName(), user.getLoginName())) {
            SysUser user1 = userRepository.findByLoginName(user.getLoginName());
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
        SysUser user = userRepository.findOne(id);
        checkNotNull(user, "未找到ID为%s的用户", id);
        LOG.info("删除用户{}", user);
        userRepository.delete(id);
        // FIXME
        // publisher.publishEvent(new RemovedEvent<>(user));

        XEvent evt = new XEvent<XSSysUserService, SysUser>(this, XEventType.DB_DELETE, user);
        publisher.publishEvent(evt);
    }

    @Transactional
    public void changePassword(List<Integer> ids, String password) {
        for (int id : ids) {
            SysUser user = userRepository.findOne(id);
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
