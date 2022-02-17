package com.cbadmin.model.param.user;

import com.cbadmin.model.param.VerifyCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class GetUserSecret extends VerifyCode implements Serializable {

    private static final long serialVersionUID = -288139553964795208L;

    @JsonIgnore
    private String managerId;

    @NotNull
    private String userId;

}
