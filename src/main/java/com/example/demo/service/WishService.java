package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.constant.WishVerifyStatusEnum;
import com.example.demo.dal.WishMapper;
import com.example.demo.dal.WishTmpMapper;
import com.example.demo.entity.WishDO;
import com.example.demo.entity.WishDto;
import com.example.demo.util.JedisUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.example.demo.constant.RedisKey.*;

/**
 * @Author: shiguang
 * @Date: 2021/12/7
 * @Description:
 **/
@Service
@Slf4j
public class WishService {

    @Resource
    WishMapper wishMapper;
    @Resource
    WishTmpMapper wishTmpMapper;

    Jedis activityOtherRedis = JedisUtil.manualInitJedis();

    Map<String,List<WishDO>> sources = Maps.newConcurrentMap();
    @Resource
    private ExecutorService commonExecutorService ;

    @Value("${wish.circuit.period:180000}")
    private int PERIOD;//3分钟

    public static final String COMMON="common_";
    public static final String PERFECT ="perfect_";
    @Value("${wish.total.count:16}")
    public int TOTAL_COUNT;
    @Value("${wish.perfect.count:4}")
    public int PERFECT_COUNT;
    @Value("${wish.self.count:1}")
    public int SELF_COUNT;
    @Value("#{'${wish.layout.strategy:2,2,1,1,2,0,1,1,2,2,1,1,0,0,1,1}'.split(',')}")
    private List<Integer> layout;
    @Value("${wish.pool.size:60000}")
    private int poolSize;
    @Value("${wish.pull.size:1000}")
    private int pullSize;
    @Value("${wish.pull.section:300}")
    private int section;
    @Value("${wish.self.list:10}")


