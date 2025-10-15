package com.green.blue.red.service;


import com.green.blue.red.domain.Product;
import com.green.blue.red.dto.PageRequestDto;
import com.green.blue.red.dto.PageResponseDto;
import com.green.blue.red.dto.ProductDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;

@Transactional
public interface ProductService {
    public PageResponseDto<ProductDto> getList(PageRequestDto dto);
    Long register(ProductDto dto);
    ProductDto get(Long pno);
    // 10/14 이후
    void modify(ProductDto dto);
    void remove(Long pno);
}
