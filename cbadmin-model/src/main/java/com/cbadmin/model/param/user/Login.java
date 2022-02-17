package com.cbadmin.model.param.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 登录
 */
public class Login {

    @Data
    public static class Request implements Serializable {

        private static final long serialVersionUID = 3876463308974227428L;

        /**
         * 用户名
         */
        @NotBlank
        @Length(min = 3, max = 32)
        private String username;

        /**
         * 密码
         */
        @NotBlank
        @Length(max = 32, min = 32)
        private String pwd;

        /**
         * 谷歌验证码
         */
        @Pattern(regexp = "^(|[0-9]{6})$", message = "谷歌验证码格式错误")
        private String gauthCode;

        /**
         * 登录ip
         */
        @JsonIgnore
        private String ip;

    }

    @Data
    public static class Response implements Serializable {

        private static final long serialVersionUID = -5092805773891818760L;


        /**
         * 用户id
         */
        @JsonIgnore
        private String userId;

        /**
         * 用户token
         */
        private String token;

        /**
         * 过期时间
         */
        private long expireTime;
    }

}
