package com.example.demo;

import lombok.Data;

import java.util.List;

/**
 * @Author: shiguang
 * @Date: 2021/4/27
 * @Description:
 **/
@Data
public class NoteCacheDto<T> {
    private String key;
    private List<T> values;
    public static <T>NoteCacheDto<T> from(String key,List<T> values){
        NoteCacheDto dto = new NoteCacheDto();
        dto.setKey(key);
        dto.setValues(values);
        return dto;
    }
}
