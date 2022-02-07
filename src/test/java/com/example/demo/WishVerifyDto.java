package com.example.demo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @Author: shiguang
 * @Date: 2021/12/27
 * @Description:
 **/
@Data
public class WishVerifyDto {
    @JSONField(name = "target_id")
    private String targetId;
    @JSONField(name = "target_type")
    private String targetType;
    private String version;
    @JSONField(name = "callback_params")
    private CallbackParam callbackParams;
    private String result;
    private List<RejectInfo> detail;
    @JSONField(name="process_time")
    private Long processTime;

    @Data
    public static class RejectInfo{
        @JSONField(name="data_field")
        private String dataField;
        @JSONField(name="content_id")
        private String contentId;
        @JSONField(name="tag_info")
        private List<TagInfo> tagInfo;
        private String action;
    }
    @Data
    public static class TagInfo{
        private Long id;
        private String tagName;
    }
    @Data
    public static class CallbackParam{
        @JSONField(name="task_type")
        private String taskType;
    }
}
