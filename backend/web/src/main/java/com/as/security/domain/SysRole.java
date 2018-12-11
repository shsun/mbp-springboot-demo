package com.as.security.domain;


import com.as.base.domain.Nameable;
import com.as.base.domain.XSuperEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * 角色实体.
 *
 * @author who
 */
@Getter
@Setter
@ToString
public class SysRole extends XSuperEntity<SysRole> implements Nameable<Integer> {
// public class SysRole extends AbstractAuditable implements Nameable<Integer> {

    private Integer id;

    /**
     * 标识符，例如在角色中是SalesManager,在
     */
    @NotEmpty(message = "不能为空")
    @SafeHtml(message = "不能包含html标签")
    @Size(min = 4, max = 30, message = "4到30个字符")
    private String identifier;

    /**
     *
     */
    @NotEmpty(message = "不能为空")
    @SafeHtml(message = "不能包含html标签")
    @Size(min = 4, max = 30, message = "4到30个字符")
    private String name;

    /**
     *
     */
    @NotEmpty(message = "不能为空")
    @SafeHtml(message = "不能包含html标签")
    @Size(min = 4, max = 30, message = "4到30个字符")

    /**
     *
     */
    private String description;

    /**
     *
     */
    private Boolean disabled = false;

    /**
     *
     */
    private List<SysPermission> permissions;


    public static SysRole of(String identifier, String name) {
        SysRole role = new SysRole();
        role.setIdentifier(identifier);
        role.setName(name);
        return role;
    }

    private void setId(Integer id) {
        this.id = id;
    }


    @Override
    public boolean isNew() {
        return id == null;
    }
}
