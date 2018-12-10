package com.as.cyems.mapper;

import com.as.cyems.SuperMapper;
import com.as.cyems.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface RoleMapper extends SuperMapper<Role> {
}