package com.example.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Author: shiguang
 * @Date: 2021/3/20
 * @Description:
 **/
@Configuration
@Data
public class ValueConfig {
    @Value("${hello1}")
    private String value;

    @PostConstruct
    public void catchVal(){
        System.out.println("postconstruct value:"+value);
    }
    {
        value = "1234";
        System.out.println("value is init by:"+value);
    }
}
