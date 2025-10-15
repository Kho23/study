package com.green.blue.red.test;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class testMain {
    public static void main(String[] args) {
      log.info("사랑");
        Map<String,Long> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put("a" + i, (long)(i+1)*5);
        }
        map.computeIfAbsent("a100", i -> Long.parseLong(i.substring(1)));
        log.info("{}",map);
//        Thread thread1 = new MyThread();
//        thread1.setName("Thread #1");
//
//        Thread thread2 = new MyThread();
//        thread2.setName("Thread #2");
//
//        Runnable runnable1 = new MyRunnable();
//        Runnable runnable2 = new MyRunnable();
//
//        Thread thread3 = new Thread(runnable1);
//        thread3.setName("Thread #3");
//
//        Thread thread4 = new Thread(runnable2);
//        thread4.setName("Thread #5");
//
//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();

        //리스트에 Thread 저장하고 start 로 실행하기
//        List<Thread> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Runnable runnable = new MyRunnable();
//            Thread thread = new Thread(runnable);
//            thread.setName("Thread #"+(i+1));
//            list.add(thread);
//        }
//        list.forEach(i->{
//            i.start();
//        });

        Map<String, List<String>> map2=new HashMap<>()  ;
        List<String> list= Arrays.asList("a","b","c","d","e");
        int[] arr={1,10,3,4,8};
        for(int i=0;i<list.size();i++){
            List<Integer> numList = Arrays.asList(arr[i],arr[i]*2,arr[i]*3,arr[i]*4,arr[i]*5);
            List<String> stringList = numList.stream().map(j->j+"").toList();
            map2.put(list.get(i),stringList);
        }
        System.out.println(map2);
    }
}
