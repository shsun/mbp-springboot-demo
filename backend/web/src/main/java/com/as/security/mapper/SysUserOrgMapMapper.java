package com.as.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 用户-组织映射.
 *
 * @author who
 */
@Component
@Mapper
public interface SysUserOrgMapMapper {

//    void insert(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

//    /**
//     * 查找角色下的所有组织.
//     */
//    List<SysOrganization> findOrgsByUserId(Integer userId);
//
//    /**
//     * 删除角色下的所有组织.
//     */
//    void deleteByUserId(Integer userId);
//
//    /**
//     * 查找组织下的所有角色.
//     */
//    List<SysUser> findUserByOrgId(Integer roleId);
//
//    /**
//     * 删除权限下的所有角色.
//     */
//    void deleteByOrgId(Integer roleId);

    /**
     * 单对单
     *
     * @param userId
     * @param roleId
     */
    void updateUserOrg(@Param("userId") Integer userId, @Param("roleId") Integer roleId);
}
