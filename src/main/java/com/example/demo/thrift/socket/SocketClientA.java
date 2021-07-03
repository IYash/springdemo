package com.example.demo.thrift.socket;

import org.apache.thrift.TException;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

/**
 * @Author: shiguang
 * @Date: 2021/7/3
 * @Description:
 **/
public class SocketClientA {

    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost",8899);
        OutputStream outputStream = socket.getOutputStream();
        byte[] input = buildByte();
        byte[] i32buf = new byte[4];
        int len = input.length;
        encodeFrameSize(len,i32buf);
        outputStream.write(i32buf,0,4);
        outputStream.write(input,0,input.length);
        outputStream.flush();
        InputStream inputStream = socket.getInputStream();
        int available = inputStream.available();
        byte[] result = new byte[available];
        inputStream.read(result);
        parseResult(result);
    }
    private static byte[] buildByte() throws TException {
        STMessage message = new STMessage("getPersonByUsername",(byte)1,1);
        STCompactProtocol protocol = new STCompactProtocol();
        protocol.writeMessageBegin(message);
        protocol.writeStructBegin(new STStruct("getPersonByUsername_args"));
        protocol.writeFieldBegin(new STField("username", org.apache.thrift.protocol.TType.STRING, (short)1));
        protocol.writeString("张三");
        protocol.writeFieldStop();
        protocol.writeStructEnd();
        return protocol.writeBuffer.get();
    }
    public static final void encodeFrameSize(int frameSize, byte[] buf) {
        buf[0] = (byte)(255 & frameSize >> 24);
        buf[1] = (byte)(255 & frameSize >> 16);
        buf[2] = (byte)(255 & frameSize >> 8);
        buf[3] = (byte)(255 & frameSize);
    }

    private static Object parseResult(byte[] result) throws Exception{//字节码解析异常的
        System.out.println(new String(result,"utf-8"));
        result = Arrays.copyOfRange(result,4,result.length);
        STCompactProtocol protocol = new STCompactProtocol(result);
        STMessage message = protocol.readMessageBegin();
        return message;
    }
}
