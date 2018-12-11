package com.as.security.service.impl;

import com.as.base.domain.LoginUser;
import com.as.base.event.XEvent;
import com.as.base.event.XEventType;
import com.as.security.domain.TUser;
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
public class XSSysUserService extends ServiceImpl<SysUserMapper, TUser> implements IXSSysUserService {

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

    @Override
    @Transactional(readOnly = true)
    public IPage<TUser> query(com.baomidou.mybatisplus.extension.plugins.pagination.Page page, UserQueryForm params) {
        /*
        if (!isNullOrEmpty(params.getKeyword())) {
            params.setKeyword("%" + params.getKeyword() + "%");
        }
        PageUtils.startPage(pageRequest); //设置分页
        List<TUser> permissions = userRepository.findByParams(params);
        return PageUtils.toPage(permissions);
        */
        Wrapper<TUser> wrapper = new QueryWrapper<TUser>()
                .lambda().like(TUser::getDescription, params.getKeyword())
                .or(e -> e.like(TUser::getName, params.getKeyword()))
                .or(e -> e.like(TUser::getLoginName, params.getKeyword()))
                .eq(TUser::getDisabled, params.getDisabled());
        IPage<TUser> p = this.page(page, wrapper);
        return p;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TUser> findByOrgId(Integer parentId) {
        // return userRepository.findByOrgId(parentId);
        Wrapper<TUser> wrapper = new QueryWrapper<TUser>()
                .lambda()
                .eq(TUser::getOrgId, parentId);
        List<TUser> list = userRepository.selectList(wrapper);
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TUser> findByOrg(Integer parentId) {
        /*
                SELECT * ,
        (CASE WHEN ORG_ID > 0 THEN 'TRUE' ELSE 'FALSE' END) as CHECKED
        FROM T_SECURITY_USERS
        where ORG_ID = #{id} OR ORG_ID IS NULL OR ORG_ID=''
         */
        Wrapper<TUser> wrapper = new QueryWrapper<TUser>()
                .lambda()
                .eq(TUser::getOrgId, parentId);
        List<TUser> list = userRepository.selectList(wrapper);
        return list;
        // return userRepository.findByOrg(parentId);
    }

    @Override
    @Transactional(readOnly = true)
    public TUser findOne(String loginName) {
        // return userRepository.findByLoginName(loginName);
        return this.findByLoginName(loginName);
    }

    private TUser findByLoginName(String loginName) {
        Wrapper<TUser> wrapper = new QueryWrapper<TUser>()
                .lambda()
                .eq(TUser::getLoginName, loginName);
        TUser user = userRepository.selectOne(wrapper);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public LoginUser authenticate(String username, String password) {
        TUser user = this.findByLoginName(username);
        if (user == null) {
            return null;
        }
        boolean matched = passwordService.passwordsMatch(password, user.getPassword());
        if (!matched) {
            return null;
        }
        return new LoginUser(user.getId(), user.getLoginName(), user.getName());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public TUser create(TUser user) {
        LOG.info("新增用户{}", user.getLoginName());
        TUser oldUser = this.findByLoginName(user.getLoginName());
        // userRepository.findByLoginName(user.getLoginName());
        checkState(oldUser == null, "登录名为%s的用户已存在", user.getLoginName());
        String password = isNullOrEmpty(user.getPassword()) ? defaultPassword : user.getPassword();
        String encryptPassword = this.passwordService.encryptPassword(password);
        user.setPassword(encryptPassword);
        userRepository.insert(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public TUser modify(TUser user) {
        TUser oldUser = userRepository.selectById(user.getId());//userRepository.findOne(user.getId());
        checkNotNull(oldUser, "ID为%s的用户不存在，无法修改", user.getId());

        //如果修改了标识符，校验标识符是否已存在
        if (!Objects.equals(oldUser.getLoginName(), user.getLoginName())) {
            TUser user1 = this.findByLoginName(user.getLoginName());
            //userRepository.findByLoginName(user.getLoginName());
            checkState(user1 == null, "标识符为%s的用户已存在", user.getLoginName());
        }
        // 不修改密码
        user.setPassword(oldUser.getPassword());
        //userRepository.update(user);
        this.saveOrUpdate(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void remove(int[] ids) {
        for (int id : ids) {
            this.remove(id);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void remove(int id) {
        TUser user = this.getById(new Integer(id));//userRepository.findOne(id);
        checkNotNull(user, "未找到ID为%s的用户", id);
        LOG.info("删除用户{}", user);
        // userRepository.delete(id);
        this.removeById(new Integer(id));
        // FIXME
        // publisher.publishEvent(new RemovedEvent<>(user));

        XEvent evt = new XEvent<XSSysUserService, TUser>(this, XEventType.DB_DELETE, user);
        publisher.publishEvent(evt);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void changePassword(List<Integer> ids, String password) {
        for (int id : ids) {
            TUser user = this.getById(id);
            // TUser user = userRepository.findOne(id);
            checkNotNull(user, "未找到ID为%s的用户", id);
            String encryptPassword = passwordService.encryptPassword(password);
            user.setPassword(encryptPassword);
            this.saveOrUpdate(user);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void modifyOrg(Integer OrgId, List<Integer> userIds) {
        // userRepository.updateOrg().deleteByUserId(userId);
        userRepository.updateOrgNull(OrgId);
        for (Integer userId : userIds) {
            userRepository.updateOrg(userId, OrgId);
        }
    }

}
