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
        int[] nums =new int[]{1,4,3};
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
    @Test
    public void test3(){
        int[] nums = new int[]{1,2,3};
        int len = nums.length;
        int[] result = findTarget(nums,0,len-1,1);
        System.out.println(result[0]+"---"+result[1]);
    }
    //在排序数组中查找给定目标值的开始位置和结束位置,考虑二分法
    private int[] findTarget(int[] nums,int left,int right,int target){
        if(left>=right){
            if(nums[left] == target) return new int[]{left,left};
            else return new int[]{-1,-1};
        }
        int mid = (left+right)>>1;
        int[] midT ;
        int[] leftT ;
        int[] rightT ;
        int midV = nums[mid];
        if(midV == target) {
            midT = new int[]{mid, mid};
            rightT = findTarget(nums,mid+1,right,target);
            leftT = findTarget(nums,left,mid-1,target);
            return combineResult(leftT,midT,rightT);
        }else if(midV < target){
            return findTarget(nums,mid+1,right,target);
        }else{
            return findTarget(nums,left,mid-1,target);
        }
    }

    private int[] combineResult(int[] leftT, int[] midT, int[] rightT) {
        int left = -1;
        int right = -1;
        if(midT[0]>0){
            left = midT[0];
            right = midT[1];
            if(leftT[0]>=0){
                left = leftT[0];
            }
            if(rightT[1]>=0){
                right = rightT[1];
            }
        }else{
            if(leftT[0]>=0) return leftT;
            if(rightT[0]>=0) return rightT;
        }
        return new int[]{left,right};
    }
    @Test
    public void test4(){
        int[] leftT = new int[]{-1,-1};
        int[] midT = new int[]{5,5};
        int[] rightT = new int[]{6,8};
        int[] result = combineResult(leftT,midT,rightT);
        System.out.println(result[0]+"---"+result[1]);
    }
}
