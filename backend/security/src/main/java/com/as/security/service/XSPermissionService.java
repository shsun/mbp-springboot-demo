package com.as.security.service;

import base.event.XEvent;
import base.event.XEventType;
import com.as.security.domain.Permission;
import com.as.security.form.PermissionQueryForm;
import com.as.security.repository.PermissionRepository;
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

//import com.as.security.excel.PermissionExcel;

/**
 * 权限服务.
 *
 * @author who
 */
@Service
// public class PermissionService {
public class XSPermissionService extends ServiceImpl<PermissionRepository, Permission> {


    private static final Logger LOG = LoggerFactory.getLogger(XSPermissionService.class);
    private final PermissionRepository permissionRepository;
    private final ApplicationEventPublisher publisher;

    public XSPermissionService(PermissionRepository permissionRepository, ApplicationEventPublisher publisher) {
        this.permissionRepository = permissionRepository;
        this.publisher = publisher;
    }

    @Transactional(readOnly = true)
    public IPage<Permission> query(com.baomidou.mybatisplus.extension.plugins.pagination.Page page, PermissionQueryForm params) {
        /*
        if (!isNullOrEmpty(params.getKeyword())) {
            params.setKeyword("%" + params.getKeyword() + "%");
        }
        PageUtils.startPage(pageRequest);
        List<Permission> permissions = permissionRepository.findByParams(params);
        return PageUtils.toPage(permissions);
        */
        Wrapper<Permission> wrapper = new QueryWrapper<Permission>()
                .lambda().like(Permission::getDescription, params.getKeyword())
                .or(e -> e.like(Permission::getName, params.getKeyword()))
                .or(e -> e.like(Permission::getIdentifier, params.getKeyword()))
                .eq(Permission::getDisabled, params.getDisabled());
        IPage<Permission> p = this.page(page, wrapper);
        return p;
    }


    @Transactional(readOnly = true)
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Transactional
    public Permission create(Permission permission) {
        Permission oldPermission = permissionRepository.findByIdentifier(permission.getIdentifier());
        checkState(oldPermission == null, "标识符为%s的权限已存在", permission.getIdentifier());

        permissionRepository.insert(permission);
        return permission;
    }

    @Transactional
    public Permission modify(Permission permission) {
        Permission oldPermission = permissionRepository.findOne(permission.getId());
        checkNotNull(oldPermission, "ID为%s的权限不存在，无法修改", permission.getId());

        //如果修改了标识符，校验标识符是否已存在
        if (!oldPermission.getIdentifier().equals(permission.getIdentifier())) {
            Permission perm = permissionRepository.findByIdentifier(permission.getIdentifier());
            checkState(perm == null, "标识符为%s的权限已存在", permission.getIdentifier());
        }

        permissionRepository.update(permission);
        return permission;
    }

    @Transactional
    public void remove(int... ids) {
        for (int id : ids) {
            remove(id);
        }
    }

    @Transactional
    public void remove(int id) {
        Permission permission = permissionRepository.findOne(id);
        checkNotNull(permission, "ID为%s的权限不存在", id);
        LOG.info("删除权限{}", permission);
        permissionRepository.delete(id);

        // FIXME
        // publisher.publishEvent(new RemovedEvent<>(permission));

        XEvent evt = new XEvent<XSPermissionService, Permission>(this, XEventType.DB_DELETE, permission);
        publisher.publishEvent(evt);
    }

    /**
     * 校验上传数据是否能够插入数据
     *
     * @param permissionExcels
     */
    /*
    @Transactional
    public void check(List<PermissionExcel> permissionExcels) {
        for (PermissionExcel s : permissionExcels) {
            Permission oldPermission = permissionRepository.findByIdentifier(s.getIdentifier());
            checkState(oldPermission == null, "标识符为%s的权限已存在", s.getIdentifier());
        }
    }
    */

    /**
     * 批量插入数据
     *
     * @param permissionExcels
     */
    /*
    @Transactional()
    public void createcPermissions(List<PermissionExcel> permissionExcels) {
        Permission permission = null;
        for (PermissionExcel permissionExcel : permissionExcels) {
            permission = new Permission();
            BeanUtils.copyProperties(permissionExcel, permission);
            permissionRepository.insert(permission);
        }
    }
    */

    /*
    @Transactional(readOnly = true)
    public List<PermissionExcel> queryExcel(PermissionQueryForm form) {
        List<PermissionExcel> lists = permissionRepository.findByParamsExcel(form);
        checkState(lists.size() != 0, "未查到符合数据");
        return lists;
    }
    */
}
