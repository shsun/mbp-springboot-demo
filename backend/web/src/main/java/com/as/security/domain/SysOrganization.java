package com.as.security.domain;

import com.as.base.domain.Nameable;
import com.as.base.domain.XSuperEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

/**
 * 组织机构实体.
 *
 * @author who
 */
@Getter
@Setter
@ToString
//public class SysOrganization extends AbstractAuditable implements Nameable<Integer> {
public class SysOrganization extends XSuperEntity<SysOrganization> implements Nameable<Integer> {
    public static final int MAX_LEVEL = 9;

    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private Integer parentId;

    /**
     *
     */
    private Byte level;

    /**
     *
     */
    private Integer childes;

    /**
     *
     */
    @NotEmpty(message = "不能为空")
    @SafeHtml(message = "不能包含html标签")
    @Size(min = 4, max = 30, message = "4到30个字符")

    /**
     * 名称
     */
    private String name;
    @NotEmpty(message = "不能为空")
    @SafeHtml(message = "不能包含html标签")
    @Size(min = 4, max = 30, message = "4到30个字符")
    /**
     * 描述
     */
    private String description;

    /**
     *
     */
    private Integer idOrg1;
    private Integer idOrg2;
    private Integer idOrg3;
    private Integer idOrg4;
    private Integer idOrg5;
    private Integer idOrg6;
    private Integer idOrg7;
    private Integer idOrg8;
    private Integer idOrg9;

    /**
     * @param parentId
     * @param name
     * @return
     */
    public static SysOrganization of(Integer parentId, String name) {
        SysOrganization org = new SysOrganization();
        org.setParentId(parentId);
        org.setName(name);
        return org;
    }

    public static SysOrganization of(SysOrganization parent, String name, String description) {
        SysOrganization org = new SysOrganization();
        if (parent == null) {
            org.setLevel((byte) 1);
        } else {
            checkState(parent.getLevel() != null && parent.getLevel() > 0, "父节点{}层级不能为空", parent);
            org.setLevel((byte) (parent.getLevel() + 1));
            org.setParentId(parent.getId());
            List<Integer> allLevelOrgIds = parent.getAllLevelOrgIds();
            allLevelOrgIds.add(parent.getId());
            org.setAllLevelOrgIds(allLevelOrgIds);
        }
        org.setName(name);
        org.setDescription(description);
        return org;
    }

    private void setId(Integer id) {
        this.id = id;
    }


    @JsonIgnore
    @Override
    public boolean isNew() {
        return id == null;
    }

    /**
     * 获取所有组织机构层级的id
     *
     * @return
     */
    public List<Integer> getAllLevelOrgIds() {
        List<Integer> orgIds = new ArrayList<>();
        if (idOrg1 == null || idOrg1 == 0) {
            return orgIds;
        } else {
            orgIds.add(idOrg1);
        }

        if (idOrg2 == null || idOrg2 == 0) {
            return orgIds;
        } else {
            orgIds.add(idOrg2);
        }

        if (idOrg3 == null || idOrg3 == 0) {
            return orgIds;
        } else {
            orgIds.add(idOrg3);
        }

        if (idOrg4 == null || idOrg4 == 0) {
            return orgIds;
        } else {
            orgIds.add(idOrg4);
        }

        if (idOrg5 == null || idOrg5 == 0) {
            return orgIds;
        } else {
            orgIds.add(idOrg5);
        }

        if (idOrg6 == null || idOrg6 == 0) {
            return orgIds;
        } else {
            orgIds.add(idOrg6);
        }

        if (idOrg7 == null || idOrg7 == 0) {
            return orgIds;
        } else {
            orgIds.add(idOrg7);
        }

        if (idOrg8 == null || idOrg8 == 0) {
            return orgIds;
        } else {
            orgIds.add(idOrg8);
        }

        if (idOrg9 == null || idOrg9 == 0) {
            return orgIds;
        } else {
            orgIds.add(idOrg9);
        }

        return orgIds;
    }

    /**
     * 重新设置组织机构id
     *
     * @param orgIds
     */
    public void setAllLevelOrgIds(List<Integer> orgIds) {
        for (int i = 0; i < MAX_LEVEL; i++) {
            switch (i) {
                case 0:
                    idOrg1 = i < orgIds.size() ? orgIds.get(i) : 0;
                    break;
                case 1:
                    idOrg2 = i < orgIds.size() ? orgIds.get(i) : 0;
                    break;
                case 2:
                    idOrg3 = i < orgIds.size() ? orgIds.get(i) : 0;
                    break;
                case 3:
                    idOrg4 = i < orgIds.size() ? orgIds.get(i) : 0;
                    break;
                case 4:
                    idOrg5 = i < orgIds.size() ? orgIds.get(i) : 0;
                    break;
                case 5:
                    idOrg6 = i < orgIds.size() ? orgIds.get(i) : 0;
                    break;
                case 6:
                    idOrg7 = i < orgIds.size() ? orgIds.get(i) : 0;
                    break;
                case 7:
                    idOrg8 = i < orgIds.size() ? orgIds.get(i) : 0;
                    break;
                case 8:
                    idOrg9 = i < orgIds.size() ? orgIds.get(i) : 0;
                    break;
                default:
                    return;
            }
        }
    }
}
