package com.green.blue.red.controller;

import com.green.blue.red.dto.ProductDto;
import com.green.blue.red.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final CustomFileUtil fileUtil;

    @PostMapping("/")
    public Map<String, String> register(ProductDto dto){
        log.info("register:{}",dto);
        List<MultipartFile> files=dto.getFiles();
        List<String> uploadFileNames = fileUtil.saveFiles(files);
        dto.setUploadFileName(uploadFileNames);
        log.info("{}",uploadFileNames);
        return Map.of("결과","성공");
    }

    @GetMapping("/view/{filename}")
    public ResponseEntity<Resource> viewFileGet(@PathVariable String fileName){
        log.info("이미지 요청 컨트롤러, fileName={}",fileName);
        return fileUtil.getFile(fileName);
    }
}
