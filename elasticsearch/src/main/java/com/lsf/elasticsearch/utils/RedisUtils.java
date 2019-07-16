package com.lsf.elasticsearch.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RedisUtils {

    InputStream inputStream;
    OutputStream outputStream;

    public RedisUtils(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    public void set(String key, String value) throws IOException {
        StringBuffer data = new StringBuffer();
        data.append("*3").append("\r\n");
        data.append("$3").append("\r\n");
        data.append("SET").append("\r\n");
        data.append("$").append(key.length()).append("\r\n");
        data.append(key).append("\r\n");
        data.append("$").append(value.length()).append("\r\n");
        data.append(value).append("\r\n");
        System.out.println(data.toString());
        outputStream.write(data.toString().getBytes());
        System.out.println("发送报文成功");

        byte[] buff = new byte[1024];
        inputStream.read(buff);
        System.out.println("接收响应");
        System.out.println(buff.toString());
    }

    public void get(String key) throws IOException {
        StringBuffer data = new StringBuffer();
        data.append("*3").append("\r\n");
        data.append("$3").append("\r\n");
        data.append("GET").append("\r\n");
        data.append("$").append(key.length()).append("\r\n");
        data.append(key).append("\r\n");
        System.out.println(data.toString());
        outputStream.write(data.toString().getBytes());
        System.out.println("发送报文成功");

        byte[] buff = new byte[1024];
        inputStream.read(buff);
        System.out.println("接收响应");
        System.out.println(buff.toString());
    }

}
