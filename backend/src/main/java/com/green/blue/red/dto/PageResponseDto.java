package com.green.blue.red.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDto<E> {
    private List<E> dtoList; //해당 페이지의 todo DATA
    private List<Integer> pageNumList; //페이지 번호를 표시하기 위한 목록
    private PageRequestDto pageRequestDto; //react 로부터 받은 정보를 다시 response 에 담아 보내기
    private boolean prev, next;
    private int totalCount, prevPage, nextPage, totalPage, current;

    //생성자
    @Builder(builderMethodName = "withAll")
    public PageResponseDto(List<E> dtoList, PageRequestDto pageRequestDto, long totalCount) {
        this.dtoList = dtoList;
        this.pageRequestDto = pageRequestDto;
        this.totalCount = (int) totalCount;
        int end = (int) (Math.ceil(pageRequestDto.getPage() / 10.0)) * 10;
        int start = end - 9;
        int last = (int) (Math.ceil(totalCount / (double) pageRequestDto.getSize()));
        end = end > last ? last : end;
        this.prev = start > 1;
        this.next = totalCount > end * pageRequestDto.getSize();
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
        if (prev) this.prevPage = start - 1;
        this.totalPage = this.pageNumList.size();
        this.current = pageRequestDto.getPage();
    }
}
