package com.as.security.repository;

import com.as.security.domain.Organization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author who
 */
@Component
@Mapper
public interface OrganizationRepository extends BaseMapper<Organization> {

    Organization findOne(Integer id);

    List<Organization> findAll();

    // void insert(Organization org);

    void update(Organization org);
//    void updateOrg(HashMap<String, String> parm);//更新rog——1、2、......

    void delete(Integer id);

    List<Organization> findByParentId(@Param("id") Integer id);
}
