package com.green.blue.red.test;

import com.green.blue.red.dto.AddressDto;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

@Slf4j
public class testMain3 {
    public static void main(String[] args) {
        //predicate 10 개 만들기 임의로
        List<Predicate<AddressDto>> list = Arrays.asList(
                (dto)->dto.getCity().length()>3,
                (dto)->dto.getAge()>10,
                (dto)->dto.getGu().length()>3,
                (dto)->dto.getAno()>3,
                (dto)->dto.getName().equals("이건호"),
                (dto)->dto.getAge()>3,
                (dto)->dto.getCity().length()>10,
                (dto)->dto.getCity().length()<10,
                (dto)->dto.getCity().length()>3,
                (dto)->dto.getCity().length()>3
        );
        // randomData 30개 저장 후 10개의 predicate 하나 꺼내서
        List<AddressDto> dtoList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
           AddressDto a = AddressDto.builder()
                   .age(i*(int)(Math.random()*7))
                   .gu("중구"+(int)(Math.random()*7))
                   .dong("중림동"+(int)(Math.random()*7))
                   .city("서울"+(int)(Math.random()*7))
                   .name("이건호"+(int)(Math.random()*7))
                   .ano((long)(int)(Math.random()*7))
                   .build();
            dtoList.add(a);
        }

        dtoList.stream().forEach(i->{
            for(Predicate<AddressDto> p : list){
                System.out.println(p.test(i));
            }
        });
        List<String> cityList = new ArrayList<>();
        List<String> guList = new ArrayList<>();
        List<String> ageList = new ArrayList<>();
        dtoList.forEach(i->{
            cityList.add(i.getCity());
            guList.add(i.getGu());
            ageList.add(""+i.getAge());
        });
        Map<String, List<String>> map = new HashMap<>();
        map.put("city",cityList);
        map.put("gu",guList);
        map.put("age",ageList);
        log.info("map={}",map);
    }
}
