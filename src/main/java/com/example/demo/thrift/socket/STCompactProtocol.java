package com.example.demo.thrift.socket;

import org.apache.thrift.ShortStack;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.transport.TTransportException;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * @Author: shiguang
 * @Date: 2021/7/3
 * @Description:
 **/
public class STCompactProtocol {

    public final byte[] temp = new byte[10];
    private static final byte[] ttypeToCompactType;
    private static final byte[] EMPTY_BYTES = new byte[0];
    private ShortStack lastField_ = new ShortStack(15);
    private short lastFieldId_=0;
    private static final ByteBuffer EMPTY_BUFFER;
    private static final STStruct ANONYMOUS_STRUCT;
    private static final STField TSTOP;
    public final STByteArrayOutputStream writeBuffer = new STByteArrayOutputStream(1024);
    public  SMemoryInputBuf readBuffer ;
    private byte[] i32buf = new byte[]{(byte)0,(byte)0,(byte)0,(byte)32};
    private int maxLength_=600;
    public  STCompactProtocol(byte[] result){
        readBuffer = new SMemoryInputBuf(result);
    }
    public STCompactProtocol(){}
    public void writeMessageBegin(STMessage message) throws TException {
        this.writeByteDirect((byte)-126);
        this.writeByteDirect(1 | message.type << 5 & -32);
        this.writeVarint32(message.seqid);
        this.writeString(message.name);
    }
    private void writeByteDirect(int n) throws TException {
        this.writeByteDirect((byte)n);
    }
    private void writeByteDirect(byte b)  {
        this.temp[0] = b;
        writeBuffer.write(temp,0,1);
    }
    private void writeVarint32(int n) throws TException {
        int idx;
        for(idx = 0; (n & -128) != 0; n >>>= 7) {
            this.temp[idx++] = (byte)(n & 127 | 128);
        }

        this.temp[idx++] = (byte)n;
        writeBuffer.write(temp,0,idx);
    }
    public void writeString(String str) throws TException {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            this.writeBinary(bytes, 0, bytes.length);
        } catch (UnsupportedEncodingException var3) {
            throw new TException("UTF-8 not supported!");
        }
    }

    private void writeBinary(byte[] buf, int offset, int length) throws TException {
        this.writeVarint32(length);
        writeBuffer.write(buf,offset,length);
    }
    public void writeStructBegin(STStruct struct) throws TException {
        this.lastField_.push(this.lastFieldId_);
        this.lastFieldId_ = 0;
    }
    public void writeFieldBegin(STField field) throws TException {
      this.writeFieldBeginInternal(field, (byte)-1);

    }
    private void writeFieldBeginInternal(STField field, byte typeOverride) throws TException {
        byte typeToWrite = typeOverride == -1 ? this.getCompactType(field.type) : typeOverride;
        if (field.id > this.lastFieldId_ && field.id - this.lastFieldId_ <= 15) {
            this.writeByteDirect(field.id - this.lastFieldId_ << 4 | typeToWrite);
        } else {
            this.writeByteDirect(typeToWrite);
            this.writeI16(field.id);
        }

        this.lastFieldId_ = field.id;
    }
    static {
        EMPTY_BUFFER = ByteBuffer.wrap(EMPTY_BYTES);
        ANONYMOUS_STRUCT = new STStruct("");
        TSTOP = new STField("", (byte)0, (short)0);
        ttypeToCompactType = new byte[16];
        ttypeToCompactType[0] = 0;
        ttypeToCompactType[2] = 1;
        ttypeToCompactType[3] = 3;
        ttypeToCompactType[6] = 4;
        ttypeToCompactType[8] = 5;
        ttypeToCompactType[10] = 6;
        ttypeToCompactType[4] = 7;
        ttypeToCompactType[11] = 8;
        ttypeToCompactType[15] = 9;
        ttypeToCompactType[14] = 10;
        ttypeToCompactType[13] = 11;
        ttypeToCompactType[12] = 12;
    }
    private byte getCompactType(byte ttype) {
        return ttypeToCompactType[ttype];
    }
    public void writeI16(short i16) throws TException {
        this.writeVarint32(intToZigZag(i16));
    }
    private int intToZigZag(int n) {
        return n << 1 ^ n >> 31;
    }
    public void writeFieldStop() throws TException {
        this.writeByteDirect((byte)0);
    }
    public void writeStructEnd() throws TException {
        this.lastFieldId_ = this.lastField_.pop();
    }
    public STMessage readMessageBegin() throws TException {
        byte protocolId = this.readByte();
        if (protocolId != -126) {
            throw new TProtocolException("Expected protocol id " + Integer.toHexString(-126) + " but got " + Integer.toHexString(protocolId));
        } else {
            byte versionAndType = this.readByte();
            byte version = (byte)(versionAndType & 31);
            if (version != 1) {
                throw new TProtocolException("Expected version 1 but got " + version);
            } else {
                byte type = (byte)(versionAndType >> 5 & 7);
                int seqid = this.readVarint32();
                String messageName = this.readString();
                return new STMessage(messageName, type, seqid);
            }
        }
    }
    public byte readByte() throws TException {
        byte b;
        if (this.readBuffer.getBytesRemainingInBuffer() > 0) {
            b = this.readBuffer.getBuffer()[this.readBuffer.getBufferPosition()];
            this.readBuffer.consumeBuffer(1);
        } else {
            readAll(this.temp, 0, 1);
            b = this.temp[0];
        }

        return b;
    }
    public int readAll(byte[] buf, int off, int len) throws TTransportException {
        int got = 0;

        int ret;
        for(boolean var5 = false; got < len; got += ret) {
            ret = this.read(buf, off + got, len - got);
            if (ret <= 0) {
                throw new TTransportException("Cannot read. Remote side has closed. Tried to read " + len + " bytes, but only got " + got + " bytes. (This is often indicative of an internal error on the server side. Please check your server logs.)");
            }
        }

        return got;
    }
    public int read(byte[] buf, int off, int len) throws TTransportException {
        int got = readBuffer.read(buf, off, len);
        if (got > 0) {
            return got;
        } else {
            readFrame();
            return readBuffer.read(buf, off, len);
        }
    }
    private void readFrame() throws TTransportException {

        readAll(i32buf, 0, 4);
        int size = decodeFrameSize(this.i32buf);
        if (size < 0) {
            //this.close();
            throw new TTransportException(5, "Read a negative frame size (" + size + ")!");
        } else if (size > this.maxLength_) {
            //this.close();
            throw new TTransportException(5, "Frame size (" + size + ") larger than max length (" + this.maxLength_ + ")!");
        } else {
            byte[] buff = new byte[size];
            this.readAll(buff, 0, size);
            this.readBuffer.reset(buff);
        }

    }
    public static final int decodeFrameSize(byte[] buf) {
        return (buf[0] & 255) << 24 | (buf[1] & 255) << 16 | (buf[2] & 255) << 8 | buf[3] & 255;
    }
    private int readVarint32() throws TException {
        int result = 0;
        int shift = 0;
        if (this.readBuffer.getBytesRemainingInBuffer() >= 5) {
            byte[] buf = this.readBuffer.getBuffer();
            int pos = this.readBuffer.getBufferPosition();
            int off = 0;

            while(true) {
                byte b = buf[pos + off];
                result |= (b & 127) << shift;
                if ((b & 128) != 128) {
                    this.readBuffer.consumeBuffer(off + 1);
                    break;
                }

                shift += 7;
                ++off;
            }
        } else {
            while(true) {
                byte b = this.readByte();
                result |= (b & 127) << shift;
                if ((b & 128) != 128) {
                    break;
                }

                shift += 7;
            }
        }

        return result;
    }
    public String readString() throws TException {
        int length = this.readVarint32();
        if (length == 0) {
            return "";
        } else {
            try {
                if (this.readBuffer.getBytesRemainingInBuffer() >= length) {
                    String str = new String(this.readBuffer.getBuffer(), this.readBuffer.getBufferPosition(), length, "UTF-8");
                    this.readBuffer.consumeBuffer(length);
                    return str;
                } else {
                    return new String(this.readBinary(length), "UTF-8");
                }
            } catch (UnsupportedEncodingException var3) {
                throw new TException("UTF-8 not supported!");
            }
        }
    }

    public ByteBuffer readBinary() throws TException {
        int length = this.readVarint32();
        if (length == 0) {
            return EMPTY_BUFFER;
        } else if (readBuffer.getBytesRemainingInBuffer() >= length) {
            ByteBuffer bb = ByteBuffer.wrap(this.readBuffer.getBuffer(), this.readBuffer.getBufferPosition(), length);
            this.readBuffer.consumeBuffer(length);
            return bb;
        } else {
            byte[] buf = new byte[length];
            readAll(buf, 0, length);
            return ByteBuffer.wrap(buf);
        }
    }
    private byte[] readBinary(int length) throws TException {
        if (length == 0) {
            return EMPTY_BYTES;
        } else {
            byte[] buf = new byte[length];
            readAll(buf, 0, length);
            return buf;
        }
    }
}
