package com.as.security.domain;


import base.domain.Nameable;
import base.domain.XSuperEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.Size;

/**
 * 权限实体.
 *
 * @author who
 */
@Getter
@Setter
@ToString
//public class Permission extends AbstractAuditable implements Nameable<Integer> {
public class Permission extends XSuperEntity<Permission> implements Nameable<Integer> {

    private Integer id;


    /**
     * 标识符，例如在角色中是SalesManager,在
     */
    @NotEmpty(message = "不能为空")
    @SafeHtml(message = "不能包含html标签")
    @Size(min = 4, max = 30, message = "4到30个字符")
    private String identifier;

    /**
     * 名称
     */
    @NotEmpty(message = "不能为空")
    @SafeHtml(message = "不能包含html标签")
    @Size(min = 4, max = 30, message = "4到30个字符")
    private String name;

    /**
     * 描述
     */
    @SafeHtml(message = "不能包含html标签")
    @Size(min = 4, max = 30, message = "4到30个字符")
    private String description;
    private Boolean disabled = false;


    public static Permission of(String identifier, String name) {
        Permission permission = new Permission();
        permission.setIdentifier(identifier);
        permission.setName(name);
        return permission;
    }


    /**
     * 禁止用户设置id
     *
     * @param id
     */
    private void setId(Integer id) {
        this.id = id;
    }


    @Override
    public boolean isNew() {
        return id == null;
    }
}
