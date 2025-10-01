package com.green.blue.red.service;

import com.green.blue.red.dto.PageRequestDto;
import com.green.blue.red.dto.PageResponseDto;
import com.green.blue.red.dto.TodoDto;

import java.util.List;

public interface TodoService {
    Long register(TodoDto dto);
    PageResponseDto list(PageRequestDto dto);
    TodoDto get(Long id);
    void remove(Long tno);
}
