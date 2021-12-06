package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @Author: shiguang
 * @Date: 2021/11/25
 * @Description:
 **/
@Data
public class ActivityDemo {
    private String  image;
    private String  link;
    private String  name;
    private String  desc;
    private Long    startTime;
    private Long    endTime;
    private Integer status;

    public static List<ActivityDemo> demoActivity(){
        String source = "[{\n" +
                "\t\t\t\"image\": \"https://fe-img-qc.xhscdn.com/athena-creator/61c0ef29770135b22fc60298eee062d01fa03b90\",\n" +
                "\t\t\t\"link\": \"https://pages.xiaohongshu.com/picasso_pages/eevee/museum?naviHidden=yes&utm_source=social&useNativeChannel=yes&fullscreen=true**&source=*creator_square*\",\n" +
                "\t\t\t\"name\": \"听说一到晚上 博物馆的文物就成精了… ?\",\n" +
                "\t\t\t\"desc\": \"限时百万流量 快分享身边的有趣文物\",\n" +
                "\t\t\t\"start_time\": 1637683200000,\n" +
                "\t\t\t\"end_time\": 1640015940000,\n" +
                "\t\t\t\"status\": 1\n" +
                "\t\t}, {\n" +
                "\t\t\t\"image\": \"https://fe-img-qc.xhscdn.com/athena-creator/cff401f9f9e2fde752b5c0fab57e15af7567924e\",\n" +
                "\t\t\t\"link\": \"xhsdiscover://item/61978dac000000002103aa8f?page_entrance_type=creator_banner\",\n" +
                "\t\t\t\"name\": \"创作学院 - 视频新星创作营\",\n" +
                "\t\t\t\"desc\": \"涨粉培训课程来袭  提升视频创作技巧\",\n" +
                "\t\t\t\"start_time\": 1637510400000,\n" +
                "\t\t\t\"end_time\": 1637855940000,\n" +
                "\t\t\t\"status\": 1\n" +
                "\t\t}, {\n" +
                "\t\t\t\"image\": \"https://fe-img-qc.xhscdn.com/athena-creator/3a9f0b1b8199d7463ccca6e895a6726c83de6099\",\n" +
                "\t\t\t\"link\": \"https://pages.xiaohongshu.com/picasso_pages/eevee/dongrirehan?naviHidden=yes&utm_source=social&useNativeChannel=yes&fullscreen=true\",\n" +
                "\t\t\t\"name\": \"冬日热汗打卡\",\n" +
                "\t\t\t\"desc\": \"冬天不养膘 健身打卡领流量\",\n" +
                "\t\t\t\"start_time\": 1637251200000,\n" +
                "\t\t\t\"end_time\": 1640879940000,\n" +
                "\t\t\t\"status\": 1\n" +
                "\t\t}, {\n" +
                "\t\t\t\"image\": \"https://fe-img-qc.xhscdn.com/athena-creator/c9939ac21705db0deafc9f81100dd7da8f01c8cf\",\n" +
                "\t\t\t\"link\": \"https://pages.xiaohongshu.com/picasso_pages/eevee/lowplastic?naviHidden=yes&utm_source=social&useNativeChannel=yes&fullscreen=true&source=creator_square\",\n" +
                "\t\t\t\"name\": \"100件低塑的小事\",\n" +
                "\t\t\t\"desc\": \"打卡赢流量，更有神秘礼包等你拿\",\n" +
                "\t\t\t\"start_time\": 1636905600000,\n" +
                "\t\t\t\"end_time\": 1639583940000,\n" +
                "\t\t\t\"status\": 1\n" +
                "\t\t}, {\n" +
                "\t\t\t\"image\": \"https://fe-img-qc.xhscdn.com/athena-creator/6386082e92f45870240a02d14d59cb2f10a3f4c6\",\n" +
                "\t\t\t\"link\": \"https://pages.xiaohongshu.com/picasso_pages/eevee/shopliverecruiting?naviHidden=yes&utm_source=creator_square\",\n" +
                "\t\t\t\"name\": \"宝藏店铺招募令\",\n" +
                "\t\t\t\"desc\": \"1v1官方扶持，花式奖励等你拿\",\n" +
                "\t\t\t\"start_time\": 1636905600000,\n" +
                "\t\t\t\"end_time\": 1643558340000,\n" +
                "\t\t\t\"status\": 1\n" +
                "\t\t}, {\n" +
                "\t\t\t\"image\": \"https://fe-img-qc.xhscdn.com/athena-creator/9e74c53fc456d01e304742362e53d29568008713\",\n" +
                "\t\t\t\"link\": \"https://pages.xiaohongshu.com/picasso_pages/eevee/internationalstudents?naviHidden=yes&utm_source=social&useNativeChannel=yes&fullscreen=true&source=creator_square\",\n" +
                "\t\t\t\"name\": \"留学那些事第2期\",\n" +
                "\t\t\t\"desc\": \"畅聊留学那些事，瓜分百万流量大奖\",\n" +
                "\t\t\t\"start_time\": 1636819200000,\n" +
                "\t\t\t\"end_time\": 1640966340000,\n" +
                "\t\t\t\"status\": 1\n" +
                "\t\t}, {\n" +
                "\t\t\t\"image\": \"https://fe-img-qc.xhscdn.com/athena-creator/83ab56f34fdc7fe7074e4e03a5135bd194f631d4\",\n" +
                "\t\t\t\"link\": \"https://pages.xiaohongshu.com/picasso_pages/eevee/REDbookstore_1?naviHidden=yes&utm_source=social&useNativeChannel=yes&fullscreen=true&source=creator_square\",\n" +
                "\t\t\t\"name\": \"RED解忧书店\",\n" +
                "\t\t\t\"desc\": \"说书评书分享书，购书基金等你拿\",\n" +
                "\t\t\t\"start_time\": 1636473600000,\n" +
                "\t\t\t\"end_time\": 1641830340000,\n" +
                "\t\t\t\"status\": 1\n" +
                "\t\t}, {\n" +
                "\t\t\t\"image\": \"https://fe-img-qc.xhscdn.com/athena-creator/7cb28b23ad173bbc3dfc73fe48bdb9fbceb1c9ba\",\n" +
                "\t\t\t\"link\": \"https://pages.xiaohongshu.com/picasso_pages/eevee/haiwaidaren_zhaomu?naviHidden=yes&utm_source=social&useNativeChannel=yes&fullscreen=true&source=creator_square\",\n" +
                "\t\t\t\"name\": \"海外达人招募计划\",\n" +
                "\t\t\t\"desc\": \"千万流量寻找歪果仁vlogger\",\n" +
                "\t\t\t\"start_time\": 1636300800000,\n" +
                "\t\t\t\"end_time\": 1639065540000,\n" +
                "\t\t\t\"status\": 1\n" +
                "\t\t}, {\n" +
                "\t\t\t\"image\": \"https://fe-img-qc.xhscdn.com/athena-creator/c9a87284e6c02d6744c999d2385a35b593063bb9\",\n" +
                "\t\t\t\"link\": \"https://pages.xiaohongshu.com/picasso_pages/eevee/redarena?naviHidden=yes&utm_source=social&useNativeChannel=yes&fullscreen=true*&source=*creator_square\",\n" +
                "\t\t\t\"name\": \"RED竞技场\",\n" +
                "\t\t\t\"desc\": \"十万现金招募更多体育创作者\",\n" +
                "\t\t\t\"start_time\": 1636300800000,\n" +
                "\t\t\t\"end_time\": 1639065540000,\n" +
                "\t\t\t\"status\": 1\n" +
                "\t\t}, {\n" +
                "\t\t\t\"image\": \"https://fe-img-qc.xhscdn.com/athena-creator/d1c9d2807d9567315bb69c990be2ef5974fab341\",\n" +
                "\t\t\t\"link\": \"https://pages.xiaohongshu.com/picasso_pages/eevee/designerv2?naviHidden=yes&utm_source=social&useNativeChannel=yes&fullscreen=true&source=creator_square\",\n" +
                "\t\t\t\"name\": \"新潮设计师企划\",\n" +
                "\t\t\t\"desc\": \"新潮设计师企划2.0正式开启\",\n" +
                "\t\t\t\"start_time\": 1636041600000,\n" +
                "\t\t\t\"end_time\": 1640966340000,\n" +
                "\t\t\t\"status\": 1\n" +
                "\t\t}]";
        return JSONObject.parseArray(source,ActivityDemo.class);
    }
}
