package com.green.blue.red.service;

import com.green.blue.red.domain.Product;
import com.green.blue.red.domain.ProductImage;
import com.green.blue.red.dto.PageRequestDto;
import com.green.blue.red.dto.PageResponseDto;
import com.green.blue.red.dto.ProductDto;
import com.green.blue.red.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.PortInUseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.xml.stream.events.ProcessingInstruction;
import java.security.PrivateKey;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements  ProductService{

    private final ProductRepository repository;

    @Override
    public PageResponseDto<ProductDto> getList(PageRequestDto dto) {
        log.info("product Service 전체 조회 : dto:{}",dto);
        Pageable pageable= PageRequest.of(
                dto.getPage()-1, //페이지 시작 번호가 0부터 시작하므로
                dto.getSize(),
                Sort.by("pno").descending()
        );
        Page<Object[]> result= repository.selectList(pageable);
        List<ProductDto> dtoList=result.get().map(i->{
            Product product =(Product) i[0];
            ProductImage productImage=(ProductImage) i[1];
            ProductDto productDTO = ProductDto.builder()
                    .pno(product.getPno())
                    .pname(product.getPname())
                    .pdesc(product.getPdesc())
                    .price(product.getPrice())
                    .build();
            String  imageStr =productImage.getFileName();
            productDTO.setUploadFileName(List.of(imageStr));
            return productDTO;
        }).toList();
        long totalCount =result.getTotalElements();

        return  PageResponseDto.<ProductDto>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDto(dto)
                .build();
    }
    private Product dtoToEntity(ProductDto dto){
        Product product = Product.builder()
                .pno(dto.getPno())
                .pname(dto.getPname())
                .pdesc(dto.getPdesc())
                .price(dto.getPrice())
                .build();
        List<String> uploadFileNames = dto.getUploadFileName();
        if(uploadFileNames==null) return product;
        uploadFileNames.stream().forEach(i->{
            product.addImageString(i);
        });
        return product;
    }

    private ProductDto entityToDto(Product product){
        ProductDto productDto= ProductDto.builder()
                .pno(product.getPno())
                .pname(product.getPname())
                .price(product.getPrice())
                .pdesc(product.getPdesc())
                .build();
        List<ProductImage> imageList=product.getImageList();
        if(imageList==null||imageList.size()==0) return productDto;
        List<String> fileNameList = imageList.stream()
                .map(ProductImage::getFileName).toList();
        productDto.setUploadFileName(fileNameList);

        log.info("fileNameList={}",fileNameList);
        return productDto;
    }

    @Override
    public Long register(ProductDto dto) {
        log.info("service 등록 dto:{}",dto);
        Product product = dtoToEntity(dto);
        Product result = repository.save(product);
        return result.getPno();
    }

    @Override
    public ProductDto get(Long pno) {
        Optional<Product> result = repository.findById(pno);
        Product product = result.orElseThrow();
        ProductDto productDto = entityToDto(product);
        return productDto;
    }

    @Override
    public void modify(ProductDto dto) {
        log.info("수정 service 실행 dto={}",dto);
        Optional<Product> result = repository.findById(dto.getPno());
        Product product = result.orElseThrow();
        product.changeName(dto.getPname());
        product.changeDesc(dto.getPdesc());
        product.changePrice(dto.getPrice());
        product.clearList();

        //이미지 관련
        List<String> uploadFileNames = dto.getUploadFileName();
        if(uploadFileNames!=null&&uploadFileNames.size()>0) uploadFileNames.forEach(product::addImageString);
        repository.save(product);
    }

    @Override
    public void remove(Long pno) {
        log.info("삭제 service 실행 pno={}",pno);
        repository.updateToDelete(pno,true);
    }
}
