package com.as.cyems.service.impl;

import com.as.base.event.XEvent;
import com.as.base.event.XEventType;
import com.as.base.jsignal.IXSignal;
import com.as.base.jsignal.XSignal;
import com.as.security.domain.SysRole;
import com.as.security.form.RoleQueryForm;
import com.as.cyems.mapper.SysRoleMapper;
import com.as.cyems.service.IXSSysRoleService;
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
public class XSSysRoleService extends ServiceImpl<SysRoleMapper, SysRole> implements IXSSysRoleService {

    private static final Logger LOG = LoggerFactory.getLogger(XSSysRoleService.class);
    private final SysRoleMapper roleRepository;
    private final ApplicationEventPublisher publisher;

    private final IXSignal signal4Remove = new XSignal(SysRole.class, String.class);


    public XSSysRoleService(SysRoleMapper roleRepository, ApplicationEventPublisher publisher) {
        this.roleRepository = roleRepository;
        this.publisher = publisher;
    }

    @Transactional(readOnly = true)
    public IPage<SysRole> query(com.baomidou.mybatisplus.extension.plugins.pagination.Page page, RoleQueryForm params) {
        /*
        PageUtils.startPage(pageRequest); //设置分页查询
        List<SysRole> roles = roleRepository.findByParams(form);
        return PageUtils.toPage(roles);
        */
        Wrapper<SysRole> wrapper = new QueryWrapper<SysRole>()
                .lambda().like(SysRole::getDescription, params.getKeyword())
                .or(e -> e.like(SysRole::getName, params.getKeyword()))
                .or(e -> e.like(SysRole::getIdentifier, params.getKeyword()))
                .eq(SysRole::getDisabled, params.getDisabled());
        IPage<SysRole> p = this.page(page, wrapper);
        return p;
    }

    @Transactional
    public SysRole create(SysRole role) {
        SysRole oldRole = roleRepository.findByIdentifier(role.getIdentifier());
        checkState(oldRole == null, "标识符为%s的角色已存在", role.getIdentifier());
        roleRepository.insert(role);
        return role;
    }

    @Transactional
    public SysRole modify(SysRole role) {
        SysRole oldRole = roleRepository.findOne(role.getId());
        checkNotNull(oldRole, "ID为%s的角色不存在，无法修改", role.getId());

        //如果修改了标识符，校验标识符是否已存在
        if (!oldRole.getIdentifier().equals(role.getIdentifier())) {
            SysRole r = roleRepository.findByIdentifier(role.getIdentifier());
            checkState(r == null, "标识符为%s的角色已存在", role.getIdentifier());
        }

        roleRepository.update(role);
        return role;
    }

    @Transactional
    public void remove(int id) {
        SysRole role = roleRepository.findOne(id);
        checkState(role != null, "ID为%s的角色不存在", id);
        LOG.info("删除角色{}", role);

        roleRepository.delete(role.getId());
        // FIXME
        // publisher.publishEvent(new RemovedEvent<SysRole>(role));


        XEvent evt = new XEvent<XSSysRoleService, SysRole>(this, XEventType.DB_DELETE, role);
        publisher.publishEvent(evt);
    }

    @Transactional
    public void remove(int... ids) {
        for (int id : ids) {
            remove(id);
        }
    }

    @Transactional(readOnly = true)
    public List<SysRole> getAll() {
        List<SysRole> list = roleRepository.findAll();
        return list;
    }
}
