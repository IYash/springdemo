package com.example.demo.constant;


import lombok.Getter;

/**
 * @Author: shiguang
 * @Date: 2021/12/7
 * @Description:
 **/
@Getter
public enum WishVerifyStatusEnum {

    PUNISH(-1),
    SELF(0),
    COMMON(1),
    HIGHLIGHT(2),
    ;
    int status;
    WishVerifyStatusEnum(int status){
        this.status = status;
    }
}
