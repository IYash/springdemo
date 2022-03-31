package com.example.author;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.testng.collections.Maps;

/**
 * @Author: shiguang
 * @Date: 2022/3/9
 * @Description: 用于解析数仓数据
 **/
@Component
@Slf4j
public class AuthorConditionBuilder {

    /**
     * 解析数仓数据成需要的数据格式
     *
     * @param uid
     * @return
     */
    public AuthorCondition buildCondition(String uid) {
        return AuthorCondition.buildCondition(null, Maps.newHashMap());
    }
}
