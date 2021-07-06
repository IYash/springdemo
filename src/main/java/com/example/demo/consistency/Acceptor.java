package com.example.demo.consistency;

import lombok.Data;

import java.util.Map;

/**
 * @Author: shiguang
 * @Date: 2021/7/6
 * @Description: paxos算法的中的acceptor
 **/
@Data
public class Acceptor {
    /**
     * 标识
     */
    int id;
    /**
     * key为提案编号，value为提案值
     */
    Map<Integer,String> proposals;
    /**
     * 接受过的最大提案编号N
     */
    int resN;

    /**
     * @param proposerN 提案编号
     *
     */
    public Map<Integer, String> prepareReq(int proposerN ){
        if(proposerN<this.resN){ //不响应
            System.out.println("proposerN:"+proposerN+",this.resN"+resN);
            return null;
        }else{
            this.resN=proposerN;
            //响应pok
            return this.proposals;
        }
    }
    /**
     *  第二阶段的 accept请求
     */
    public String acceptReq(Map<Integer,String> map){
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getKey()>=this.resN){
                this.setProposals(map);
                return "aok";
            }
        }
        return "no";
    }

}
