package com.as.security.form;

import lombok.Data;

/**
 * @author who
 */
@Data
public class UserQueryForm {

    /**
     *
     */
    private String keyword;
    /**
     *
     */
    private Boolean disabled;

    /**
     *
     */
    private Integer id;
}
