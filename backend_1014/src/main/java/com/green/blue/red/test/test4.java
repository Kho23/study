package com.green.blue.red.test;

import com.green.blue.red.dto.AddressDto;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Slf4j
public class test4 {
    private static String generateGu(List<String> dongStr, int[] u, AddressDto d){
        dongStr.add(1,"차량");
        u[1]=100;
        d.setGu("매롱");
        return null;
    };

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("사랑");
        list.add("증오");
        list.add("바보");
        int [] arr = {1,2,3};
        AddressDto dto = new AddressDto();
        generateGu(list,arr,dto);
        list.forEach(i->log.info("{}",i));
        log.info("{}",arr);
        System.out.println(dto);
    }
}
