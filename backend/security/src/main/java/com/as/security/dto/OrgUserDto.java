package com.as.security.dto;

import base.domain.XSuperEntity;
import lombok.Data;

/**
 * @author who
 */
@Data
public class OrgUserDto extends XSuperEntity<OrgUserDto> {
//public class OrgUserDto extends AbstractAuditable {

    private Integer id;

    private Integer parentId;

    private String name;

    private String description;

    private Boolean hasChildren;

    private String type;

    public OrgUserDto(String type, Boolean hasChildren) {
        this.type = type;
        this.hasChildren = hasChildren;
    }
}
