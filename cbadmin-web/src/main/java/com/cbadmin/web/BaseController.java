package com.cbadmin.web;

import com.cbadmin.common.constant.enums.UserType;
import com.cbadmin.model.param.user.Check;
import com.cbadmin.model.vo.UserV;
import com.cbadmin.service.Services;
import com.cbadmin.web.service.AuthService;
import com.cbmai.common.util.BizAssert;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    @Autowired
    protected AuthService authService;

    @Autowired
    protected Services services;

    /**
     * 获取用户
     * @return
     */
    public UserV getUser() {
        return authService.getUser();
    }

    /**
     * 用户类型
     * @return
     */
    public UserType userType() {
        return UserType.getUserType(
                getUser().getUserType()
        );
    }

    /**
     * 检查当前用户二级密码
     */
    public void checkCurrentUserSpwd(String spwd) {
        Check.Password param = new Check.Password(userId(), spwd);
        services.userService.checkSpwd(param);
    }

    /**
     * 监测谷歌验证码
     * @param gauthCode
     */
    public void checkGauthCode(String gauthCode) {
        //检查谷歌验证码
        Check.Gauth check = new Check.Gauth();
        check.setUserId(userId());
        check.setGauthCode(gauthCode);
        BizAssert.isTrue(services.userService.checkGauth(check), "谷歌验证码错误");
    }

    /**
     * 检查验证码
     * @param code
     */
    public void checkVerifyCode(String code) {
        //检查验证码
        services.userService.checkVerifyCode(userId(), code);
    }

    public String userId() {
        return authService.getUserId();
    }

    public String requestToken() {
        return authService.getRequestToken();
    }
}
