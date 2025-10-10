package com.green.blue.red.service;

import com.green.blue.red.domain.Todo;
import com.green.blue.red.dto.PageRequestDto;
import com.green.blue.red.dto.PageResponseDto;
import com.green.blue.red.dto.TodoDto;

import java.util.List;

public interface TodoService {
    TodoDto register(TodoDto dto);
    PageResponseDto<TodoDto> list(PageRequestDto dto);
    TodoDto get(Long id);
    void remove(Long tno);
}
