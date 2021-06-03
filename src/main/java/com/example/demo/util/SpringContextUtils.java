package com.example.demo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: shiguang
 * @Date: 2021/2/20
 * @Description:
 **/
@Configuration
public class SpringContextUtils implements ApplicationContextAware {
    static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static void autowireBean(Object bean) {
        context.getAutowireCapableBeanFactory().autowireBean(bean);
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }
    public static void destroyBean(String beanName){
        context.getAutowireCapableBeanFactory().destroyBean(getBeanWithName(beanName));
    }
    public static Object getBeanWithName(String beanName){
        return context.getAutowireCapableBeanFactory().getBean(beanName);
    }
}
