package com.example.demo.thrift.socket;

import lombok.Data;

/**
 * @Author: shiguang
 * @Date: 2021/7/3
 * @Description:
 **/
@Data
public class STStruct {
    public final String name;

    public STStruct() {
        this("");
    }

    public STStruct(String n) {
        this.name = n;
    }
}
