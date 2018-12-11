package com.as.security.mapper;

import com.as.security.domain.SysOrganization;
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
public interface SysOrganizationMapper extends BaseMapper<SysOrganization> {

    SysOrganization findOne(Integer id);

    List<SysOrganization> findAll();

    // void insert(SysOrganization org);

    void update(SysOrganization org);
//    void updateOrg(HashMap<String, String> parm);//更新rog——1、2、......

    void delete(Integer id);

    // List<SysOrganization> findByParentId(@Param("id") Integer id);
}
