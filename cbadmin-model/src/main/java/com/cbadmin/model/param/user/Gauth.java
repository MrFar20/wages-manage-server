package com.cbadmin.model.param.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 谷歌验证码
 */
public class Gauth implements Serializable {

    /**
     * 启用谷歌验证码
     */
    @Data
    public static class Enable implements Serializable {

        private static final long serialVersionUID = 2290214216022986790L;

        @NotBlank
        @Length(min = 32, max = 32)
        private String spwd;
    }

    /**
     * 启用谷歌验证码
     */
    @Data
    public static class Disable implements Serializable {

        private static final long serialVersionUID = 7000789195464397823L;

        @JsonIgnore
        private String userId;

        /**
         * 谷歌验证码
         */
        @NotBlank
        @Pattern(regexp = "^(|[0-9]{6})$", message = "谷歌验证码格式错误")
        private String gauthCode;

        @NotBlank
        @Length(min = 32, max = 32)
        private String spwd;
    }

}
