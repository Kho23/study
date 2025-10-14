package com.green.blue.red.test;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Slf4j
public class testMain3 {
    public static void main(String[] args) {
        Map<Integer,AddressDTO> map = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            map.put(i,new AddressDTO((i+1),i*2,"중림"+i+"동","이거노"+i,i+"중구","중림"+i+"동"));
        }
        Map<Integer,AddressDTO> ascMap = new TreeMap<>(map);
        ascMap.computeIfAbsent(120, i -> new AddressDTO(21,28,"중림"+i+"동","이거노"+i,i+"중구","중림"+i+"동"));
        log.info("{}",ascMap);
        //TreeMap 사용 시 key 값 기준으로 오름차순 정렬해준다.
    }
}
