package com.example.demo.dal;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @Author: shiguang
 * @Date: 2021/12/10
 * @Description:
 **/
@Component
@Mapper
public interface WishUserMapper {
    String DB_TABLE ="activity_wish_user";

    @Insert("insert into "+DB_TABLE+" values (#{wishId},#{userId})")
    void addAssociation(@Param("wishId") Long wishId, @Param("userId") Long userId);

    @Select("select user_id from "+DB_TABLE+" where wish_id = #{wishId}")
    Long queryUserId(Long wishId);
}
