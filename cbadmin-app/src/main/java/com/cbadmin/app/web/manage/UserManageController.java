package com.cbadmin.app.web.manage;

import com.cbadmin.common.constant.enums.UserType;
import com.cbadmin.model.param.OperateUserWithVerifyCode;
import com.cbadmin.model.param.user.AddUser;
import com.cbadmin.model.param.user.GetUserSecret;
import com.cbadmin.model.param.user.ResetSecretByManager;
import com.cbadmin.model.param.user.UpdateUser;
import com.cbadmin.model.vo.UserV;
import com.cbadmin.web.BaseController;
import com.cbadmin.web.annotation.ApiLog;
import com.cbadmin.web.annotation.Auth;
import com.cbmai.common.exception.BizException;
import com.cbmai.common.param.PageAndDateRangeRequest;
import com.cbmai.common.query.Page;
import com.cbmai.common.query.QueryCondition;
import com.cbmai.common.result.R;
import com.cbmai.common.result.RInfos;
import com.cbmai.common.util.BizAssert;
import com.cbmai.web.annotation.PropIgnore;
import com.cbmai.web.annotation.WebDecrypt;
import com.cbmai.web.annotation.WebEncrypt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage/user")
@WebDecrypt
@WebEncrypt
public class UserManageController extends BaseController {

    @GetMapping
    @Auth({UserType.ADMIN})
    @ApiLog("管理获取用户")
    @PropIgnore(clazz = UserV.class, ignores = {"pwd", "pwdSalt", "spwd", "spwdSalt", "googleAuthenticationSecret", "secret"})
    public R<?> getUsers(PageAndDateRangeRequest request) {
        QueryCondition condition = new QueryCondition()
                .page(request, Page.Sort.byCreateTimeDesc())
                .notEqual("userId", getUser().getUserId());

        R result = services.userService.query(condition);


        return result;
    }

    @PostMapping
    @Auth(UserType.ADMIN)
    @ApiLog("添加用户")
    public R<?> addUser(@Validated @RequestBody AddUser addUser) {
        UserV manager = getUser();
        addUser.setManagerId(
                manager.getUserId()
        );

        checkVerifyCode(addUser.getVerifyCode());

        services.userService.addUser(addUser);
        return R.succ();
    }

    @PostMapping("/secret")
    @Auth(UserType.ADMIN)
    @ApiLog("管理查看用户密钥")
    public R<?> getUserSecretByManager(@Validated @RequestBody GetUserSecret request) {

        UserV manager = getUser();

        //防止自己查看自己信息
        BizAssert.isNotTrue(manager.getUserId().equals(request.getUserId()), new BizException(RInfos.PERMISSION_DENIED));

        checkVerifyCode(request.getVerifyCode());

        UserV user = services.userService.getById(request.getUserId());
        BizAssert.notNull(user, BizException.notFound("用户不存在"));

        return R.succData(
                user.getSecret()
        );
    }

    @PostMapping("/login_out")
    @Auth(UserType.ADMIN)
    @ApiLog("管理强制下线用户")
    public R<?> userLoginOut(@Validated @RequestBody OperateUserWithVerifyCode param) {
        checkVerifyCode(param.getVerifyCode());
        services.userService.loginOutByUserId(param.getUserId());
        return R.succ();
    }

    @PostMapping("/secret/reset")
    @Auth(UserType.ADMIN)
    @ApiLog("管理重置用户密钥")
    public R<?> resetUserSecretByManager(@Validated @RequestBody ResetSecretByManager request) {

        UserV manager = getUser();

        //防止自己查看自己信息
        BizAssert.isNotTrue(manager.getUserId().equals(request.getUserId()), new BizException(RInfos.PERMISSION_DENIED));

        checkVerifyCode(request.getVerifyCode());

        services.userService.resetSecret(request.getUserId());

        return R.succ();
    }

    @PostMapping("/update")
    @Auth(UserType.ADMIN)
    @ApiLog("管理更新用户信息")
    public R<?> updateUserByManager(@Validated @RequestBody UpdateUser request) {

        UserV manager = getUser();

        //防止自己查看自己信息
        BizAssert.isNotTrue(manager.getUserId().equals(request.getUserId()), new BizException(RInfos.PERMISSION_DENIED));

        //设置用户
        request.setMangaerId(manager.getUserId());

        checkVerifyCode(request.getVerifyCode());

        services.userService.updateUser(request);

        return R.succ();
    }

}
