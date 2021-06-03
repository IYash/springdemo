package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.SpringContextUtils;
import com.example.demo.request.JsonRequest;
import com.example.demo.response.BaseResponse;
import com.example.demo.service.HelloService;
import groovy.lang.GroovyClassLoader;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

import static com.example.demo.response.BaseResponse.success;

/**
 * @Author: shiguang
 * @Date: 2021/2/2
 * @Description:
 **/
@RestController
public class HelloController {

    @Resource
    HelloService helloService;

    @PostMapping("/runScriptEngine")
    public BaseResponse runScriptEngine() throws ResourceException, ScriptException, IOException {
        helloService.sayHi("/Users/shiguang/Desktop","backend");
        return success();
    }

    @PostMapping("/testJsonParam")
    public BaseResponse test(@RequestBody JsonRequest req){
        System.out.println(JSONObject.toJSONString(req));
        return success();
    }

    @RequestMapping("/runScript")
    public Object runScript(HttpServletRequest request) throws Exception {
        ServletInputStream inputStream = request.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String tmp ="";
        while(StringUtils.isNotBlank(tmp = reader.readLine()))
        {
            sb.append(tmp);
        }
        String script = sb.toString();
        System.out.println(script);
        if (script != null) {
            // 这里其实就是groovy的api动态的加载生成一个Class，然后反射生成对象，然后执行run方法，最后返回结果
            // 最精华的地方就是SpringContextUtils.autowireBean，可以实现自动注入bean，
            Class clazz = new GroovyClassLoader().parseClass(script);
            System.out.println(clazz.toString());
            Method run = clazz.getMethod("run");
            Object o = clazz.newInstance();
            SpringContextUtils.autowireBean(o);
            Object ret = run.invoke(o);
            return ret;
        } else {
            return "no script";
        }
    }
    @RequestMapping("/checkBean")
    public Object checkBean(String beanName) throws Exception {
        Class clz = Class.forName("com.example.demo.biz.LoaderV");
        Object o = SpringContextUtils.getBean(clz);
        //Object o = SpringContextUtils.getBeanWithName(beanName);
        return o.toString();
    }

    @RequestMapping("/destroyBean")
    public BaseResponse destroyBean(String beanName) throws Exception{
        SpringContextUtils.destroyBean(beanName);
        return success();
    }
}
