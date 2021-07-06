package com.example.demo.consistency;

import java.util.Random;

/**
 * @Author: shiguang
 * @Date: 2021/7/6
 * @Description:
 **/
public class RandomUtils {

    public static int randomAcceptorId(){
        Random random = new Random();
        int id = random.nextInt(Common.ACCEPTOR_COUNT)+1;
        return id;
    }
    public static int randomProposerId(){
        Random random = new Random();
        int id = random.nextInt(Common.PROPOSER_COUNT)+1;
        return id;
    }

}
