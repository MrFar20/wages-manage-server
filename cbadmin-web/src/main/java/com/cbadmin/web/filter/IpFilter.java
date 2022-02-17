package com.cbadmin.web.filter;

import cn.hutool.extra.servlet.ServletUtil;
import com.cbadmin.common.systemconfig.SystemConfigs;
import com.cbmai.common.result.R;
import com.cbmai.common.util.StringUtils;
import com.cbmai.web.RequestUtils;
import com.cbmai.web.util.IPUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component("globalIPTablesFilter")
public class IpFilter extends OncePerRequestFilter {

    @Autowired
    private SystemConfigs systemConfigs;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //检查全局ip白名单是否启用
        log.info("==> [{}] 访问 {} {}", IPUtil.getCurrentRequestIP(), request.getMethod(), request.getRequestURI());

        boolean enable = systemConfigs.globalIPWhiteListEnable.get();
        String ip = RequestUtils.requestIP();

        if (enable) {
            String whiteList = systemConfigs.globalIPWhiteList.get();
            //不在ip白名单内
            if (!StringUtils.isNullOrBlank(whiteList) && !whiteList.contains(ip)) {

                String failMsg = "IP[" + ip +"]禁止访问";
                ServletUtil.write(response, objectMapper.writeValueAsString(R.fail(failMsg)), "application/json;charset=utf-8");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
