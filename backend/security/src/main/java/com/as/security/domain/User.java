package com.as.security.domain;

import com.as.base.domain.Nameable;
import com.as.base.domain.XSuperEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 用户实体.
 *
 * @author who
 * @SafeHtml 可能报错
 */
@Getter
@Setter
@ToString
public class User extends XSuperEntity<User> implements Nameable<Integer> {
//public class User extends AbstractAuditable implements Persistable<Integer> {

    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private Integer orgid;

    /**
     *
     */
    @NotEmpty(message = "不能为空")
    @SafeHtml(message = "不能包含html标签")
    @Size(min = 4, max = 30, message = "4到30个字符")
    private String loginName;

    /**
     *
     */
    @NotEmpty(message = "不能为空")
    @SafeHtml(message = "不能包含html标签")
    @Size(min = 4, max = 30, message = "4到30个字符")
    private String name;

    /**
     * 手机号
     */
    @Pattern(regexp = "^1\\d{10}", message = "手机号码格式不正确")
    private String mobile;

    /**
     *
     */
    @JsonIgnore
    private String password;

    /**
     *
     */
    private String description;

    /**
     *
     */
    private Boolean disabled = false;

    /**
     * 组织机构
     */
    private Integer orgId;
    /**
     *
     */
    private Boolean checked = false;


    public static User of(String loginName, String name) {
        User user = new User();
        user.setLoginName(loginName);
        user.setName(name);
        return user;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
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
