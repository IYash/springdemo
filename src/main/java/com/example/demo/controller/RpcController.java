package com.example.demo.controller;

import com.example.demo.remote.LocalService;
import com.google.gson.JsonObject;
import com.example.demo.remote.SakuraActivityService;
import com.example.demo.response.BaseResponse;
import com.example.demo.rpc.RpcService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author: shiguang
 * @Date: 2021/3/13
 * @Description:
 **/
@RestController
public class RpcController {

    @Resource
    RpcService rpcService;
    @Resource
    SakuraActivityService sakuraActivityService;
    @Resource
    LocalService localService;
    @Resource
    RestTemplate restTemplate;

    @RequestMapping("myRpc")
    public BaseResponse myRpc(){
        return new BaseResponse("0",rpcService.sayHi("web"));
    }
    @RequestMapping("url")
    public BaseResponse remoteUrl()  throws Exception{
      JsonObject result=  sakuraActivityService.demoHttpFetch().execute().body();
      return new BaseResponse("0",result.toString());
    }
    @RequestMapping("local")
    public BaseResponse local() throws Exception{
        JsonObject result = localService.catchVal().execute().body();
        return new BaseResponse("0",result.toString());
    }
    @RequestMapping("restTemplate")
    public BaseResponse restTemplate(String uri) throws Exception{
        String response = restTemplate.getForObject("http://localhost:8081/demo/" +uri, String.class);
        return new BaseResponse("0",response);
    }
}
