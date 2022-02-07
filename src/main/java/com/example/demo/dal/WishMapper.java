package com.example.demo.dal;

import com.example.demo.entity.WishDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: shiguang
 * @Date: 2021/12/7
 * @Description:
 **/
@Mapper
@Component
public interface WishMapper {

    String DB_TABLE = "activity_wish_source";
    @Select("<script> select * from "+DB_TABLE+" where 1=1 " +
            "<if test = \"updateTime != null\"> " +
            " and update_time between #{startTime} and #{endTime} </if>" +
            "<if test = \"userId != null\"> " +
            " and user_id = #{userId} </if> " +
            "<if test = \"id != null\"> " +
            " and id = #{id} </if>"+
            " order by update_time desc limit #{size}</script>" )
    List<WishDO> queryByCondition(Map<String,Object> params);


    @Insert("insert into "+DB_TABLE+"(id,user_id,content) values (#{id},#{userId},#{content})")
    void addWish(WishDO vo);

    @Insert("<script> insert into "+DB_TABLE+"(id,user_id,content) values " +
            "<foreach collection='vos' item='vo' separator=','>" +
            "(#{vo.id},#{vo.userId},#{vo.content})" +
            "</foreach>" +
            "</script>")
    void batchAddWish(@Param("vos") List<WishDO> vos);

    @Update("update "+DB_TABLE+" set status = #{status} where user_id= #{userId} and id = #{id}")
    void updateStatus(@Param("userId") Long userId,@Param("id") Long id,@Param("status") int status);

    @Update("update "+DB_TABLE+" set likes = likes + 1 where user_id = #{userId} and id = #{wishId}")
    void updateLikes(@Param("userId") Long userId,@Param("wishId") Long wishId);

    @Select("select max(likes) from "+DB_TABLE+" where user_id = #{userId}")
    int maxLikes(Long userId);
    @Select("select max(update_time) from "+DB_TABLE+" where user_id = #{user_id}")
    Date queryLastPostTime(Long userId);
}
