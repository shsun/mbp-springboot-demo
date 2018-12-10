package com.as.security.repository;

import com.as.security.domain.Permission;
import com.as.security.domain.User;
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
public interface UserRepository extends BaseMapper<User> {

    User findOne(Integer id);

    List<User> findAll();

    // void insert(User permission);

    void update(User permission);

    void updateOrg(@Param("id") Integer id, @Param("orgId") Integer orgId);

    void updateOrgNull(@Param("orgId") Integer orgId);

    void delete(Integer id);

    User findByLoginName(String loginName);

    List<User> findByOrgId(Integer id);

    List<User> findByOrg(Integer id);

    List<User> findByParams(UserQueryForm params);

}
