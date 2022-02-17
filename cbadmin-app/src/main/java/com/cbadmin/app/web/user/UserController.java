package com.cbadmin.app.web.user;


import com.cbadmin.model.param.VerifyCode;
import com.cbadmin.model.param.user.*;
import com.cbadmin.model.vo.UserV;
import com.cbadmin.web.BaseController;
import com.cbadmin.web.annotation.ApiLog;
import com.cbadmin.web.annotation.Auth;
import com.cbmai.common.result.R;
import com.cbmai.common.util.BizAssert;
import com.cbmai.core.gauth.GoogleAuthenticator;
import com.cbmai.web.RequestUtils;
import com.cbmai.web.annotation.PropIgnore;
import com.cbmai.web.annotation.WebDecrypt;
import com.cbmai.web.annotation.WebEncrypt;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户controller
 */
@RequestMapping("/user")
@RestController
@WebDecrypt
@WebEncrypt
public class UserController extends BaseController {

    //用户登录
    @PostMapping("/login")
    @ApiLog("用户登录")
    public R<Login.Response> login(@RequestBody @Validated Login.Request request) {
        request.setIp(RequestUtils.requestIP());
        Login.Response response = services.userService.login(request);
        return R.succData(response);
    }

    @DeleteMapping("/login")
    @ApiLog("用户登出")
    @Auth
    public R<?> loginOut() {
        services.userService.loginOut(requestToken());
        return R.succ();
    }

    //获取用户信息
    @GetMapping
    @Auth
    @PropIgnore(clazz = UserV.class, ignores = {"pwd", "pwdSalt", "spwd", "spwdSalt", "googleAuthenticationSecret", "secret", "gatewayIpWhiteList", "ipWhiteList"})
    public R<?> getInfo() {
        UserV user = getUser();
        return R.succData(
                user
        );
    }

    @GetMapping("/secret")
    @Auth
    @ApiLog("用户查看密钥")
    public R<?> getSecret(@Validated @Length(max = 32) String verifyCode) {

        UserV user = getUser();

        checkVerifyCode(verifyCode);

        return R.succData(
                user.getSecret()
        );
    }

    //重置密钥
    @PostMapping("/secret/reset")
    @Auth
    @ApiLog("用户重置密钥")
    public R<?> resetSecret(@Validated @RequestBody VerifyCode request) {

        String userId = userId();

        checkVerifyCode(request.getVerifyCode());

        services.userService.resetSecret(userId);

        return R.succ();
    }

    @PostMapping("/update/pwd")
    @Auth
    @ApiLog("用户修改密码")
    public R<?> updatePwd(@RequestBody @Validated UserUpdatePwd request) {

        String userId = userId();

        //检查验证码
        checkVerifyCode(request.getVerifyCode());

        //检查旧密码
        Check.Password param2 = new Check.Password(userId, request.getOldPwd());
        services.userService.checkPwd(param2);

        //更新密码
        UpdateUser updateUser = new UpdateUser();
        updateUser.setUserId(userId);
        updateUser.setPwd(request.getNewPwd());

        services.userService.updateUser(updateUser);

        return R.succ();
    }

    @PostMapping("/update/spwd")
    @Auth
    @ApiLog("用户修改二级密码")
    public R<?> updateSpwd(@RequestBody @Validated UserUpdatePwd request) {

        String userId = userId();


        checkVerifyCode(request.getVerifyCode());

        //检查二级密码
        Check.Password param = new Check.Password(userId, request.getOldPwd());
        services.userService.checkSpwd(param);

        //更新密码
        UpdateUser updateUser = new UpdateUser();
        updateUser.setUserId(userId);
        updateUser.setSpwd(request.getNewPwd());

        services.userService.updateUser(updateUser);

        return R.succ();
    }

    //启用谷歌验证
    @PostMapping("/gauth/enable")
    @Auth
    @ApiLog("启用谷歌验证")
    public R<String> enableGAuth(@RequestBody @Validated Gauth.Enable param) {


        checkCurrentUserSpwd(param.getSpwd());

        UserV user = getUser();

        String secret = services.userService.enableGauth(user.getUserId());

        return R.succData(
                GoogleAuthenticator.getQRBarcode(user.getUsername(), secret)
        );
    }

    //关闭谷歌验证
    @PostMapping("/gauth/disable")
    @Auth
    @ApiLog("关闭谷歌验证")
    public R<String> disableGAuth(@RequestBody @Validated Gauth.Disable param) {


        //检查二级密码
        checkCurrentUserSpwd(param.getSpwd());

        UserV user = getUser();

        //检查谷歌验证码
        Check.Gauth check = new Check.Gauth();
        check.setUserId(user.getUserId());
        check.setGauthCode(param.getGauthCode());

        BizAssert.isTrue(services.userService.checkGauth(check), "谷歌验证码错误");

        UpdateUser update = new UpdateUser();
        update.setUserId(user.getUserId());
        update.setGoogleAuthenticationEnabled(false);

        services.userService.updateUser(update);

        return R.succ();
    }

}
