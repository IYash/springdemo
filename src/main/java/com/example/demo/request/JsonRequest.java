package com.example.demo.request;

import lombok.Data;

/**
 * @Author: shiguang
 * @Date: 2021/2/2
 * @Description:
 **/
@Data
public class JsonRequest {

    private String name;
    private String color;

    @Data
    public static class InnerJson{
        private int price;
        private int amount;
    }

}
