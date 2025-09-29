package com.green.blue.red.controller;

import com.green.blue.red.domain.Todo;
import com.green.blue.red.dto.ScoreDto;
import com.green.blue.red.dto.TodoDto;
import com.green.blue.red.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@CrossOrigin(
        origins = "http://localhost:8080",
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PATCH,RequestMethod.OPTIONS},
        allowedHeaders = "*")
public class TodoController {
    @Autowired
    public TodoRepository repository;

    Todo toEntity(TodoDto dto){
        Todo t = new Todo();
        t.setComplete(dto.isComplete());
        t.setWriter(dto.getWriter());
        t.setTitle(dto.getTitle());
        t.setDueDate(dto.getDueDate());
        return t;
    }
    TodoDto toDto(Todo d){
        TodoDto t = new TodoDto();
        t.setTno(d.getTno());
        t.setTitle(d.getTitle());
        t.setWriter(d.getWriter());
        t.setComplete(d.isComplete());
        t.setDueDate(d.getDueDate());
        return t;
    }

    @GetMapping("/todo/list")
    public ResponseEntity<List<TodoDto>> list(){
        return ResponseEntity.ok(repository.findAll().stream().map(this::toDto).toList());
    }

    @GetMapping("/todo/read/{id}")
    public ResponseEntity<Optional<Todo>> read(@PathVariable("id") Long id){
        return ResponseEntity.ok(repository.findById(id));
    }

    @PutMapping("/todo/update")
    public ResponseEntity<String> update(@RequestBody TodoDto d){
        Todo t = repository.findById(d.getTno()).get();
        t.setDueDate(d.getDueDate());
        t.setWriter(d.getWriter());
        t.setTitle(d.getTitle());
        t.setComplete(d.isComplete());
        repository.save(t);
        return ResponseEntity.ok("성공");
    }
}
