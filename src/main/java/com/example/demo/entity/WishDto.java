package com.example.demo.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: shiguang
 * @Date: 2021/12/8
 * @Description:
 **/
@Data
public class WishDto {
    private String cursor;
    private List<List<WishDO>> wishList = Lists.newArrayList();
    private WishDO shareDO;

    public static WishDto defaultDto(){
        WishDto dto = new WishDto();
        dto.setCursor("-1");
        return dto;
    }
    public static WishDto build(List<WishDO> perfect,List<WishDO> common,List<WishDO> self,List<Integer> layout,String cursor){
        int size = layout.size();
        int pSize = perfect.size();
        int cSize = common.size();
        int sSize = self.size();
        WishDto dto = new WishDto();
        dto.setCursor(cursor);
        List<WishDO> vos = null;
        Map<Long,WishDO> selfMap = Maps.newHashMap();
        self.stream().forEach(vo->selfMap.put(vo.getId(),vo));
        int type = 0;
        int pPos = 0;
        int cPos = 0;
        int sPos = 0;
        for(int i=0;i<size;i++){
            if(i % 4 == 0){
                vos = Lists.newArrayList();
                dto.getWishList().add(vos);
            }
            type = layout.get(i);
            if(type == 2){
                if(pPos<pSize){
                    pPos = replaceWishDO(perfect, selfMap, vos, pPos);continue;
                }
            }else if(type == 0){
                if(sPos<sSize){
                    sPos = replaceWishDO(self,selfMap,vos,sPos);continue;
                }
            }
            if(cPos<cSize){
                cPos = replaceWishDO(common, selfMap, vos, cPos);
            }
        }
        return dto;
    }

    private static int replaceWishDO(List<WishDO> common,Map<Long,WishDO> selfMap, List<WishDO> vos, int cPos) {
        WishDO vo;
        vo = common.get(cPos++);
        if (Objects.nonNull(selfMap.get(vo))) {
            vo = selfMap.get(vo.getId());//替换后数据相对准确
            selfMap.remove(vo);
        }
        vos.add(vo);
        return cPos;
    }
}
