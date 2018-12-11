package com.as.security.mapper;

import com.as.security.domain.TUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 用户DAO.
 *
 * @author who
 */
@Component
@Mapper
public interface SysUserMapper extends BaseMapper<TUser> {
    //
//    TUser findOne(Integer id);
//
//    List<TUser> findAll();
//
//    // void insert(TUser permission);
//
//    void update(TUser permission);
//
    void updateOrg(@Param("id") Integer id, @Param("orgId") Integer orgId);

    void updateOrgNull(@Param("orgId") Integer orgId);
//
//    void delete(Integer id);
//
//    TUser findByLoginName(String loginName);
//
//    List<TUser> findByOrgId(Integer id);

    //List<TUser> findByOrg(Integer id);

    // List<TUser> findByParams(UserQueryForm params);

}
