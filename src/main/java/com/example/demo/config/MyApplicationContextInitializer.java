package com.example.demo.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: shiguang
 * @Date: 2021/3/20
 * @Description: 用于测试PropertySource
 **/
public class MyApplicationContextInitializer  implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        Map<String, Object> props = new HashMap<>();
        props.put("hello1", "value1");
        PropertySource propertySource = new MapPropertySource("myPropertySource", props);
        configurableApplicationContext.getEnvironment().getPropertySources().addLast(propertySource);

    }
}
