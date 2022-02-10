package com.fernando.demo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RedPacketUtil {
    // 红包金额预分配 二倍均值法
    public static List<Integer> divideRedPackage(Integer totalAmount, Integer totalPeopleNum){
        List<Integer> amountList = new ArrayList<>();
        if(totalAmount > 0 && totalPeopleNum > 0){
            Integer restAmount = totalAmount;
            Integer restPeopleNum = totalPeopleNum;

            Random random = new Random();
            for(int i = 0; i < totalPeopleNum - 1; i++){
                // 随即范围为[1, 剩余人均金额的两倍) 开区间可避免极端情况 导致最后金额不够分
                // 注意单位为分
                int amount = random.nextInt(restAmount / restPeopleNum * 2 - 1) + 1;
                restAmount -= amount;
                restPeopleNum--;
                amountList.add(amount);
            }
            // 最后一个随机金额
            amountList.add(restAmount);
        }
        return amountList;
    }
}
