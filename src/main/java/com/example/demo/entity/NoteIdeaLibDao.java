package com.example.demo.entity;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

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
    //笔记灵感2.2添加字段
    /**
     * 灵感唯一比较，直接使用hash
     */
    private int uniqueCode;
    /**
     * 渠道 0为全部
     */
    private int canal;
    /**
     * 热点数据描述
     */
    private String hotData;
    /**
     * 召回日期
     */
    private Date recallDate;
    /**
     * 昨日发布数量
     */
    private int publishLastDay;
    /**
     * 最近一周发布数量
     */
    private int publishLastWeek;
    /**
     * 可见人群，ugc/pgc
     */
    private String userType;
    /**
     * 是否加权
     */
    private int priority;
    /**
     * 时效性
     */
    private int timeliness;
    /**
     * 关键字[]
     */
    private String keyword;
    /**
     * 相关关键词[]
     */
    private String keywordRel;
    /**
     * 手动添加笔记[]
     */
    private String notesManual;
    /**
     * 运营召回笔记[]
     */
    private String notesRecall;
    /**
     * 上架笔记数量
     */
    private int topN;
    /**
     * 计算指标
     */
    private String calQuota;
    /**
     * 是否展示，默认展示默认0:展示 1:移动到灵感库,不展示2:上架
     */
    private Integer status;
}
