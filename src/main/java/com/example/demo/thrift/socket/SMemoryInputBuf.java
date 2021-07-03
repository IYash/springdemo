package com.example.demo.thrift.socket;

import lombok.Data;
import org.apache.thrift.transport.TTransportException;

/**
 * @Author: shiguang
 * @Date: 2021/7/3
 * @Description:
 **/
@Data
public class SMemoryInputBuf {
    private byte[] buf_;
    private int pos_;
    private int endPos_;

    public SMemoryInputBuf() {
    }

    public SMemoryInputBuf(byte[] buf) {
        this.reset(buf);
    }

    public SMemoryInputBuf(byte[] buf, int offset, int length) {
        this.reset(buf, offset, length);
    }

    public void reset(byte[] buf) {
        this.reset(buf, 0, buf.length);
    }

    public void reset(byte[] buf, int offset, int length) {
        this.buf_ = buf;
        this.pos_ = offset;
        this.endPos_ = offset + length;
    }

    public void clear() {
        this.buf_ = null;
    }

    public void close() {
    }

    public boolean isOpen() {
        return true;
    }

    public void open() throws TTransportException {
    }

    public int read(byte[] buf, int off, int len) throws TTransportException {
        int bytesRemaining = this.getBytesRemainingInBuffer();
        int amtToRead = len > bytesRemaining ? bytesRemaining : len;
        if (amtToRead > 0) {
            System.arraycopy(this.buf_, this.pos_, buf, off, amtToRead);
            this.consumeBuffer(amtToRead);
        }

        return amtToRead;
    }

    public void write(byte[] buf, int off, int len) throws TTransportException {
        throw new UnsupportedOperationException("No writing allowed!");
    }

    public byte[] getBuffer() {
        return this.buf_;
    }

    public int getBufferPosition() {
        return this.pos_;
    }

    public int getBytesRemainingInBuffer() {
        return this.endPos_ - this.pos_;
    }

    public void consumeBuffer(int len) {
        this.pos_ += len;
    }
}
