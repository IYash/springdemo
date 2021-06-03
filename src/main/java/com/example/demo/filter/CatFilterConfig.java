package com.example.demo.filter;

import com.dianping.cat.servlet.CatFilter;
import com.example.demo.cat.CatServletFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: shiguang
 * @Date: 2021/3/23
 * @Description:
 **/
@Configuration
public class CatFilterConfig {

    @Bean
    public FilterRegistrationBean catFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CatServletFilter filter = new CatServletFilter();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("cat-filter");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
