package com.example.demo;

import lombok.Data;
import org.assertj.core.util.Lists;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: shiguang
 * @Date: 2021/4/27
 * @Description:
 **/
@Data
public class TrendIntrudeResponse {
    private static final String setter="setter";//发起人
    private static final String uploader="uploader";//博主
    private static final String principal="principal";//主理人
    private static final String art="art";//艺术
    private static final String music="music";//音乐
    private static final String sport="sport";//运动

    public static <T>List<TrendInfo<T>> convertToTrendInfo(List<NoteCacheDto<T>> noteCacheDtos){
        if(CollectionUtils.isEmpty(noteCacheDtos)) return Lists.newArrayList();
//        Map<String,NoteCacheDto> dtoMap = noteCacheDtos.stream().collect(Collectors.toMap(NoteCacheDto::getKey, Function.identity(),(v1, v2)->v1));
//        if(Objects.isNull(dtoMap.get(setter))) dtoMap.put(setter,NoteCacheDto.from(setter,Lists.newArrayList()));
//        if(Objects.isNull(dtoMap.get(uploader))) dtoMap.put(uploader,NoteCacheDto.from(uploader,Lists.newArrayList()));
//        if(Objects.isNull(dtoMap.get(principal))) dtoMap.put(principal,NoteCacheDto.from(principal,Lists.newArrayList()));
//        if(Objects.isNull(dtoMap.get(art))) dtoMap.put(art,NoteCacheDto.from(art,Lists.newArrayList()));
//        if(Objects.isNull(dtoMap.get(music))) dtoMap.put(music,NoteCacheDto.from(music,Lists.newArrayList()));
//        if(Objects.isNull(dtoMap.get(sport))) dtoMap.put(sport,NoteCacheDto.from(sport,Lists.newArrayList()));
        List<TrendInfo<T>> result = Lists.newArrayList();
        noteCacheDtos.stream().forEach(noteCacheDto -> {
            result.add(TrendInfo.from(noteCacheDto.getKey(),noteCacheDto.getValues()));
        });
        return result;
    }
    @Data
    public static class TrendInfo<T>{
        private String trend_name;
        private List<T> note_list;

        public static <T>TrendInfo<T> from(String trend, List<T> noteList){
            TrendInfo response = new TrendInfo();
            System.out.println(trend);
            response.setTrend_name(trend);
            response.setNote_list(noteList);
            return response;
        }
    }
    @Data
    public static class Art{
        private String name;
    }
}
