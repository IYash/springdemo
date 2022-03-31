package com.example.demo.kafka;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @Author: shiguang
 * @Date: 2022/3/21
 * @Description:
 **/
public class MyProducer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "dt-mongo-sit-kafka01.int.xiaohongshu.com:9092,dt-mongo-sit-kafka02.int.xiaohongshu.com:9092,dt-mongo-sit-kafka03.int.xiaohongshu.com:9092");

        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);

        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer producer = new KafkaProducer<Integer, String>(props);
        String key = "6239bfb5000000000000c989";//noteId

        producer.send(new ProducerRecord<String, String>("note_human_level_change", key, buildJson(key)));
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("MyProducer.main");

    }

    @Data
    static class Msg {
        String bizId;
        String bizType;
        List<DataInfo> dataList;
    }

    @Data
    static class DataInfo {
        private int type;
        private int value;
    }

    public static String buildJson(String key) {
        Msg msg = new Msg();
        msg.setBizId(key);
        msg.setBizType("NOTE");
        DataInfo info = new DataInfo();
        info.setType(20002);
        info.setValue(2);
        msg.setDataList(Lists.newArrayList(info));
        return JSONObject.toJSONString(msg);
    }
}
