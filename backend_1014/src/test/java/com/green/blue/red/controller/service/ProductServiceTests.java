package com.green.blue.red.service;

import com.green.blue.red.domain.Product;
import com.green.blue.red.dto.PageRequestDto;
import com.green.blue.red.dto.PageResponseDto;
import com.green.blue.red.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.ErrorPageRegistrarBeanPostProcessor;

@SpringBootTest
@Slf4j
public class ProductServiceTests {
    @Autowired
    private  ProductService productService;

    @Test
    public void testList(){
        PageRequestDto dto= PageRequestDto.builder().build();

        PageResponseDto<ProductDto> result =productService.getList(dto);

        result.getDtoList().forEach(i->log.info("dto={}",i));
    }

    @Test
    public void testRegister(){
        ProductDto dto=ProductDto.builder()

                .build();
    }

    @Test
    public void testRead(){
        ProductDto productDto = productService.get(118L);
        log.info("{}",productDto);
        log.info("{}",productDto.getUploadFileName());
    }
}
