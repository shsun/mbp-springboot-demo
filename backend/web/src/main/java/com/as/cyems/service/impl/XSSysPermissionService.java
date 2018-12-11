package com.as.cyems.service.impl;

import com.as.base.event.XEvent;
import com.as.base.event.XEventType;
import com.as.security.domain.SysPermission;
import com.as.security.form.PermissionQueryForm;
import com.as.cyems.mapper.SysPermissionMapper;
import com.as.cyems.service.IXSSysPermissionService;
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
public class XSSysPermissionService extends ServiceImpl<SysPermissionMapper, SysPermission> implements IXSSysPermissionService {


    private static final Logger LOG = LoggerFactory.getLogger(XSSysPermissionService.class);
    private final SysPermissionMapper permissionRepository;
    private final ApplicationEventPublisher publisher;

    public XSSysPermissionService(SysPermissionMapper permissionRepository, ApplicationEventPublisher publisher) {
        this.permissionRepository = permissionRepository;
        this.publisher = publisher;
    }

    @Transactional(readOnly = true)
    public IPage<SysPermission> query(com.baomidou.mybatisplus.extension.plugins.pagination.Page page, PermissionQueryForm params) {
        /*
        if (!isNullOrEmpty(params.getKeyword())) {
            params.setKeyword("%" + params.getKeyword() + "%");
        }
        PageUtils.startPage(pageRequest);
        List<SysPermission> permissions = permissionRepository.findByParams(params);
        return PageUtils.toPage(permissions);
        */
        Wrapper<SysPermission> wrapper = new QueryWrapper<SysPermission>()
                .lambda().like(SysPermission::getDescription, params.getKeyword())
                .or(e -> e.like(SysPermission::getName, params.getKeyword()))
                .or(e -> e.like(SysPermission::getIdentifier, params.getKeyword()))
                .eq(SysPermission::getDisabled, params.getDisabled());
        IPage<SysPermission> p = this.page(page, wrapper);
        return p;
    }


    @Transactional(readOnly = true)
    public List<SysPermission> findAll() {
        // return permissionRepository.findAll();
        return permissionRepository.selectList(null);
    }

    @Transactional
    public SysPermission create(SysPermission permission) {

        Wrapper<SysPermission> wrapper = new QueryWrapper<SysPermission>().lambda().eq(SysPermission::getIdentifier, permission.getIdentifier());
        SysPermission oldPermission = permissionRepository.selectOne(wrapper);

        // SysPermission oldPermission = permissionRepository.findByIdentifier(permission.getIdentifier());
        checkState(oldPermission == null, "标识符为%s的权限已存在", permission.getIdentifier());

        permissionRepository.insert(permission);
        return permission;
    }

    @Transactional
    public SysPermission modify(SysPermission permission) {
        Wrapper<SysPermission> wrapper;

        wrapper = new QueryWrapper<SysPermission>().lambda().eq(SysPermission::getIdentifier, permission.getId());
        SysPermission oldPermission = permissionRepository.selectOne(wrapper);

        // SysPermission oldPermission = permissionRepository.findOne(permission.getId());
        checkNotNull(oldPermission, "ID为%s的权限不存在，无法修改", permission.getId());

        //如果修改了标识符，校验标识符是否已存在
        if (!oldPermission.getIdentifier().equals(permission.getIdentifier())) {
            wrapper = new QueryWrapper<SysPermission>().lambda().eq(SysPermission::getIdentifier, permission.getIdentifier());
            SysPermission perm = permissionRepository.selectOne(wrapper);
            //SysPermission perm = permissionRepository.findByIdentifier(permission.getIdentifier());
            checkState(perm == null, "标识符为%s的权限已存在", permission.getIdentifier());
        }

        this.updateById(permission);
        // permissionRepository.update(permission);
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

        Wrapper<SysPermission> wrapper = new QueryWrapper<SysPermission>().lambda().eq(SysPermission::getId, id);
        SysPermission permission = permissionRepository.selectOne(wrapper);
        // SysPermission permission = permissionRepository.findOne(id);

        checkNotNull(permission, "ID为%s的权限不存在", id);
        LOG.info("删除权限{}", permission);
        permissionRepository.delete(id);

        // FIXME
        // publisher.publishEvent(new RemovedEvent<>(permission));

        XEvent evt = new XEvent<XSSysPermissionService, SysPermission>(this, XEventType.DB_DELETE, permission);
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
            SysPermission oldPermission = permissionRepository.findByIdentifier(s.getIdentifier());
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
        SysPermission permission = null;
        for (PermissionExcel permissionExcel : permissionExcels) {
            permission = new SysPermission();
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
