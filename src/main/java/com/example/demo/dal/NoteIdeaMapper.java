package com.example.demo.dal;

import com.example.demo.entity.NoteIdeaLibDao;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: shiguang
 * @Date: 2021/11/26
 * @Description:
 **/
@Mapper
@Component
public interface NoteIdeaMapper {

    @Insert("INSERT INTO note_idea_online_history(info)" +
            "VALUES(#{info})")
    int insertOnlineHistory(String info);

    @Select("select lib_id from note_idea_used where preview = 2" )
    List<Integer> selectLibIds();

    @Select("<script> select * from `note_idea_libs` where id in " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}</foreach></script>" )
    List<NoteIdeaLibDao> noteIdeas(@Param("ids") List<Integer> ids);

}
