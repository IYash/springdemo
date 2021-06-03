package com.example.demo.util;

import com.example.demo.config.ValueConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Author: shiguang
 * @Date: 2021/3/20
 * @Description: 接收到消息后更新数据
 **/
@Component
public class Receiver {
    @Autowired
    ApplicationContext ctx;//可以通过其他的形式获取bean

    public void receiveMessage(String message){
        ValueConfig value = ctx.getBean(ValueConfig.class);
        value.setValue(message);
    }
}
