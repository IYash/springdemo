package com.example.demo.biz;

import java.util.Comparator;

/**
 * @Author: shiguang
 * @Date: 2021/3/31
 * @Description:
 **/
@FunctionalInterface
public interface MyFunction<T,U> {

    T apply(T t,U u);

    public static <T> MyFunction<T,T> minBy(Comparator<? super T> comparator) {
        return (a, b) -> comparator.compare(a, b) <= 0 ? a : b;
    }
}
