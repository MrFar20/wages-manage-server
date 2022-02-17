package com.cbadmin.model.param.user;

import com.cbadmin.model.param.VerifyCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @createTime: 2021-08-23 11:37:33
 * @created by: mrwangx
 * @description:
 */
@Data
public class UserUpdatePwd extends VerifyCode implements Serializable {

    private static final long serialVersionUID = -3334535059163434653L;

    @JsonIgnore
    private Long userId;

    @NotBlank
    @Length(max = 32, min = 32)
    private String oldPwd;

    @NotBlank
    @Length(max = 32, min = 32)
    private String newPwd;

}
