package com.green.blue.red.test;

import ch.qos.logback.core.joran.conditional.ThenAction;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
@Slf4j
public class MyThread extends Thread {
    private static final Random random = new Random();

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        log.info("실행되는 쓰레드 이름: {}",threadName);
        int delay = 1000+random.nextInt(4000);
        try {
            Thread.sleep(delay);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        log.info("{}가 {}ms 이후 종료됨",threadName,delay);
    }
}
