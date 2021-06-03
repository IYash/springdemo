package com.example.demo.metric;

import com.example.demo.util.JedisUtil;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: shiguang
 * @Date: 2021/4/10
 * @Description:
 **/
@Configuration
public class MetricConfig {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags(){
        return registry -> registry.config().commonTags("region","us-east-8");
    }
    @Bean
    public MeterBinder jedisCall(JedisUtil jedisUtil){
        return registry->
            Gauge.builder("jedis.call",jedisUtil::inc).register(registry);
    }
    @Bean
    public MeterFilter renameRegionTagMeterFilter() {
        return MeterFilter.renameTag("http.server.requests", "region", "area");
    }

}
