package com.example.demo.service;

import groovy.lang.Binding;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: shiguang
 * @Date: 2021/2/20
 * @Description:
 **/
@Service
@Slf4j
public class HelloService {

    public void sayHello(String from){

        System.out.println(from + "say hello to you!");
    }

    public void sayHi(String script,String from) throws IOException, ResourceException, ScriptException {
        //通过脚本计算规则，进行输出
        System.out.println("sayHi");
        GroovyScriptEngine engine = new GroovyScriptEngine(script);
        Script spt = engine.createScript("test.groovy",new Binding());
        System.out.println(spt.run()+ "--->"+from);
    }
}
