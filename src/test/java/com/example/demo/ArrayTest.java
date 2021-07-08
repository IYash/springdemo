package com.example.demo;

import org.junit.Test;



/**
 * @Author: shiguang
 * @Date: 2021/7/1
 * @Description:
 **/
public class ArrayTest {
    @Test
    public void test1() {//有序数组移除重复元素
        int[] nums = new int[]{1,2,3,4,4,5,5,6,6,7,8,8,9};
//        System.out.println(removeDuplicated1(nums));
//        System.out.println(removeDuplicated(nums));

        System.out.println(removeVal(nums,4));
    }
    private int  removeDuplicated(int[] nums){//这个形式可以直接截取数据就可以返回数组
        int n = nums.length;
        if(n==0) return 0;
        int fast = 1,slow=1;
        while(fast<n){
            if(nums[fast] != nums[fast-1]) {
                nums[slow] = nums[fast];
                ++slow;
            }
            ++fast;
        }
        return slow;
    }
    private int removeDuplicated1(int[] nums){
        int n = nums.length;
        if(n==0) return 0;
        int slow = 1;
        int val = nums[0];
        for(int i=1;i<n;i++){
            if(val!=nums[i]) {val = nums[i];slow++;}
        }
        return slow;
    }
    //双指针一个有意思的地方就是指针的起始位置,可同一个方向也可相反方向
    private int removeVal(int[] nums,int val){//存在需要保留的元素重复赋值的处理
        int n = nums.length;
        int slow = 0;
        int fast =0;
        for(;fast<n;fast++){
            if(nums[fast]!= val){
                nums[slow]=nums[fast];
                slow ++;
            }
        }
        return slow;
    }
    @Test
    public void test2(){
        int[] nums =new int[]{2,4,3};
        nums = nextSeq(nums);
        System.out.println(nums[0]+"--"+nums[1]+"--"+nums[2]);
    }
    //获取数列的下一个序列nums=[1,2,3]->[1,3,2]
    public int[] nextSeq(int[] nums){
        int len = nums.length;
        int minLeft = 0;
        int right = len-1;
        int maxLeft = -1;
        //找到最小的移动范围
        for(int j=len-1;j>minLeft;j--){
            for(int i=j-1;i>=minLeft;i--){
                if(nums[j]>nums[i]){
                    minLeft = i+1;
                    if(i>maxLeft){
                        right = j;
                        maxLeft = i;
                    }
                    break;
                }
            }
        }
        if(maxLeft!=-1){
            swap(nums,maxLeft,right);
            //重新排列范围内的值至最小序列
            for(int i=maxLeft+1;i<len-1;i++){
                for(int j=i+1;j<len;j++){
                    if(nums[i]>nums[j]){
                        swap(nums,i,j);
                    }
                }
            }
        }
        return nums;
       }

    private void swap(int[] nums, int left, int right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }
}
