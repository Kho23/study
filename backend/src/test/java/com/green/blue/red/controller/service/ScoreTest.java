package com.green.blue.red.controller.service;

import com.green.blue.red.dto.ScoreDTO;
import com.green.blue.red.dto.TodoDto;
import com.green.blue.red.service.ScoreService;
import com.green.blue.red.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
@Slf4j
@SpringBootTest
public class ScoreTest {
    @Autowired
    private ScoreService service;

    @Test
    public void testRegister() {
        for (int i = 0; i < 100; i++) {
            ScoreDTO dto = ScoreDTO.builder()
                    .eng((int) (Math.random() * 100))
                    .math((int) (Math.random() * 100))
                    .korea((int) (Math.random() * 100))
                    .build();
            dto.setTotal(dto.getEng() + dto.getKorea() + dto.getMath());
            dto.setAvg(dto.getTotal() / 3);
            dto.setGrade(dto.calcGrade(dto.getAvg()));
            service.register(dto);
        }
    }
}
