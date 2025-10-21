package com.green.blue.red.controller.optional;

import com.green.blue.red.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
@SpringBootTest
@Slf4j
public class OptionalTests {
    public static void main(String[] args) {
        String name1=Optional.ofNullable("loose").orElse("test");
        log.info("name1={}",name1);

    }
}
