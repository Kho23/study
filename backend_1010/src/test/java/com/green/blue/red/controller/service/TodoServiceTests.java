package com.green.blue.red.controller.service;

import com.green.blue.red.dto.PageRequestDto;
import com.green.blue.red.dto.PageResponseDto;
import com.green.blue.red.dto.TodoDto;
import com.green.blue.red.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
public class TodoServiceTests {
    @Autowired
    private TodoService service;
//    @Test
//    public void testRegister(){
//        for(int i = 0;i<100;i++){
//            TodoDto dto = TodoDto.builder()
//                    .title((i+1)+" 번째 서비스 테스트")
//                    .writer((i+1)+"이건호 테스트")
//                    .dueDate(LocalDate.of(2025,10,01))
//                    .build();
//            Long tno = service.register(dto);
//            log.info("tno={}",tno);
//        }
//    }
//    @Test
//    public void get(){
//       List<Long> list1 = service.list().stream().filter(i->i.getTitle().length()%2==0).map(TodoDto::getTno).toList();
//       List<Long> list2 = service.list().stream().filter(i->i.getTitle().length()%2==1).map(TodoDto::getTno).toList();
//       Map<String, List<Long>> map = new HashMap<>();
//       map.put("짝수",list1);
//       map.put("홀수",list2);
//        System.out.println(map);
//    }

    @Test
    public void testList(){
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .page(2)
                .size(10)
                .build();
        PageResponseDto<TodoDto> response = service.list(pageRequestDto);
        log.info("res:{}",response);
    }
}
