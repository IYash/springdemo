package com.example.demo.dal;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Author: shiguang
 * @Date: 2021/4/15
 * @Description:
 **/
@Mapper
@Component
public interface TeachingVideoProgressMapper {

    @Insert("INSERT INTO teaching_video_progress(user_id, note_id, progress)" +
            "VALUES(#{userId}, #{noteId}, #{progress}) ON DUPLICATE KEY UPDATE progress = if(progress<#{progress},#{progress},progress)")
    int upsertCreatorTeachingVideoProgress(@Param("userId") Long userId, @Param("noteId") Long noteId, @Param("progress") Double progress);
}
