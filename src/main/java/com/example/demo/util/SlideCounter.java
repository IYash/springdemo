package com.example.demo.util;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: shiguang
 * @Date: 2021/6/29
 * @Description: 基于滑动窗口的计数器，简易版，只考虑时间窗口内的数据；升级版处理指定窗口内的数据
 **/

@Data
public class SlideCounter {

    private AtomicInteger[] count;//循环使用的队列
    private int duration; //时间窗口大小
    private long lastTimestamp;//最后记录时间

    public SlideCounter(int duration){
        this.duration = duration;
        this.count = new AtomicInteger[duration];
        initCount(0,duration-1);
        this.lastTimestamp = System.currentTimeMillis();
    }

    //添加到滑动窗口中
    public void addCount(){ //支持并发
        long curTimeStamp = System.currentTimeMillis();
        //判断是否是在一个窗口内的，是则添加，否则重新计数
        long gap = curTimeStamp - lastTimestamp + 1;
        if(gap > 2 * duration) {
            initCount(0,duration-1);
            lastTimestamp = curTimeStamp;
            count[0].incrementAndGet();
        } else if(gap > duration){
            int pos = (int)gap - duration;
            initCount(0,pos-1);
            count[pos-1].incrementAndGet();
        } else {
            int pos = (int)gap-1;
            count[pos].incrementAndGet();
        }
    }
    public void initCount(int from,int to){
        while(from<=to){
            if(count[from] == null) count[from] = new AtomicInteger();
            else count[from].getAndSet(0);
            from++;
        }
    }
    public int totalCount(){//这里存在一个问题，感觉需要一个watermark，即时范围计算出来的可能存在一定问题
        int total = 0;
        for(int i=0;i<duration;i++){
            total += count[i].get();
        }
        return total;
    }

}
