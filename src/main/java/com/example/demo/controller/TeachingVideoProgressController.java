package com.example.demo.controller;

import com.example.demo.response.BaseResponse;
import com.example.demo.service.TeachingVideoProgressService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: shiguang
 * @Date: 2021/4/15
 * @Description:
 **/
@RestController
public class TeachingVideoProgressController {
    @Resource
    TeachingVideoProgressService teachingVideoProgressService;

    @RequestMapping("updateProgress")
    public BaseResponse updateProgress(@RequestParam("userId") Long userId, @RequestParam("noteId") Long noteId,@RequestParam("progress") Double progress){
        teachingVideoProgressService.upsertCreatorTeachingVideoProgress(userId,noteId,progress);
        return BaseResponse.success();
    }
}
