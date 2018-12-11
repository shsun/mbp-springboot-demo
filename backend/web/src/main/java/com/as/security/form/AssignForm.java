package com.as.security.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author who
 */
@Data
public class AssignForm {

    /**
     * 需要被赋值的id，如用户权限时，是给用户赋权限，assigneeids为用户id
     */
    @NotEmpty
    private List<Integer> assigneeIds;

    /**
     *
     */
    @NotNull
    private List<Integer> selectedIds;


}
