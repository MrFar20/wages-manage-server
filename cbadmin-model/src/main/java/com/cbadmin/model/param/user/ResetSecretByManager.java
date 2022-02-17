package com.cbadmin.model.param.user;

import com.cbadmin.model.param.VerifyCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 重设密码
 */
@Data
public class ResetSecretByManager extends VerifyCode implements Serializable {

    private static final long serialVersionUID = -262536337058639807L;

    @JsonIgnore
    private String managerId;

    @NotBlank
    private String userId;
}
