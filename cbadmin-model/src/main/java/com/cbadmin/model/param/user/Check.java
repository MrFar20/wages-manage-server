package com.cbadmin.model.param.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class Check {

    /**
     * 检查密码
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Password implements Serializable {

        private static final long serialVersionUID = -7176488441281814358L;

        @NotNull
        private String userId;

        /**
         * 密码
         */
        @NotNull
        @Length(min = 32, max = 32)
        private String pwd;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Gauth implements Serializable {

        private static final long serialVersionUID = 7280234927998734569L;

        @NotNull
        private String userId;

        /**
         * 密码
         */
        @NotNull
        @Pattern(regexp = "^(|[0-9]{6})$", message = "谷歌验证码格式错误")
        private String gauthCode;

    }

}
