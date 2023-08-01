package org.example.interceptor;

/**
 * @Description:
 * @Author: cl.h
 * @Date: 2023/6/7
 */
public class ChangeConditionHolder {
    public static final ThreadLocal<ChangeConditionEnum> CURRENT_CONDITION = ThreadLocal.withInitial(
            () -> {
                return ChangeConditionEnum.D_CONDITION;
            }
    );

    public static void setCondition(ChangeConditionEnum condition){
        CURRENT_CONDITION.set(condition);
    }

    public static ChangeConditionEnum getCondition(){
        return CURRENT_CONDITION.get();
    }

    public static void remove(){
        CURRENT_CONDITION.remove();
    }
}
