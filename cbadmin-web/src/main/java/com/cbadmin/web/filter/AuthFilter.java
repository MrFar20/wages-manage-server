package com.cbadmin.web.filter;

import com.cbadmin.common.constant.enums.UserType;
import com.cbadmin.model.vo.UserV;
import com.cbadmin.web.annotation.Auth;
import com.cbadmin.web.service.AuthService;
import com.cbmai.common.exception.BizException;
import com.cbmai.common.result.RInfos;
import com.cbmai.web.RequestUtils;
import com.cbmai.web.filter.AuthCheckData;
import com.cbmai.web.filter.BaseAuthFilter;
import com.cbmai.web.util.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限过滤器
 */
@Slf4j
@Component
public class AuthFilter extends BaseAuthFilter<Auth> {

    @Autowired
    private AuthService authService;

    @Override
    public Class<Auth> authAnnotationClazz() {
        return Auth.class;
    }

    @Override
    public void checkAuth(AuthCheckData<Auth> authCheckData) {
        HttpServletRequest request = authCheckData.getRequest();
        Auth auth = authCheckData.getAuthAnnotation();

        UserV user = authService.getUser();

        if (user == null) {
            log.info("-> 【未登录用户】访问 {}:{}, ip:{}", request.getMethod(), request.getRequestURI(), IPUtil.getCurrentRequestIP());
            throw new BizException(RInfos.UNAUTHORIZED);
        }

        //用户被禁用
        if (!user.getEnable()) {
            throw BizException.fail("用户已被禁用");
        }

        String ip = RequestUtils.requestIP();
        String whiteList = user.getIpWhiteList();
        if (whiteList == null) whiteList = "";

        if (user.getIpTableEnable() && !whiteList.contains(ip)) {
            throw BizException.fail("用户IP[" + ip +"]禁止访问");
        }

        UserType userType = UserType.getUserType(user.getUserType());

        boolean hasAuth = false;
        if (auth.value().length == 0) {
            hasAuth = true;
        } else {
            for (UserType ut : auth.value()) {
                if (ut == userType) {
                    hasAuth = true;
                    break;
                }
            }
        }

        if (!hasAuth) {
            log.info("-> 【无权限用户】[{}:{}]访问 {}: {}, ip:{} <-", userType.getDesc(), user.getUsername(), request.getMethod(), request.getRequestURI(), IPUtil.getCurrentRequestIP());
            throw new BizException(RInfos.PERMISSION_DENIED);
        }

        log.info("-> 【用户】[{}:{}]访问 {}: {}, ip:{} <-", userType.getDesc(), user.getUsername(), request.getMethod(), request.getRequestURI(), IPUtil.getCurrentRequestIP());
    }
}
