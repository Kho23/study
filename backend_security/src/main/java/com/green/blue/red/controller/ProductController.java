package com.green.blue.red.controller;

import com.green.blue.red.domain.Product;
import com.green.blue.red.dto.PageRequestDto;
import com.green.blue.red.dto.PageResponseDto;
import com.green.blue.red.dto.ProductDto;
import com.green.blue.red.service.ProductService;
import com.green.blue.red.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final ProductService service;

    @PostMapping("/")
    public Map<String, Long> register(ProductDto dto){
        log.info("register:{}",dto);
        List<MultipartFile> files=dto.getFiles();
        List<String> uploadFileNames = fileUtil.saveFiles(files);
        dto.setUploadFileName(uploadFileNames);
        log.info("{}",uploadFileNames);
        Long pno=service.register(dto);
        return Map.of("결과",pno);
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGet(@PathVariable String fileName){
        log.info("이미지 요청 컨트롤러, fileName={}",fileName);
        return fileUtil.getFile(fileName);
    }

    //페이지당 전체 목록 조회
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    //Page당 전체 목록 조회
    @GetMapping("/list")
    public PageResponseDto<ProductDto> list(PageRequestDto dto){
        log.info("list controller ............. pageRequest:{},start={}",dto);
        return  service.getList(dto);
    }

    @PutMapping("/{pno}")
    public Map<String, String> modify(@PathVariable("pno") Long pno, ProductDto dto){
        dto.setPno(pno);
        ProductDto oldProductDto=service.get(pno);
        //기존 파일(DB 파일을 수정과정에서 삭제되었을수도 있음)
        List<String> oldFileNames = oldProductDto.getUploadFileName();
        //기존 파일 이름
        List<MultipartFile> files = dto.getFiles();
        //새로 업로드되면서 만들어진 파일 이름
        List<String> currentUploadFileNames = fileUtil.saveFiles(files);
        //화면에서 변화없이 계속 유지된 파일들
        List<String> uploadedFileNames = dto.getUploadFileName();
        //유지되는 파일들 + 새로 업로드된 파일 이름들이 저장해야하는 파일 목록이 됨
        if(currentUploadFileNames!=null&&currentUploadFileNames.size()>0) uploadedFileNames.addAll(currentUploadFileNames);
        service.modify(dto);
        if(oldFileNames!=null&&oldFileNames.size()>0) {
            //지워야하는 파일 목록 찾기
            //예전 파일들 중에서 지워야 하는 파일이름들
            List<String> removeFiles = oldFileNames
                    .stream().filter(i->uploadedFileNames.indexOf(i)==-1).toList(); //검색되지 않으면 -1 반환
            //실제 파일 삭제
            fileUtil.deleteFiles(removeFiles);
        }
        return Map.of("Result","성공!");
    }

    @DeleteMapping("/{pno}")
    public Map<String, String> remove(@PathVariable("pno") Long pno){
        //삭제해야 되는 파일 알아내기
        List<String> oldFileName = service.get(pno).getUploadFileName();
        service.remove(pno);
        fileUtil.deleteFiles(oldFileName);
        return Map.of("Result","성공");
    }
    @GetMapping("/{pno}")
    public ResponseEntity<ProductDto> getOne(@PathVariable("pno") Long pno){
        ProductDto dto = service.get(pno);
        log.info("조회 컨트롤러 실행 dto={}",dto);
        return ResponseEntity.ok(dto);
    }

}
