package com.example.demo;

import com.example.demo.entity.NoteIdeaLibDao;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: shiguang
 * @Date: 2022/3/31
 * @Description: 召回数据的排序对象
 **/

public class NoteIdeaComparator {
    
    /**
     * 灵感库粗排，局部有序
     * @param daos
     * @return
     */
    public static List<NoteIdeaLibDao> sort(List<NoteIdeaLibDao> daos){
        if (CollectionUtils.isEmpty(daos)){
            return daos;
        }
        Map<Integer, List<NoteIdeaLibDao>> collect = daos.stream().collect(Collectors.groupingBy(NoteIdeaLibDao::getCanal));
        List<NoteIdeaLibDao> search = collect.get(NoteIdeaCanal.poka_search.val);
        List<NoteIdeaLibDao> page = collect.get(NoteIdeaCanal.poka_page.val);
        List<NoteIdeaLibDao> activate = collect.get(NoteIdeaCanal.ugc_activate.val);
        List<NoteIdeaLibDao> up = collect.get(NoteIdeaCanal.ugc_activate.val);
        List<NoteIdeaLibDao> competitor = collect.getOrDefault(NoteIdeaCanal.competitor.val,Lists.newArrayList());
        List<NoteIdeaLibDao> result = Lists.newArrayList();
        result.addAll(activateComp(activate));
        result.addAll(upComp(up));
        result.addAll(searchComp(search));
        result.addAll(pageComp(page));
        result.addAll(competitor);
        return result;
    }
    /**
     * poka搜索侧热点
     *
     * @param daos
     * @return
     */
    public static List<NoteIdeaLibDao> searchComp(List<NoteIdeaLibDao> daos) {
        if (CollectionUtils.isEmpty(daos)) {
            return daos;
        }
        Collections.sort(daos, (o1, o2) -> {
            String o1HotData = o1.getHotData();
            String o2HotData = o2.getHotData();
            HotDataHolder1.HotData o1Data = HotDataHolder1.parseHotData(o1HotData);
            HotDataHolder1.HotData o2Data = HotDataHolder1.parseHotData(o2HotData);
            int o1MaxUv = o1Data.getMaxUv();
            int o2MaxUv = o2Data.getMaxUv();
            if (o1MaxUv > o2MaxUv) {
                return 1;
            } else if (o1MaxUv == o2MaxUv) {
                String o1Rate = substr(o1Data.getOneDayGrowthRate());
                String o2Rate = substr(o2Data.getOneDayGrowthRate());
                if (Double.valueOf(o1Rate).doubleValue() >= Double.valueOf(o2Rate).doubleValue()) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        });
        return daos;
    }

    /**
     * poka话题侧热点
     *
     * @param daos
     * @return
     */
    public static List<NoteIdeaLibDao> pageComp(List<NoteIdeaLibDao> daos) {
        if (CollectionUtils.isEmpty(daos)) {
            return daos;
        }
        Collections.sort(daos, (o1, o2) -> {
            String o1HotData = o1.getHotData();
            String o2HotData = o2.getHotData();
            HotDataHolder1.HotData o1Data = HotDataHolder1.parseHotData(o1HotData);
            HotDataHolder1.HotData o2Data = HotDataHolder1.parseHotData(o2HotData);
            int o1Vv = o1Data.getL17dNewP7TotalNoteVvInc();
            int o2Vv = o2Data.getL17dNewP7TotalNoteVvInc();
            if (o1Vv > o2Vv) {
                return 1;
            } else if (o1Vv == o2Vv) {
                int o1tn = o1Data.getL17dTotalNoteNumInc();
                int o2tn = o2Data.getL17dTotalNoteNumInc();
                if (o1tn >= o2tn) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        });
        return daos;
    }

    /**
     * ugc高活话题
     *
     * @return
     */
    public static List<NoteIdeaLibDao> activateComp(List<NoteIdeaLibDao> daos) {
        if (CollectionUtils.isEmpty(daos)) {
            return daos;
        }
        Collections.sort(daos, (o1, o2) -> {
            String o1HotData = o1.getHotData();
            String o2HotData = o2.getHotData();
            HotDataHolder1.HotData o1Data = HotDataHolder1.parseHotData(o1HotData);
            HotDataHolder1.HotData o2Data = HotDataHolder1.parseHotData(o2HotData);
            int o1npn = o1Data.getNewPostNum();
            int o2npn = o2Data.getNewPostNum();
            if (o1npn >= o2npn) {
                return 1;
            } else {
                return -1;
            }
        });
        return daos;
    }

    /**
     * ugc飙升话题
     *
     * @return
     */
    public static List<NoteIdeaLibDao> upComp(List<NoteIdeaLibDao> daos) {
        if (CollectionUtils.isEmpty(daos)) {
            return daos;
        }
        Collections.sort(daos, (o1, o2) -> {
            String o1HotData = o1.getHotData();
            String o2HotData = o2.getHotData();
            HotDataHolder1.HotData o1Data = HotDataHolder1.parseHotData(o1HotData);
            HotDataHolder1.HotData o2Data = HotDataHolder1.parseHotData(o2HotData);
            int o1npn = o1Data.getNewPostNum();
            int o2npn = o2Data.getNewPostNum();
            if (o1npn > o2npn) {
                return 1;
            } else if (o1npn == o2npn) {
                String o1sr = substr(o1Data.getSoarRate());
                String o2sr = substr(o2Data.getSoarRate());
                if (Double.valueOf(o1sr).doubleValue() >= Double.valueOf(o2sr).doubleValue()) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        });
        return daos;
    }

    private static String substr(String source){
        if (StringUtils.isEmpty(source)){
            return source;
        }
        int len = source.length();
        if (len>4){
            return source.substring(0,4);
        }
        return source;
    }
}
