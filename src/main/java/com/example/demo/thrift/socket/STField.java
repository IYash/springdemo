package com.example.demo.thrift.socket;

import lombok.Data;

/**
 * @Author: shiguang
 * @Date: 2021/7/3
 * @Description:
 **/
@Data
public class STField {
    public final String name;
    public final byte type;
    public final short id;

    public STField() {
        this("", (byte)0, (short)0);
    }

    public STField(String n, byte t, short i) {
        this.name = n;
        this.type = t;
        this.id = i;
    }
}
