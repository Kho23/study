package com.green.blue.red.service;

import com.green.blue.red.dto.PageRequestDto;
import com.green.blue.red.dto.PageResponseDto;
import com.green.blue.red.dto.ScoreDTO;
import com.green.blue.red.dto.TodoDto;

public interface ScoreService {
    void register(ScoreDTO dto);
    PageResponseDto<ScoreDTO> list(PageRequestDto dto);
    ScoreDTO get(Long id);
    void remove(Long tno);
}
