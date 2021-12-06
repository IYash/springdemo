package com.example.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class NoteIdeaLibDao {

    /**
     * 记录id
     */
    private Integer id;

    /**
     * 灵感标题
     */
    private String title;

    /**
     * 灵感描述
     */
    private String desc;

    /**
     * 灵感垂类id
     */
    private Integer labelId;

    /**
     * 灵感关联话题id
     */
    private String pageId;

    /**
     * 推荐理由
     */
    private String reason;

    /**
     * 热度值
     */
    private Long hot;

    /**
     * 笔记id列表
     */
    private String notes;

    /**
     * 最后更新人
     */
    private String updateBy;

    /**
     * 是否经典选题
     * 0 - 空
     * 1 - 是
     * 2 - 否
     * @zhongli 产品(白浅)设计字段有个空，所以别纠结为啥是否字段还有个空了。
     */
    private Integer classic;

    /**
     * 记录最后更新时间
     */
    private Date updateAt;
}
