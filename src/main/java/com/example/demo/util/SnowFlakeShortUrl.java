package com.example.demo.util;

/**
 * @Author: shiguang
 * @Date: 2021/6/28
 * @Description: twitter的snowflake算法，使用snowFlake算法生成一个整数，然后转化为62进制变成一个短地址url
 **/
public class SnowFlakeShortUrl {
    /**
     * 其实时间戳
     */
    private final static long START_TIMESTAMP = 1480166465631L;
    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数
    private final static long MACHINE_BIT =5; //机器标识占用的位数
    private final static long DATA_CENTER_BIT =5; //数据中心占用的位数
    /**
     * 每一部分的最大值
     */
    private final static long MAX_SEQUENCE = -1L^(-1L<<SEQUENCE_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L<<MACHINE_BIT);
    private final static long MAX_DATA_CENTER_NUM = -1L^(-1L<<DATA_CENTER_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    private long dataCenterId;
    private long machineId;
    private long sequence =0L;
    private long lastTimeStamp=-1L;

    private long getNextMill(){
        long mill = getNewTimeStamp();
        while(mill <=lastTimeStamp){
            mill = getNewTimeStamp();
        }
        return mill;
    }
    private long getNewTimeStamp() {
        return System.currentTimeMillis();
    }

    /**
     * 根据指定的数据中心id和机器标志id生成指定的序列号
     * @param dataCenterId
     * @param machineId
     */
    public SnowFlakeShortUrl(long dataCenterId,long machineId){
        if(dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0){
            throw  new IllegalArgumentException("datacenterId can't be greater than max or less than 0");
        }
        if(machineId > MAX_MACHINE_NUM || machineId<0){
            throw  new IllegalArgumentException("machineId can't be greater than max or less than 0");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个id,这个效率理论上是无法满足的
     * @return
     */
    public synchronized long nextId(){
        long currTimeStamp = getNewTimeStamp();
        if(currTimeStamp < lastTimeStamp){
            throw new RuntimeException("clock move backwards.refusing to generate nextid");
        }
        if(currTimeStamp == lastTimeStamp){
            //相同毫秒内，序列号自增
            sequence = (sequence+1) & MAX_SEQUENCE;
            //同一毫秒内的序列数已经达到最大
            if(sequence == 0L){
                currTimeStamp = getNextMill();
            }
        }else{
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }
        lastTimeStamp = currTimeStamp;
        return (currTimeStamp - START_TIMESTAMP) << TIMESTAMP_LEFT //时间戳部分
                |dataCenterId << DATA_CENTER_LEFT  //数据中心部分
                |machineId << MACHINE_LEFT //机器识别部分
                |sequence; //序列号部分
    }
}

