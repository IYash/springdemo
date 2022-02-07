package com.example.demo;

import lombok.Data;

@Data
public class WishBaseResponse<T> {
    private Integer code;
    private String msg;
    private T data;

    public static WishBaseResponse success = new WishBaseResponse(200,"操作成功");
    public WishBaseResponse(){

    }
    public WishBaseResponse(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public void addSuccessData(T data){
        this.code = success.getCode();
        this.msg = success.getMsg();
        this.data = data;
    }
}