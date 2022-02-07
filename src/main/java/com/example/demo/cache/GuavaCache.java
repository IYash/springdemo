package com.example.demo.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: shiguang
 * @Date: 2021/8/19
 * @Description:
 **/
@Component
@Slf4j
public class GuavaCache {
    //线程池异步刷新guava缓存
    ListeningExecutorService executorService;
    LoadingCache<String, Set<String>> bulletScreenPostTrigWord;
    Set<String> baseCache = Sets.newHashSet("hello","world","hi","you");
    private final String BASE_KEY = "myCache";
    @PostConstruct
    public void init(){
        initExecutor();
        initCache();
    }

    private void initCache() {
        bulletScreenPostTrigWord = CacheBuilder.newBuilder()
                .maximumSize(1)
                .refreshAfterWrite(2, TimeUnit.SECONDS)
                .build(new CacheLoader<String, Set<String>>() {
                    @Override
                    public Set<String> load(String key) throws Exception {
                        try{
                            log.info("bulletScreenPostCache load! key:{}", key);
                            return baseCache;
                        }catch (Exception e){
                            log.warn("bulletScreenPostCache load error! key:{},msg:{}", key,e.getMessage());
                        }
                        return Sets.newHashSet();
                    }
                    @Override
                    public ListenableFuture<Set<String>> reload(String key, Set<String> oldValue) throws Exception {
                        return executorService.submit(()->{
                                    try{
                                        log.info("bulletScreenPostCache reload! key:{}", key);
                                        return baseCache;
                                    }catch (Exception e){
                                        log.warn("bulletScreenPostCache reload error! key:{},msg:{}", key,e.getMessage());
                                        return oldValue;
                                    }
                                }
                        );
                    }
                });
    }

    private void initExecutor() {
        ThreadFactory loadToMapThreadFactory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("LoadingBulletScreenConfToSet-%d").build();
        this.executorService = MoreExecutors.listeningDecorator(new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), loadToMapThreadFactory));
    }
    public boolean contains(String content){
        try{
            return bulletScreenPostTrigWord.get(BASE_KEY).contains(content);
        }catch (Exception e){
            log.warn("cache error:{}",e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) throws Exception{
        GuavaCache cache = new GuavaCache();
        cache.init();
        String[] cs = new String[]{"hello","hi","you","world","perfect","you","world","wow"};
        int len = cs.length;
        for(int i=0;i<len;i++){
            System.out.println(cache.contains(cs[i]));
            Thread.sleep(1000);
        }
        System.in.read();
    }
}
