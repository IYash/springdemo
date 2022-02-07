package com.example.demo.constant;

/**
 * @Author: shiguang
 * @Date: 2021/12/16
 * @Description:
 **/
public enum WishViewStatusEnum {

    VIEW_SELF(0),
    VIEW_COMMON(1),
    ;
    int status;
    WishViewStatusEnum(int status){
        this.status = status;
    }
}
