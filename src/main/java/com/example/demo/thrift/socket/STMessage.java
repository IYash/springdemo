package com.example.demo.thrift.socket;

import lombok.Data;

/**
 * @Author: shiguang
 * @Date: 2021/7/3
 * @Description:
 **/
@Data
public class STMessage {
    public final String name;
    public final byte type;
    public final int seqid;
}