    @PostConstruct
    public void runTask(){
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                buildSource(section);
            }
        }, 100, PERIOD);
    }
    //首页数据获取
    public WishDto view(String cursor, Long userId,Long wishId){//cursor设计，维护各自的pos index_pos1_pos2_pos3 分片，优质，个人，安全
        if(StringUtils.equals("-1",cursor)) {return WishDto.defaultDto();}
        int[]  indexes = calculateIndex(cursor);
        //拼接输出轨道
        StringBuilder sb = new StringBuilder();
        sb.append(indexes[0]);
        //perfect
        List<WishDO> perfectPool = subList(indexes[0],indexes[1],PERFECT_COUNT,PERFECT,sb);
        //self,这里使用的非实时策略
        List<WishDO> selfPool = Lists.newArrayList();
        if(Objects.nonNull(activityOtherRedis.get(String.format(USER_WISH_POSTED,userId)))){
            if(!posCheck(indexes[2])){
                selfPool = querySelfPool(userId,false);
                selfPool = subWishDOS(indexes[2],SELF_COUNT,selfPool,sb);
            }
        }else{
            isLastPos(sb);
        }
        //common
        int commonSize = TOTAL_COUNT - perfectPool.size() - selfPool.size();
        List<WishDO> commonPool = subList(indexes[0],indexes[3],commonSize,COMMON,sb);
        //构建输出矩阵
        WishDto wishDto =  WishDto.build(perfectPool,commonPool,selfPool,layout,sb.toString());
        //分享出来的数据是实时数据，需要点亮异步,点亮动作由调用层完成
        if(Objects.nonNull(wishId)){
            Map<String,Object> params = Maps.newHashMap();
            buildParams("user_id",userId,params);
            buildParams("id",wishId,params);
            buildParams("size",1,params);
            List<WishDO> shareDOS = queryByCondition(params);
            if(!CollectionUtils.isEmpty(shareDOS)){
                wishDto.setShareDO(shareDOS.get(0));
            }
        }
        return wishDto;
    }

    private List<WishDO> querySelfPool(Long userId,boolean fromDb) {
        List<WishDO> selfPool = Lists.newArrayList();
        String key = String.format(USER_WISH_JSON, userId);
        String self = activityOtherRedis.get(key);
        if(StringUtils.isEmpty(self)){
            List<WishDO> selfList = querySelfPoolFromDB(userId);
            if(!CollectionUtils.isEmpty(selfList)){
                selfList = filterAndScore(selfList);
                self = JSONObject.toJSONString(selfList);
                activityOtherRedis.setex(key,PERIOD, self);
            }
        }else{
            if(fromDb){
                selfPool = querySelfPoolFromDB(userId);
            }else{
                selfPool = JSONObject.parseArray(self,WishDO.class);
            }
        }
        selfPool = filterAndScore(selfPool);
        selfPool = selfPool.stream().map(vo->{vo.setStatus(WishVerifyStatusEnum.SELF.getStatus());return vo;}).collect(Collectors.toList());
        Collections.sort(selfPool, Comparator.comparing(WishDO::getScore).reversed());
        return selfPool;
    }

    private List<WishDO> querySelfPoolFromDB(Long userId) {
        Map<String,Object> params = Maps.newHashMap();
        buildParams("userId", userId,params);
        buildParams("size",10,params);
        List<WishDO> selfList = wishMapper.queryByCondition(params);
        return selfList;
    }
    //更新间隔较短，个人列表展示
    public List<WishDO> selfPool(Long userId){
        String oldV = activityOtherRedis.get(String.format(USER_WISH_POSTED,userId));
        if(Objects.isNull(oldV)) {
            return Lists.newArrayList();
        }
        return querySelfPool(userId,(Long.valueOf(oldV)-System.currentTimeMillis()) > PERIOD/3);
    }

    public void postWish(String content,Long userId){
        Long id = activityOtherRedis.incr(idGeneratorKey);
        WishDO vo = WishDO.build(id,userId,content);
        wishMapper.addWish(vo);
        activityOtherRedis.set(String.format(USER_WISH_POSTED,userId),System.currentTimeMillis()+"");//持久化key，不过期
        activityOtherRedis.incr(POSTED_WISHES);
    }
    //仅用于mock数据
    public void batchPostWish(String content,Long userId,int batch){
        List<WishDO> vos = Lists.newArrayList();
        for(int i=0;i<batch;i++){
            vos.add(WishDO.build(activityOtherRedis.incr(idGeneratorKey),userId+i,content+i));
        }
        if(!CollectionUtils.isEmpty(vos)){
            wishMapper.batchAddWish(vos);
            activityOtherRedis.incrBy(POSTED_WISHES,Long.valueOf(vos.size()));
        }
    }
    private List<WishDO> subList(int poolIndex,int pos,int size,String key,StringBuilder sb){
        List<WishDO> pool = sources.get(key+poolIndex);
        if(CollectionUtils.isEmpty(pool)) {
            isLastPos(sb);
            return Lists.newArrayList();
        }
        return subWishDOS(pos, size, pool,sb);
    }

    private List<WishDO> subWishDOS(int pos, int size, List<WishDO> pool,StringBuilder sb) {
        List<WishDO> result = Lists.newArrayList();
        if(posCheck(pos)){
            isLastPos(sb);
            return result;
        }
        int total = pool.size();
        if(pos>total){
            isLastPos(sb);
            return result;
        }
        WishDO vo;
        for(int i=pos;i<total;i++){
            if(result.size()>=size){
                break;
            }
            vo = pool.get(i);
            if(punishCheck(vo)){
                result.add(vo);
            }
        }
        if(result.size()>=size){
            sb.append("_").append(pos+size);
        }else{
            isLastPos(sb);
        }
        return result;
    }

    private void isLastPos(StringBuilder sb) {
        sb.append("_").append("-1");
    }

    private boolean posCheck(int pos) {
        return pos == -1;
    }

    private int[] calculateIndex(String cursor) {
        if(StringUtils.isEmpty(cursor)){
            return new int[]{new Random().nextInt(sources.size()/2),0,0,0};
        }else{
            String[] parts = cursor.split("_");
            return new int[]{Integer.valueOf(parts[0]),Integer.valueOf(parts[1]),Integer.valueOf(parts[2]),Integer.valueOf(parts[3])};
        }
    }
    //审核回调
    public void updateStatus(Long userId,Long id,int status){
        String key = String.format(EXCLUDE_KEY,id);
        if(status == WishVerifyStatusEnum.PUNISH.getStatus()){
            activityOtherRedis.setex(key,PERIOD,"1");
        }else{
            activityOtherRedis.del(key);
        }
        wishMapper.updateStatus(userId,id,status);
    }

    public List<WishDO> queryByCondition(Map<String,Object> params){
        return wishMapper.queryByCondition(params);
    }
    //分片任务
    public Map<String,List<WishDO>> buildSource(int section){//有限次召回数据，进行shuffle，召回数据支持配置
        List<WishDO> perfectGroup = Lists.newArrayList();
        List<WishDO> commonGroup = Lists.newArrayList();
        //先从source中取出数据
        List<WishDO> nextCircle = Lists.newArrayList();
        List<WishDO> lastSourceList = Lists.newArrayList();
        sources.entrySet().stream().forEach(
                entry->{
                    lastSourceList.addAll(entry.getValue());
                }
        );
        //从数据库召回
        int cSize = 0;
        int cCount = 0;
        Map<String,Object> params = Maps.newHashMap();
        long curTime = System.currentTimeMillis();
        List<WishDO> dos = Lists.newArrayList();
        WishDO lastDO = null;
        long startTime = curTime-48*PERIOD;
        long endTime = curTime;
        nextCircle.addAll(lastSourceList);
        while(cSize < poolSize && cCount < poolSize/pullSize){
            buildParams("startTime",startTime,params);
            buildParams("endTime",endTime,params);
            buildParams("size",pullSize,params);
            dos = queryByCondition(params);
            if(CollectionUtils.isEmpty(dos)||dos.size()<pullSize){
                break;
            }
            cSize += dos.size();
            cCount += 1;
            lastDO = dos.get(dos.size()-1);
            endTime = lastDO.getUpdateTime().getTime()+1;
            nextCircle.removeAll(dos);
            nextCircle.addAll(dos);
        }
        nextCircle = filterAndScore(nextCircle);
        if(nextCircle.size()>=poolSize){
            nextCircle = nextCircle.subList(0,poolSize);
        }
        //分组
        nextCircle.stream().forEach(vo-> {
                    if(vo.getStatus()==WishVerifyStatusEnum.COMMON.getStatus()){
                        commonGroup.add(vo);
                    }else if(vo.getStatus()==WishVerifyStatusEnum.HIGHLIGHT.getStatus()){
                        perfectGroup.add(vo);
                    }
                }
        );
        //分组后进行shuffle，之后内部排序
        Collections.shuffle(perfectGroup);
        Collections.shuffle(commonGroup);
        //切分,备份
        wishTmpMapper.removeTmp();
        if(!CollectionUtils.isEmpty(perfectGroup)){
            commonExecutorService.execute(()-> wishTmpMapper.backTmp(perfectGroup));
        }
        if(!CollectionUtils.isEmpty(commonGroup)){
            commonExecutorService.execute(()-> wishTmpMapper.backTmp(commonGroup));
        }
        fillMap(perfectGroup,section,3,PERFECT,sources);
        fillMap(commonGroup,section,6,COMMON,sources);
        return sources;
    }
    //分片填充
    public void fillMap(List<WishDO> source,int parts,int size,String key,Map<String,List<WishDO>> map){
        int index = 0;
        int total = source.size();
        if(total == 0) {
            return;
        }
        for(int i=0;i<parts;i++){
            List<WishDO> part = Lists.newArrayList();
            while(true){
                if(index == total){
                    index = 0;
                }
                if(part.size()<size){
                    part.add(source.get(index++));
                }else{
                    map.put(key+i,part);
                    break;
                }
            }
        }
    }
    //数据库数据召回
    private Map<String,Object> buildParams(String key,Object obj,Map<String,Object> map){
        map.put(key,obj);
        return map;
    }
    //分片时过滤并计算分数
    private List<WishDO> filterAndScore(List<WishDO> vos){
        if(CollectionUtils.isEmpty(vos)){
            return Lists.newArrayList();
        }
        return vos.stream().filter(vo-> punishCheck(vo))
                .filter(vo->vo.getStatus().intValue() != WishVerifyStatusEnum.PUNISH.getStatus())
                .map(vo->{vo.score();return vo;}).filter(vo->vo.getScore() >= 0.4).collect(Collectors.toList());
    }
    //不符合安全规则
    private boolean punishCheck(WishDO vo) {
        return Objects.isNull(activityOtherRedis.get(String.format(EXCLUDE_KEY, vo.getId())));
    }

    public boolean postedCheck(Long userId){
        String oldV = activityOtherRedis.get(String.format(USER_WISH_POSTED,userId));
        if(StringUtils.isEmpty(oldV)) {
            Long lastPostTime =  wishMapper.queryLastPostTime(userId).getTime();
            if(Objects.nonNull(lastPostTime)){
                activityOtherRedis.set(String.format(USER_WISH_POSTED,userId),lastPostTime+"");
                return true;
            }
        }
        return false;
    }

    public Long posted(){
        String posted = activityOtherRedis.get(POSTED_WISHES);
        if(StringUtils.isEmpty(posted)) {
            return 10000L;
        }
        return Long.valueOf(posted);
    }
}
