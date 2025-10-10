package com.green.blue.red.service;

import com.green.blue.red.domain.Todo;
import com.green.blue.red.dto.PageRequestDto;
import com.green.blue.red.dto.PageResponseDto;
import com.green.blue.red.dto.TodoDto;
import com.green.blue.red.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor //생성자 자동 주입(auto injection)
public class TodoServiceImpl implements TodoService {
    private final ModelMapper mapper;
    private final TodoRepository repository;


    @Override
    public TodoDto register(TodoDto dto) {
        Todo todo = mapper.map(dto, Todo.class);
        Todo savedTodo = repository.save(todo);
        return mapper.map(savedTodo,TodoDto.class);
    }

    @Override
    public PageResponseDto<TodoDto> list(PageRequestDto pageRequestDto) {
        Pageable pageable = PageRequest.of(pageRequestDto.getPage() - 1, pageRequestDto.getSize(), Sort.by("tno").descending());
        Page<Todo> result = repository.findAll(pageable);
        List<TodoDto> dtoList = result.getContent().stream().map(i -> mapper.map(i, TodoDto.class)).toList();
        long totalCount = result.getTotalElements();
        PageResponseDto<TodoDto> responseDto = PageResponseDto.<TodoDto>withAll()
                .dtoList(dtoList)
                .pageRequestDto(pageRequestDto)
                .totalCount(totalCount)
                .build();
        return responseDto;
    }

    @Override
    public TodoDto get(Long id) {
        List<TodoDto> r = repository.findById(id).stream().map(i -> mapper.map(i, TodoDto.class)).toList();
        return r.get(0);
    }

    @Override
    public void remove(Long tno) {
        repository.deleteById(tno);
    }
}
