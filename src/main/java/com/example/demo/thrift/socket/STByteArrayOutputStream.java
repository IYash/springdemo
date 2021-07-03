package com.example.demo.thrift.socket;

import lombok.Data;

import java.io.ByteArrayOutputStream;

/**
 * @Author: shiguang
 * @Date: 2021/7/3
 * @Description:
 **/
@Data
public class STByteArrayOutputStream extends ByteArrayOutputStream {
    private final int initialSize;

    public STByteArrayOutputStream(int size) {
        super(size);
        this.initialSize = size;
    }

    public STByteArrayOutputStream() {
        this(32);
    }

    public byte[] get() {
        return this.buf;
    }

    public void reset() {
        super.reset();
        if (this.buf.length > this.initialSize) {
            this.buf = new byte[this.initialSize];
        }

    }

    public int len() {
        return this.count;
    }
}
