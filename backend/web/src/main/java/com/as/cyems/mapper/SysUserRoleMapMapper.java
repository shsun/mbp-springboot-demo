package com.as.cyems.mapper;

import com.as.security.domain.SysRole;
import com.as.security.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户-角色映射.
 *
 * @author who
 */
@Component
@Mapper
public interface SysUserRoleMapMapper {

    void insert(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    /**
     * 查找角色下的所有权限.
     */
    List<SysRole> findRolesByUserId(Integer userId);

    /**
     * 删除角色下的所有权限.
     */
    void deleteByUserId(Integer userId);

    /**
     * 查找权限下的所有角色.
     */
    List<SysUser> findUserByRoleId(Integer roleId);

    /**
     * 删除权限下的所有角色.
     */
    void deleteByRoleId(Integer roleId);
}
