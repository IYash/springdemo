package com.example.demo.consistency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: shiguang
 * @Date: 2021/7/6
 * @Description:
 **/
public class ConsistencyTest1 {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    //接受者集合（全部）
    private static List<Acceptor> acceptorList=new ArrayList<>();
    //接受者集合（过半）
    private static List<Acceptor> acceptorListQuorum=new ArrayList<>();
    //提交者集合
    private static List<Proposer> proposerList=new ArrayList<>();
    private static Thread thread;
    private static Thread runnable;

    /**
     * 初始化方法
     */
    public static void init(){
        //初始化接受者 acceptor (全部)
        for (int i = 1; i <= Common.ACCEPTOR_COUNT; i++) {
            Acceptor acceptor = new Acceptor();
            acceptor.setId(i);
            acceptor.setResN(0);
            acceptor.setProposals(new HashMap<>());
            acceptorList.add(acceptor);
        }

        //初始化提交者proposer
        for (int i = 1; i <= Common.PROPOSER_COUNT; i++) {
            Proposer proposer = new Proposer();
            proposer.setId(i);
            proposer.setProposals(new HashMap<>());
            proposerList.add(proposer);
        }


    }

    private static void randomAcceptorQuorum() {
        ArrayList<Acceptor> temp = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();
        Random random = new Random();
        for (;;) {
            int id = random.nextInt(Common.ACCEPTOR_COUNT)+1;
            if(ids.contains(id)){
                continue;
            }
            double ceil = (Common.ACCEPTOR_COUNT / 2)+1;
            if(ids.size()==ceil){
                break;
            }

            if(acceptorListQuorum.size()==0){
                Acceptor acceptor = new Acceptor();
                acceptor.setId(id);
                acceptor.setResN(0);
                acceptor.setProposals(new HashMap<>());
                temp.add(acceptor);
                ids.add(id);
            }else{
                Boolean f=false;
                for (Acceptor acceptor : acceptorListQuorum) {
                    if(id==acceptor.getId()){
                        temp.add(acceptor);
                        ids.add(id);
                        f=true;
                    }
                }
                if(!f){
                    Acceptor acceptor = new Acceptor();
                    acceptor.setId(id);
                    acceptor.setResN(0);
                    acceptor.setProposals(new HashMap<>());
                    temp.add(acceptor);
                    ids.add(id);
                }
            }
        }
        acceptorListQuorum=temp;
    }

    public static void main(String[] args) throws InterruptedException {
        init();

        int id = RandomUtils.randomProposerId();//宕机id
        for (int i = 0; i < proposerList.size(); i++) {
            Proposer proposer = proposerList.get(i);
            if(id!=proposer.getId()){//使用Random来模拟网络通信阻塞（宕机）
                runnable = new Thread() {
                    @Override
                    public void run() {
                        try {
                            propose(proposer);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                runnable.start();
            }
        }
        Thread.sleep(4000);
        //宕机的机器重启了
        for (Proposer proposer1 : proposerList) {
            if(proposer1.getId()==id){//找到宕机的机器
                thread = new Thread() {//模拟重启
                    @Override
                    public void run() {
                        try {
                            runnable.join();
                            System.out.println("提交者id：" + id + "重启了");
                            propose(proposer1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        }
    }
    public static void propose( Proposer proposer) throws InterruptedException {
        //1、生成提案
        // 先判断（学习）之前的接受者里面有没有接受之前提议者的提案,没有就自己生成一个提案
        //如果有接受者已经接受了之前提议者的提案，无论自己的提案编号大还是小，都得把自己的提案的value指定为之前的那个提案的value
        randomAcceptorQuorum();
        if(!checkAccept()){//没有接受过提案
            HashMap<Integer, String> map = new HashMap<>();
            map.put(Common.proposerN.incrementAndGet(),"提案"+proposer.getId());
            proposer.setProposals(map);
        }else{
            //之前有接受者接受过提案,只能乖乖用之前的提案值（也就是Map的value使用之前的提案的）
            for (int i = 0; i < acceptorListQuorum.size(); i++) {
                Acceptor acceptor = acceptorListQuorum.get(i);
                Map<Integer, String> proporsal = acceptor.getProposals();
                if(proporsal.size()!=0){
                    for (Map.Entry<Integer, String> entry : proporsal.entrySet()) {
                        Map<Integer, String> map = new HashMap<>();
                        map.put(Common.proposerN.incrementAndGet(),entry.getValue());
                        proposer.setProposals(map);
                        break;
                    }
                    break;
                }
            }
        }

        Integer var1=0;//提案编号
        Map<Integer, String> proporsal = proposer.getProposals();
        Set<Map.Entry<Integer, String>> entries = proporsal.entrySet();
        for (Map.Entry<Integer, String> entry : entries) {
            var1 = entry.getKey();
        }



        //2、prepare请求(这里是对半数以上的接受者进行请求)
        //统计 prepare请求  pok响应的个数
        randomAcceptorQuorum();
        AtomicInteger var2 =new AtomicInteger(0);
        Map<Integer, String> var3;
        String maxAcceptV;
        int maxAcceptN=0;
        Boolean flg=true;
        for (int i = 0; i < acceptorListQuorum.size(); i++) {
            Acceptor acceptor = acceptorListQuorum.get(i);
            var3 = acceptor.prepareReq(var1);
            if(var3!=null){
                var2.incrementAndGet();
                for (Map.Entry<Integer, String> var4 : var3.entrySet()) {
                    Integer key = var4.getKey();
                    if(flg){
                        maxAcceptN=key;
                        maxAcceptV=var4.getValue();
                        flg=false;
                    }
                    if(maxAcceptN<key){
                        maxAcceptN=key;
                        maxAcceptV=var4.getValue();
                    }
                }
            }
        }
        //判断是否收到超过一半响应(包括一半)
        //阶段2，accept请求
        randomAcceptorQuorum();
        AtomicInteger aokCount=new AtomicInteger(0);
        Boolean half = checkHalf(Common.ACCEPTOR_COUNT, var2.intValue());
        if(half){
            for (Acceptor acceptor : acceptorListQuorum) {
                String req = acceptor.acceptReq(proporsal);
                if("aok".equals(req)){
                    aokCount.incrementAndGet();
                }
            }
        }else{
            propose(proposer);
        }
        //如果过半，V被确定，不过半，重新发起Prepare请求
        Boolean var4 = checkHalf(Common.ACCEPTOR_COUNT, aokCount.intValue());
        if(var4){
            //输出一下每个acceptor的AcceptV
            for (Acceptor acceptor : acceptorListQuorum) {
                for (Map.Entry<Integer, String> entry : acceptor.getProposals().entrySet()) {
                    System.out.println("接受者的id:"+acceptor.getId()+"最终acceptN :"+entry.getKey()+",最终acceptV:"+entry.getValue());
                }
            }
            return;//结束
        }else{
            propose(proposer);
        }

    }

    /**
     *  判断是否超过一半响应
     * @param total
     * @param var1
     * @return true 过半  false 不过半
     */
    public static Boolean checkHalf(int total,int var1){
        double var = total / 2.0;
        if(var>var1){
            return false;
        }else{return true;}
    }

    /**
     *
     * @return false 没有接收提案 true 有接受过提案
     */
    public static Boolean checkAccept(){
        Boolean res=false;
        for (int i = 0; i < acceptorListQuorum.size(); i++) {
            Acceptor acceptor = acceptorListQuorum.get(i);
            Map<Integer, String> proporsal = acceptor.getProposals();
            if(proporsal.size()!=0){//之前有接受过提案
                return true;
            }
        }
        return res;
    }


}
