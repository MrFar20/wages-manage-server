package com.cbadmin.service;


import com.cbadmin.model.param.user.AddUser;
import com.cbadmin.model.param.user.Check;
import com.cbadmin.model.param.user.Login;
import com.cbadmin.model.param.user.UpdateUser;
import com.cbadmin.model.vo.UserV;
import com.cbmai.common.service.DataQueryService;

/**
 * 用户服务
 */
public interface UserService extends DataQueryService {

    /**
     * 通过id查找用户
     * @param userId
     * @return
     */
    UserV getById(String userId);


    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    UserV getByUsername(String username);


    /**
     * 登录
     * @param request
     * @return
     */
    Login.Response login(Login.Request request);


    /**
     * 登出
     * @param token
     */
    void loginOut(String token);


    /**
     * 用户id登出
     * @param userId
     */
    void loginOutByUserId(String userId);


    /**
     * 检查密码
     * @param param
     */
    void checkPwd(Check.Password param);

    /**
     * 检查二级密码
     * @param param
     */
    void checkSpwd(Check.Password param);


    /**
     * 检查谷歌验证码
     * @param param
     * @return
     */
    boolean checkGauth(Check.Gauth param);


    /**
     * 检查验证码
     *  1.谷歌验证码开启时检查谷歌验证码
     *  2.没开启时检查二级密码
     * @param userId
     * @param code
     */
    void checkVerifyCode(String userId, String code);

    /**
     * 添加用户
     * @param param
     */
    void addUser(AddUser param);

    /**
     * 更新用户
     * @param param
     */
    void updateUser(UpdateUser param);

    /**
     * @return
     */
    String enableGauth(String userId);

    /**
     * 重置密钥
     * @param userId
     */
    void resetSecret(String userId);
}
