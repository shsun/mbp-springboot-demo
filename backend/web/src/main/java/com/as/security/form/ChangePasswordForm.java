package com.as.security.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @author who
 */
@Data
public class ChangePasswordForm {
    /**
     *
     */
    @NotEmpty(message = "用户ID列表不能为空")
    private List<Integer> ids;

    /**
     *
     */
    @NotEmpty(message = "密码不能为空")
    private String password;
}
