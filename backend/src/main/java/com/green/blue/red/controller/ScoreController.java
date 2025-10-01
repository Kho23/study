package com.green.blue.red.controller;

import com.green.blue.red.domain.Todo;
import com.green.blue.red.dto.PageRequestDto;
import com.green.blue.red.dto.PageResponseDto;
import com.green.blue.red.dto.ScoreDTO;
import com.green.blue.red.dto.TodoDto;
import com.green.blue.red.repository.ScoreRepository;
import com.green.blue.red.repository.TodoRepository;
import com.green.blue.red.service.ScoreService;
import com.green.blue.red.service.ScoreServiceImpl;
import com.green.blue.red.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("score")
public class ScoreController {
    private final ScoreService service;
    private final ScoreRepository repository;

    @GetMapping("/list")
    public PageResponseDto<ScoreDTO> list(PageRequestDto pageRequestDto){
        return service.list(pageRequestDto);
    }

    @GetMapping("/read/{id}")
    public ScoreDTO get(@PathVariable("id") Long id){
        log.info("리드 컨트롤러 id:{}",id);
        return service.get(id);
    }
}
