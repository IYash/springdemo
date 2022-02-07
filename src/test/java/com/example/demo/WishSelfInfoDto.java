package com.example.demo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Objects;

/**
 * @Author: shiguang
 * @Date: 2022/1/4
 * @Description:
 **/
@Data
public class WishSelfInfoDto {

    private SimpleUserInfo uinfo;
    private String content;
    private Long likes;
    private Integer pos;
    private Long rtime;
    private Integer state;
    private Integer status;
    @JSONField(name="user_id_str")
    private String userIdStr;

    @Data
    public static class SimpleUserInfo{
        private String uid;
        private String nickname;
        @JSONField(name="image_s")
        private String imageS;
        private Long seq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WishSelfInfoDto that = (WishSelfInfoDto) o;
        return pos.equals(that.pos) && userIdStr.equals(that.userIdStr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, userIdStr);
    }
}
