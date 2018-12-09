package com.as.cyems.domain;

import base.domain.XSuperEntity;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

public class User extends XSuperEntity<User> {

    /**
     * 主键。 如果有主键, 必须写出哪个属性是主键, 没有的主键的话, 则可以不写。
     */
    @TableId("test_id")
    private Long testId;

    private Long tenantId;

    private String roleKey;

    private String name;

    private Integer age;

    private Integer testType;

    private Date testDate;

    private String phone;

    private static final long serialVersionUID = 1L;

    public User() {
        this(null, null);
    }

    public User(String name, String roleKey) {
        this.setName(name);
        this.setRoleKey(roleKey);
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey == null ? null : roleKey.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getTestType() {
        return testType;
    }

    public void setTestType(Integer testType) {
        this.testType = testType;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 该方法必须有
     *
     * @return
     */
    @Override
    protected Serializable pkVal() {
        return this.testId;
    }
}