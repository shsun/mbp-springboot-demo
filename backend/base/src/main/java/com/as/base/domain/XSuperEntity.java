package com.as.base.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 *
 * @param <T>
 */
public class XSuperEntity<T extends Model> extends Model<T> {


    /**
     * 主键ID , 这里故意演示注解可以无
     */
    /*
    @TableId("test_id")
    private Long id;
    private Long tenantId;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public XSuperEntity setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

      */

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
