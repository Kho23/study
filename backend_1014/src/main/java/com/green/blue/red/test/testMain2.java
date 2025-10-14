package com.green.blue.red.test;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

@Slf4j
public class testMain2 {
    public static void main(String[] args) {
        List<Integer> randomData = new ArrayList<>();
        for(int i=0;i<20;i++){
            randomData.add((int)(Math.random()*100));
        }
        List<Predicate<Integer>> list = Arrays.asList(
                (num)->num%3==0,
                (num)->num%5==0,
                (num)->num%7==0,
                (num)->num>3,
                (num)->num<20
        );
            Map<String, List<Boolean>> resultMap = new HashMap<>();
            List<Boolean> trueList = new ArrayList<>();
            List<Boolean> falseList = new ArrayList<>();
        for(int i : randomData){
            List<Boolean> result = list.stream().map(l->l.test(i)).toList();
            result.forEach(j->{
                if(j) trueList.add(j);
                else falseList.add(j);
            });
        }
        resultMap.put("참",trueList);
        resultMap.put("거짓",falseList);
        log.info("참={}",resultMap.get("참"));
        log.info("거짓={}",resultMap.get("거짓"));
    }
}
