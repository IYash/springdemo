package com.example.demo.entity;

import lombok.Data;

/**
 * @Author: shiguang
 * @Date: 2021/4/15
 * @Description:
 **/
@Data
public class TeachingVideoProgressDO{
    private Integer id;
    private Long userId;
    private Long noteId;
    private Double progress;
}
