package com.example.demo.response;

import lombok.Data;


/**
 * @Author: shiguang
 * @Date: 2021/2/2
 * @Description:
 **/
@Data
public class BaseResponse  {

    private String code;
    private String msg;

    public BaseResponse() {
    }

    public BaseResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static BaseResponse success(){
        return new BaseResponse("0","success");
    }
}
