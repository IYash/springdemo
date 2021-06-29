package com.example.demo;

import lombok.Data;
import org.junit.Test;

/**
 * @Author: shiguang
 * @Date: 2021/6/29
 * @Description: 使用递归的方式处理
 **/
public class LinkedListTest {

    @Test
    public void testSwap(Node head){
        swapPair(head);
    }
    public Node swapPair(Node head){
        if(head == null || head.next == null){
            return head;
        }
        Node newHead = head.next;
        head.next = swapPair(newHead.next);
        newHead.next = head;
        return newHead;
    }
    @Data
    static class Node{
        Node next;
    }

}
