package com.cbadmin.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class OperateUserWithVerifyCode extends VerifyCode {

    private static final long serialVersionUID = 3765928102582381093L;

    /**
     * 用户id
     */
    @NotBlank
    @Length(max = 32)
    private String userId;

}
