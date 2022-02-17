package com.cbadmin.model.param.user;

import com.cbadmin.model.param.VerifyCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AddUser extends VerifyCode implements Serializable {

    private static final long serialVersionUID = -5678621325159388855L;

    @JsonIgnore
    private String managerId;

    @NotBlank
    @Length(min = 3, max = 32)
    private String username;

    @NotBlank
    @Length(max = 32, min = 32)
    private String pwd;

    @NotBlank
    private String userType;

}
