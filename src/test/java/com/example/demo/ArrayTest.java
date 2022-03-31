package com.example.demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        int[] nums =new int[]{4,7,6};
        nums = nextSeq2(nums);
        System.out.println(nums[0]+"--"+nums[1]+"--"+nums[2]);
    }
    public int[] nextSeq2(int[] nums){
        //1.从右侧相邻位置开始查找,当nums[i] > nums[i-1]表示i-1是一个可替换的位置，需要保证i-1足够大
        //2.从i->n中找出最小值，替换i-1
        //3.i->n完成升序排序
        int len = nums.length;
        int i = len-1;
        int left = -1;
        while(i>0){
            if (nums[i]>nums[i-1]) {
                left = i -1;
                break;
            }
            i--;
        }
        if (left!=-1){
            int rMin = Integer.MAX_VALUE;
            int index = len-1;
            int right = 0;
            while(index > left){
                if (rMin > nums[index]){
                    rMin = nums[index];
                    right = index;
                }
                index -- ;
            }
            swap(nums,left,right);
            for(int k=left+1;k<len;k++){
                for(int j=i+1;j<len;j++){
                    if(nums[k]>nums[j]){
                        swap(nums,k,j);
                    }
                }
            }
        }
        return nums;
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

    @Test
    public void test5(){

    }
    //有效的数独
    private boolean isValidSudoku(char[][] board){
        //行判断，列判断，块判断
        //init data
        Map<Integer,Integer>[] rows = new HashMap[9];
        Map<Integer,Integer>[] columns = new HashMap[9];
        Map<Integer,Integer>[] boxes = new HashMap[9];
        for(int i=0;i<9;i++){
            rows[i] = new HashMap<>();
            columns[i] = new HashMap<>();
            boxes[i] = new HashMap<>();
        }
        //validate a board
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                char num = board[i][j];
                if(num!='.'){
                    int n = (int)num;
                    int box_index = (i/3)*3 +j/3;
                    //keep the current cell value
                    rows[i].put(n,rows[i].getOrDefault(n,0)+1);
                    columns[j].put(n,columns[j].getOrDefault(n,0)+1);
                    boxes[box_index].put(n,boxes[box_index].getOrDefault(n,0)+1);
                    //check if this value has been already seen
                    if(rows[i].get(n)>1 || columns[j].get(n)>1||boxes[box_index].get(n)>1){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Test
    public void test6(){

    }
    private boolean[][] line = new boolean[9][9];
    private boolean[][] column = new boolean[9][9];
    private boolean[][][] block = new boolean[3][3][9];
    private boolean valid = false;
    private List<int[]> spaces = new ArrayList<>();

    //数独的解
    private void solveSudoku(char[][] board){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board[i][j] =='.'){
                    spaces.add(new int[]{i,j});
                }else{
                    int digit = board[i][j]-'0'-1;
                    line[i][digit] = column[j][digit]=block[i/3][j/3][digit]=true;
                }
            }
        }
        dfs(board,0);
    }
    public void dfs(char[][] board,int pos){
        if(pos == spaces.size()){
            valid = true;
            return;
        }
        int[] space =  spaces.get(pos);
        int i = space[0],j = space[1];
        for(int digit = 0;digit<9 && !valid;++digit){
            if(!line[i][digit] && !column[j][digit] && !block[i/3][j/3][digit]){
                line[i][digit] = column[j][digit]=block[i/3][j/3][digit]=true;
                board[i][j] = (char)(digit + '0' +1);
                dfs(board,pos+1);
                line[i][digit] = column[j][digit]=block[i/3][j/3][digit]=false;
            }
        }
    }
    @Test
    public void test7(){
        System.out.println(countAndSay(10));
    }
    //对字符"1"进行n次的外观描述
    private String countAndSay(int n){
        if(n==1) return "1";
        String result = countAndSay(n-1);
        System.out.println(result);
        return parseResult(result);
    }
    //描述字符串
    private String parseResult(String result) {
        if(result.length() == 1) return "11";
        int len = result.length();
        StringBuilder sb = new StringBuilder();
        int i=0,j=1;
        for(;i < len && j<len;){
            while(j<len){
                if(result.charAt(i) != result.charAt(j)){
                    sb.append(j-i).append(result.charAt(i));
                    i=j;
                    j ++;
                    break;
                }else {
                    j++;
                }
            }
        }
        if(j-i>=1) sb.append(j-i).append(result.charAt(i));
        return sb.toString();
    }
    @Test
    public void test8(){
        int[] candidates = {2,2,3,4};
        int target = 7;
        combinationSum(candidates,target);
    }
    //组合总和，假设candidates是升序存储
    private List<List<Integer>> combinationSum(int[] candidates,int target){
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> combine = new ArrayList<>();
        dfs(candidates,target,ans,combine,0);
        return ans;
    }

    private void dfs(int[] candidates, int target, List<List<Integer>> ans, List<Integer> combine,int idx) {
        if(idx == candidates.length){
            return;
        }
        if(target == 0){
            ans.add(new ArrayList<>(combine));
            return;
        }
        //直接跳过
        dfs(candidates,target,ans,combine,idx+1);
        //选择当前数
        if(target - candidates[idx] >= 0 ){
            combine.add(candidates[idx]);
            dfs(candidates,target-candidates[idx],ans,combine,idx);
            combine.remove(combine.size()-1);
        }
    }
    //未排序数组中查找缺失的最小正整数，简单的方式就是通过map记录数据，依次判断数据是否在map中，需要过多的额外空间；这里使用常数的额外空间
    //使用标记处理方式如下，使用数值替换也是一种可行的思路
    private int lostMinNum(int[] nums){
        //[1,2,4,5],获取数组的长度，大于数组长度的数字直接跳过，小于1的数字直接跳过
        int len = nums.length;
        for(int i=0;i<len;i++){
            if(nums[i]<=0) nums[i] = len+1;
        }
        for(int i=0;i<len;i++){
            int num = Math.abs(nums[i]);
            if(num<=len){
                nums[num-1] = -Math.abs(nums[num-1]);
            }
        }
        for(int i=0;i<len;i++){
            if(nums[i]>0) return i+1;
        }
        return len+1;
    }
    //给定n个非负整数表示每个宽度为1的柱子的高度图，计算按此排列的柱子，下雨之后能接多少水
    public int trap(int[] height){
        int ans = 0;
        int left = 0,right=height.length-1;
        int leftMax = 0,rightMax = 0;
        while(left<right){
            leftMax = Math.max(leftMax,height[left]);
            rightMax = Math.max(rightMax,height[right]);
            if(height[left]<height[right]){
                ans += leftMax - height[left];
                ++left;
            }else{
                ans += rightMax - height[right];
                --right;
            }
        }
        return ans;
    }
}
