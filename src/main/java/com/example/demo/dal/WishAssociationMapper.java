package com.example.demo.dal;

import com.example.demo.entity.WishAssociation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: shiguang
 * @Date: 2021/12/9
 * @Description:
 **/
@Mapper
@Component
public interface WishAssociationMapper {

    String DB_TABLE = "activity_wish_association";
    @Insert("<script> insert into "+DB_TABLE+"(wish_id,like_user_id,user_id) values " +
            "<foreach collection='associations' item='tmp' separator=','>" +
            "(#{tmp.wishId},#{tmp.likeUserId},#{tmp.userId})" +
            "</foreach>" +
            "</script>")
    void batchAddAssociation(@Param("associations") List<WishAssociation> associations);

    @Insert("insert into "+DB_TABLE+"(wish_id,like_user_id,user_id) values " +
            "(#{wishId},#{likeUserId},#{userId})")
    void addAssociation(WishAssociation association);
}
