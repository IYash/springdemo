package com.example.demo.dal;

import com.example.demo.entity.WishDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: shiguang
 * @Date: 2021/12/16
 * @Description:
 **/
@Component
@Mapper
public interface WishTmpMapper {
    String DB_TABLE = "activity_wish_tmp";
    @Delete("delete from "+DB_TABLE)
    void removeTmp();

    @Insert("<script> insert into "+DB_TABLE +" values " +
            "<foreach collection='dos' item='tmp' separator=','>" +
            "(#{tmp.id},#{tmp.userId},#{tmp.content},#{tmp.status},#{tmp.likes},#{tmp.createTime},#{tmp.updateTime})" +
            "</foreach>" +
            "</script>")
    void backTmp(@Param("dos") List<WishDO> dos);
}
