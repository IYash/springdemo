package com.example.demo;



import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: shiguang
 * @Date: 2021/11/9
 * @Description:
 **/
public class CandidatesTest {
    List<int[]> freq = Lists.newArrayList();
    List<List<Integer>> ans = Lists.newArrayList();
    List<Integer> sequences = Lists.newArrayList();

    public List<List<Integer>> combinationSum2(int[] candidates,int target){
        Arrays.sort(candidates);
        for(int num:candidates){
            int size = freq.size();
            if(freq.isEmpty() || num!= freq.get(size-1)[0]){
                freq.add(new int[]{num,1});
            }else{
                ++freq.get(size-1)[1];
            }
        }
        dfs(0,target);
        return ans;
    }
    public void dfs(int pos,int rest){
        if (rest == 0){
            ans.add(Lists.newArrayList(sequences));
            return ;
        }
        if(pos == freq.size()|| rest<freq.get(pos)[0]){
            return ;
        }
        dfs(pos+1,rest);
        int most = Math.min(rest/freq.get(pos)[0],freq.get(pos)[1]);
        for(int i=1;i<=most;i++){
            sequences.add(freq.get(pos)[0]);
            dfs(pos+1,rest-i* freq.get(pos)[0]);
        }
        for(int i=1;i<=most;i++){
            sequences.remove(sequences.size()-1);
        }
    }
    @Test
    public void test(){
        int[] candidates = new int[]{10,1,2,7,6,1,5};
        combinationSum2(candidates,8);
    }
}
