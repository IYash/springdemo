package com.example.demo.dal;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @Author: shiguang
 * @Date: 2021/12/9
 * @Description:
 **/
@Component
@Mapper
public interface WishSparkMapper {

    String DB_TABLE = "activity_wish_spark";
    @Insert("insert into "+DB_TABLE+"(user_id,spark_level) values(#{userId},#{sparkLevel}) on duplicate key update spark_level = #{sparkLevel}")
    void addOrUpdateSparkLevel(@Param("userId") Long userId, @Param("sparkLevel") Integer sparkLevel);

    @Select("select count(1) from "+DB_TABLE+" where user_id = #{userId} and spark_level >= #{sparkLevel}")
    int fireWorkCheck(@Param("userId") Long userId, @Param("sparkLevel") Integer sparkLevel);
}
