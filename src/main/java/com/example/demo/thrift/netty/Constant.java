package com.example.demo.thrift.netty;

/**
 * @Author: shiguang
 * @Date: 2021/6/4
 * @Description:
 **/
public class Constant {
    public static final int PKG_HEAD_LEN = 4;

    public static final int MAX_CONTENT_LENGTH = 16384000;

    public static final int MAX_BUFF_SIZE = 24 * 1024;

    public static final int SOCK_BACKLOG = 1024;

    // unit: millisecond
    public static final long DEFAULT_PERIOD_PER_BULK = 300;

    // unit: second
    public static final int IDLE_TIMEOUT = 5;

}
