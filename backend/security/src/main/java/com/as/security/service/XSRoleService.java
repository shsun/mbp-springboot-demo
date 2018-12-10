package com.as.security.service;

import com.as.base.event.XEvent;
import com.as.base.event.XEventType;
import com.as.base.jsignal.IXSignal;
import com.as.base.jsignal.XSignal;
import com.as.security.domain.Permission;
import com.as.security.domain.Role;
import com.as.security.form.RoleQueryForm;
import com.as.security.repository.RoleRepository;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author who
 */
@Service
public class XSRoleService extends ServiceImpl<RoleRepository, Role> {

    private static final Logger LOG = LoggerFactory.getLogger(XSRoleService.class);
    private final RoleRepository roleRepository;
    private final ApplicationEventPublisher publisher;

    private final IXSignal signal4Remove = new XSignal(Role.class, String.class);


    public XSRoleService(RoleRepository roleRepository, ApplicationEventPublisher publisher) {
        this.roleRepository = roleRepository;
        this.publisher = publisher;
    }

    @Transactional(readOnly = true)
    public IPage<Role> query(com.baomidou.mybatisplus.extension.plugins.pagination.Page page, RoleQueryForm params) {
        /*
        PageUtils.startPage(pageRequest); //设置分页查询
        List<Role> roles = roleRepository.findByParams(form);
        return PageUtils.toPage(roles);
        */
        Wrapper<Role> wrapper = new QueryWrapper<Role>()
                .lambda().like(Role::getDescription, params.getKeyword())
                .or(e -> e.like(Role::getName, params.getKeyword()))
                .or(e -> e.like(Role::getIdentifier, params.getKeyword()))
                .eq(Role::getDisabled, params.getDisabled());
        IPage<Role> p = this.page(page, wrapper);
        return p;
    }

    @Transactional
    public Role create(Role role) {
        Role oldRole = roleRepository.findByIdentifier(role.getIdentifier());
        checkState(oldRole == null, "标识符为%s的角色已存在", role.getIdentifier());
        roleRepository.insert(role);
        return role;
    }

    @Transactional
    public Role modify(Role role) {
        Role oldRole = roleRepository.findOne(role.getId());
        checkNotNull(oldRole, "ID为%s的角色不存在，无法修改", role.getId());

        //如果修改了标识符，校验标识符是否已存在
        if (!oldRole.getIdentifier().equals(role.getIdentifier())) {
            Role r = roleRepository.findByIdentifier(role.getIdentifier());
            checkState(r == null, "标识符为%s的角色已存在", role.getIdentifier());
        }

        roleRepository.update(role);
        return role;
    }

    @Transactional
    public void remove(int id) {
        Role role = roleRepository.findOne(id);
        checkState(role != null, "ID为%s的角色不存在", id);
        LOG.info("删除角色{}", role);

        roleRepository.delete(role.getId());
        // FIXME
        // publisher.publishEvent(new RemovedEvent<Role>(role));


        XEvent evt = new XEvent<XSRoleService, Role>(this, XEventType.DB_DELETE, role);
        publisher.publishEvent(evt);
    }

    @Transactional
    public void remove(int... ids) {
        for (int id : ids) {
            remove(id);
        }
    }

    @Transactional(readOnly = true)
    public List<Role> getAll() {
        List<Role> list = roleRepository.findAll();
        return list;
    }
}
