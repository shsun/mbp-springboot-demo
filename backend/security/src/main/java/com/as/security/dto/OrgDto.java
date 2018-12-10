package com.as.security.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.Size;

/**
 * @author who
 */
@Data
public class OrgDto {
    /**
     *
     */
    private Integer parentId;
    /**
     *
     */
    @NotEmpty
    @SafeHtml
    @Size(min = 1, max = 30)
    private String name;
    /**
     *
     */
    @NotEmpty
    @SafeHtml
    @Size(min = 1, max = 100)
    private String description;
}
