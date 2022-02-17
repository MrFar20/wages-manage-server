package com.cbadmin.model.param.user;

import com.cbadmin.model.param.VerifyCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @createTime: 2021-08-17 17:03:11
 * @created by: mrwangx
 * @description:
 */
@Data
public class UpdateUser extends VerifyCode implements Serializable {

    @JsonIgnore
    private String mangaerId;

    @NotNull
    private String userId;

    @Length(max = 32, min = 32)
    private String pwd;

    @Length(max = 32, min = 32)
    private String spwd;

    private Boolean enable;

    private Boolean googleAuthenticationEnabled;

    /**
     * ip白名单
     */
    private String ipWhiteList;


    /**
     * ip防火墙开启
     */
    private Boolean ipTableEnable;
}
