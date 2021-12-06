package com.example.demo.entity;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class NoteIdeaLibInfo {
    /**
     * 记录id
     */
    private Integer id;

    /**
     * 最后更新人
     */
    private String updateBy;


    /**
     * 灵感标题
     */
    private String title;

    /**
     * 灵感垂类id
     */
    private Integer labelId;

    /**
     * 灵感描述
     */
    private String desc = "";

    /**
     * 灵感关联话题id
     */
    private String pageId = "";

    /**
     * 推荐理由
     */
    private List<Integer> reason = new ArrayList<>();

    /**
     * 热度值
     */
    private Long hot = 0L;

    /**
     * 笔记id列表
     */
    private List<String> noteIds = new ArrayList<>();

    /**
     * 是否经典选题
     * 0 - 空
     * 1 - 是
     * 2 - 否
     * @zhongli 产品(白浅)设计字段有个空，所以别纠结为啥是否字段还有个空了。
     */
    private Integer classic = 0;

    /**
     * 笔记灵感配置有无异常
     */
    private Integer status = 0;

    /**
     * 笔记灵感已经在本周应用
     */
    private Integer inCurrent;

    /**
     * 笔记灵感已经在下周应用
     */
    private Integer InNext;

    /**
     * 最后更新时间，秒级时间戳
     */
    private Integer updateAt;

    public NoteIdeaLibInfo(NoteIdeaLibDao dao) {
        this.id = dao.getId();
        this.title = dao.getTitle();
        this.desc = dao.getDesc();
        this.labelId = dao.getLabelId();
        this.pageId = dao.getPageId();
        this.reason = JSON.parseArray(dao.getReason(), Integer.class);
        this.hot = dao.getHot();
        this.noteIds = JSON.parseArray(dao.getNotes(), String.class);
        this.updateBy = dao.getUpdateBy();
        this.classic = dao.getClassic();
        this.updateAt = (int)(dao.getUpdateAt().getTime() / 1000);
    }
}
