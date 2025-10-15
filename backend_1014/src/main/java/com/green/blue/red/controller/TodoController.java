package com.green.blue.red.controller;

import com.green.blue.red.domain.Todo;
import com.green.blue.red.dto.PageRequestDto;
import com.green.blue.red.dto.PageResponseDto;
import com.green.blue.red.dto.TodoDto;
import com.green.blue.red.repository.TodoRepository;
import com.green.blue.red.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("todo")
public class TodoController {
    private final TodoService service;
    private final TodoRepository repository;

//    Todo toEntity(TodoDto dto){
//        Todo t = new Todo();
//        t.setComplete(dto.isComplete());
//        t.setWriter(dto.getWriter());
//        t.setTitle(dto.getTitle());
//        t.setDueDate(dto.getDueDate());
//        return t;
//    }
//    TodoDto toDto(Todo d){
//        TodoDto t = new TodoDto();
//        t.setTno(d.getTno());
//        t.setTitle(d.getTitle());
//        t.setWriter(d.getWriter());
//        t.setComplete(d.isComplete());
//        t.setDueDate(d.getDueDate());
//        return t;
//    }

    @GetMapping("/list")
    public PageResponseDto<TodoDto> list(PageRequestDto pageRequestDto){ //Params 로 list 뒤에 붙은 URL 을 자동으로 가져와준다
        return service.list(pageRequestDto);
    }

    @GetMapping("/read/{id}")
    public TodoDto get(@PathVariable("id") Long id){
        log.info("리드 컨트롤러 id:{}",id);
        return service.get(id);
    }

//    @PutMapping("/update")
//    public ResponseEntity<String> update(@RequestBody TodoDto d){
//        Todo t = repository.findById(d.getTno()).get();
//        t.setDueDate(d.getDueDate());
//        t.setWriter(d.getWriter());
//        t.setTitle(d.getTitle());
//        t.setComplete(d.isComplete());
//        repository.save(t);
//        return ResponseEntity.ok("성공");
//    }
    @PostMapping("/")
    public Map<String, TodoDto> register(@RequestBody TodoDto dto){
        log.info("todo controller 추가 dt0:{}",dto);
        TodoDto todo = service.register(dto);
        return Map.of("todo",todo);
    }
    @PutMapping("/{tno}")
    public Map<String, String> modify(@RequestBody TodoDto dto){
        log.info("수정 컨트롤러 dto:{}",dto);
        service.modify(dto);
        return Map.of("result","성공");
    }
    @DeleteMapping("/{tno}")
    public Map<String, String> remove(@PathVariable(name="tno") Long tno){
        log.info("삭제 컨트롤러 tno:{}",tno);
        service.remove(tno);
        return Map.of("result","성공");
    }
}
