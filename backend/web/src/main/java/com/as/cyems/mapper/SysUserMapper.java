package com.as.cyems.mapper;

import com.as.security.domain.SysUser;
import com.as.security.form.UserQueryForm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户DAO.
 *
 * @author who
 */
@Component
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser findOne(Integer id);

    List<SysUser> findAll();

    // void insert(SysUser permission);

    void update(SysUser permission);

    void updateOrg(@Param("id") Integer id, @Param("orgId") Integer orgId);

    void updateOrgNull(@Param("orgId") Integer orgId);

    void delete(Integer id);

    SysUser findByLoginName(String loginName);

    List<SysUser> findByOrgId(Integer id);

    List<SysUser> findByOrg(Integer id);

    List<SysUser> findByParams(UserQueryForm params);

}
