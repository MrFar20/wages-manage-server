package com.cbadmin.web.config;

import com.cbadmin.web.filter.AuthFilter;
import com.cbadmin.web.filter.IpFilter;
import com.cbmai.web.annotation.EnableWebDES;
import com.cbmai.web.des.servlet.filter.DESFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebDES
public class EpWebConfig {

    /**
     * 权限过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean epAuthFilterBean(AuthFilter authFilter) {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(authFilter);
        bean.addUrlPatterns("/*");
        bean.setName("authFilter");
        bean.setOrder(100);
        return bean;
    }

    /**
     * @return
     */
    @Bean
    public FilterRegistrationBean ipFilterBean(IpFilter ipFilter) {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(ipFilter);
        bean.addUrlPatterns("/*");
        bean.setName("ipFilter");
        bean.setOrder(0);
        return bean;
    }

    /**
     * 加密解密过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean ecDesFilter(DESFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("desFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
