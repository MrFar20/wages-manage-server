package com.cbadmin.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 检查验证码
 */
@Data
public class VerifyCode implements Serializable {


    private static final long serialVersionUID = 4636955841044254650L;

    @Length(max = 32)
    @NotBlank
    private String verifyCode;

}
