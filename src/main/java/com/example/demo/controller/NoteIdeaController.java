package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.dal.NoteIdeaMapper;
import com.example.demo.entity.NoteIdeaLibDao;
import com.example.demo.entity.NoteIdeaLibInfo;
import com.example.demo.entity.SelectTopicCate;
import com.example.demo.response.BaseResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: shiguang
 * @Date: 2021/11/26
 * @Description:
 **/
@RestController
public class NoteIdeaController {

    @Resource
    NoteIdeaMapper noteIdeaMapper;

    @RequestMapping("pass")
    public BaseResponse pass(){
        List<Integer> libIds = noteIdeaMapper.selectLibIds();
        List<NoteIdeaLibDao> noteIdeaLibDaos = noteIdeaMapper.noteIdeas(libIds);

        List<NoteIdeaLibInfo> infos = noteIdeaLibDaos.stream().map(NoteIdeaLibInfo::new)
                .collect(Collectors.toList());
        Map<Integer,List<NoteIdeaLibInfo>> infoMap = Maps.newHashMap();
        infos.stream().forEach(info->{
            List<NoteIdeaLibInfo> libInfoList = infoMap.getOrDefault(info.getLabelId(),Lists.newArrayList());
            libInfoList.add(info);
            infoMap.put(info.getLabelId(),libInfoList);
        });
        Map<Integer, List<NoteIdeaLibInfo>> libDaoMap = new HashMap<>();
        List<SelectTopicCate> selectTopicCates = SelectTopicCate.defaultCate();
        selectTopicCates.forEach(item -> libDaoMap.put(item.getId(), infoMap.get( item.getId())));
        noteIdeaMapper.insertOnlineHistory( JSON.toJSONString(libDaoMap));
        return BaseResponse.success();
    }
}
