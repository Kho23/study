package com.green.blue.red.service;

import com.green.blue.red.domain.ScoreVO;
import com.green.blue.red.domain.Todo;
import com.green.blue.red.dto.PageRequestDto;
import com.green.blue.red.dto.PageResponseDto;
import com.green.blue.red.dto.ScoreDTO;
import com.green.blue.red.dto.TodoDto;
import com.green.blue.red.repository.ScoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService{
    private final ModelMapper mapper;
    private final ScoreRepository repository;


    @Override
    public void register(ScoreDTO dto) {
        repository.save(mapper.map(dto, ScoreVO.class));
    }

    @Override
    public PageResponseDto<ScoreDTO> list(PageRequestDto dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getSize(), Sort.by("sno").descending());
        Page<ScoreVO> result = repository.findAll(pageable);
        List<ScoreDTO> dtoList = result.getContent().stream().map(i -> mapper.map(i, ScoreDTO.class)).toList();
        long totalCount = result.getTotalElements();
        PageResponseDto<ScoreDTO> responseDto = PageResponseDto.<ScoreDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDto(dto)
                .totalCount(totalCount)
                .build();
        return responseDto;
    }

    @Override
    public ScoreDTO get(Long id) {
        return mapper.map(repository.findById(id),ScoreDTO.class);
    }

    @Override
    public void remove(Long tno) {

    }
}
