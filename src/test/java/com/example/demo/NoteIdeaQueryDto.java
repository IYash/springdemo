package com.example.demo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * @Author: shiguang
 * @Date: 2022/3/15
 * @Description: 查询对象封装
 **/
@Data
public class NoteIdeaQueryDto {
    private Long id;
    /**
     * 灵感id
     */
    private Integer libId;
    /**
     * 渠道
     */
    private Integer canal;
    /**
     * 垂类
     */
    private Integer labelId;
    /**
     * 召回日期
     */
    private Long recallDate;

    /**
     * 标题
     */
    private String title;
    /**
     * 唯一标识
     */
    private Integer uniqueCode;
    /**
     * 入库日期
     */
    private Long optionDate;

    private Integer page = 1;
    private Long offset;

    private Integer size = 10;

    private Integer status = 0;

    private Integer orderBy = 0;


    public long getOffset(){
        return Long.valueOf(page - 1) * size;
    }

    public int getUniqueCode(){
        return 0;
    }
    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public String getStartDate(){
        if (Objects.nonNull(recallDate)) {
            return sdf.format(new Date(recallDate));
        } else {
            return sdf.format(new Date(optionDate));
        }
    }

    public String getEndDate(){
        if (Objects.nonNull(recallDate)) {
            return sdf.format(new Date(recallDate + 86400000));
        } else {
            return sdf.format(new Date(optionDate + 86400000));
        }
    }

    /**
     * 灵感库查询对象
     * @param canal
     * @param title
     * @param recallDate
     * @param page
     * @param size
     * @return
     */
    public static NoteIdeaQueryDto buildSourceInfo(Integer canal,String title,String recallDate,Integer page,Integer size){
        NoteIdeaQueryDto dto = commonInfo(null,canal,title,recallDate,null,page,size);
        dto.setStatus(0);
        return dto;
    }

    /**
     * 备选库查询对象
     * @param title
     * @param optionDate
     * @param page
     * @param size
     * @return
     */
    public static NoteIdeaQueryDto buildOptionInfo(Integer labelId,String title, String optionDate, Integer page, Integer size) {
        NoteIdeaQueryDto dto = commonInfo(labelId,null,title,null,optionDate,page,size);
        dto.setStatus(1);
        return dto;
    }

    /**
     * 上线库查询对象
     * @param labelId
     * @param canal
     * @param title
     * @param page
     * @param size
     * @return
     */
    public static NoteIdeaQueryDto buildUsedInfo(Integer labelId, Integer canal, String title, Integer page, Integer size) {
        NoteIdeaQueryDto dto = commonInfo(labelId,canal,title,null,null,page,size);
        dto.setStatus(2);
        return dto;
    }

    /**
     * 权重查询对象
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    public static NoteIdeaQueryDto buildWeightInfo(Integer labelId,Integer page,Integer size){
        return commonInfo(labelId,null,null,"0",null,page,size);
    }

    public static NoteIdeaQueryDto commonInfo(Integer labelId,Integer canal,String title,String recallDate,String optionDate,Integer page,Integer size){
        NoteIdeaQueryDto dto = new NoteIdeaQueryDto();
        if (Objects.nonNull(canal)){
            dto.setCanal(canal);
        }
        if (Objects.nonNull(labelId)){
            dto.setLabelId(labelId);
        }
        if (!StringUtils.isEmpty(title)){
            dto.setTitle(title);
        }
        if (!StringUtils.isEmpty(recallDate)){
            dto.setRecallDate(Long.valueOf(recallDate));
        }
        if (!StringUtils.isEmpty(optionDate)){
            dto.setOptionDate(Long.valueOf(optionDate));
        }
        dto.setPage(page);
        dto.setSize(size);
        return dto;
    }

    public static NoteIdeaQueryDto syncNoteIdeaDto(Long id,Long activateDate,Integer size){
        NoteIdeaQueryDto dto = new NoteIdeaQueryDto();
        dto.setId(id);
        dto.setOptionDate(Long.valueOf(parseDateString(activateDate)));
        dto.setSize(size);
        return dto;
    }
    public static NoteIdeaQueryDto syncNoteIdeaBaseData(Integer id,Integer libId,Long recallDate,Integer size){
        NoteIdeaQueryDto dto = new NoteIdeaQueryDto();
        if (Objects.nonNull(libId)){
            dto.setLibId(libId);
        }
        if (Objects.nonNull(id)){
            dto.setId(id.longValue());
        }
        if (Objects.nonNull(recallDate)) {
            dto.setRecallDate(Long.valueOf(parseDateString(recallDate)));
        }
        dto.setSize(size);
        return dto;
    }
    public static NoteIdeaQueryDto selectByTitle(String title){
        NoteIdeaQueryDto dto = new NoteIdeaQueryDto();
        dto.setTitle(title);
        dto.setUniqueCode(title.hashCode());
        return dto;
    }

    public static NoteIdeaQueryDto selectFromSource(String title){
        NoteIdeaQueryDto dto = selectByTitle(title);
        dto.setStatus(1);
        dto.setCanal(0);
        return dto;
    }
    /**
     * 将时间转成 2022-02-22 00:00:00 这样的零点状态
     * @param time
     * @return
     */
    /**
     * yyyy-MM-dd 00:00:00的日期
     *
     * @param
     * @return
     */
    public static String parseDateString(long time){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(time));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long dateTime = calendar.getTimeInMillis();
        return sdf.format(dateTime);
    }
}

