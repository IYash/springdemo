package com.example.demo.service;

import com.example.demo.dal.TeachingVideoProgressMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: shiguang
 * @Date: 2021/4/15
 * @Description:
 **/
@Service
public class TeachingVideoProgressService {
    @Resource
    TeachingVideoProgressMapper teachingVideoProgressMapper;

    public int upsertCreatorTeachingVideoProgress(Long userId,Long noteId,Double progress){
        return teachingVideoProgressMapper.upsertCreatorTeachingVideoProgress(userId,noteId,progress);
    }
}
