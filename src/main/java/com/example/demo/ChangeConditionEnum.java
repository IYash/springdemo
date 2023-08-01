package org.example.interceptor;

import lombok.Getter;

/**
 * @Description:
 * @Author: cl.h
 * @Date: 2023/6/7
 */
@Getter
public enum ChangeConditionEnum {

    D_CONDITION("0", "0", "默认逻辑"),
    ON_CONDITION("0", "1", "执行新逻辑,当新表查不到数据时不回查老表"),
    RE_CONDITION("1", "1", "执行新逻辑,当新表查不到数据时回查老表"),
    ;

    /**
     * 0 表示否定，非0 表示肯定
     */
    private String originFlag;
    private String logicFlag;
    private String description;

    private ChangeConditionEnum(String originFlag, String logicFlag, String description) {
        this.originFlag = originFlag;
        this.logicFlag = logicFlag;
        this.description = description;
    }

    public boolean needChange() {
        return getLogicFlag().equals("1");
    }

    public boolean needOrigin() {
        return getOriginFlag().equals("1");
    }
}
