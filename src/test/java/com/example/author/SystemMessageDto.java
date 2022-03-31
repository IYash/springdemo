package com.example.author;

import lombok.Data;

/**
 * @Author: shiguang
 * @Date: 2021/11/23
 * @Description:
 **/
@Data
public class SystemMessageDto {
    private String title;
    private String userId;
    private String senderId;//5b84b6c3d24e10000169b0b1
    private String content;
    private String source;
    private String link = "";
    private String tag;
    private String buttonDesc;
    private String buttonLink;
    private String token;//758303640581607424

    public static SystemMessageDto buildDto(String title, String userId, String senderId, String content, String source,
                                            String link, String tag, String buttonDesc, String buttonLink, String token
                                            ) {
        SystemMessageDto dto = new SystemMessageDto();
        dto.title = title;
        dto.userId = userId;
        dto.senderId = senderId;
        dto.content = content;
        dto.source = source;
        dto.link = link;
        dto.tag = tag;
        dto.buttonDesc = buttonDesc;
        dto.buttonLink = buttonLink;
        dto.token = token;

        return dto;
    }

    public static SystemMessageDto buildSystemDto(String title, String userId, String senderId, String content, String source,
                                                  String link, String tag, String buttonDesc, String buttonLink, String token
    ) {
        return buildDto(title, userId, senderId, content, source, link, tag, buttonDesc, buttonLink, token);
    }
}
