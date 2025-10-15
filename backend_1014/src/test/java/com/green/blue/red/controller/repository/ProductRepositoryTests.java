package com.green.blue.red.repository;

import com.green.blue.red.domain.Product;
import com.green.blue.red.domain.ProductImage;
import com.green.blue.red.dto.NaDto;
import com.green.blue.red.dto.ProductDto;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import java.util.*;

@SpringBootTest
@Slf4j
public class ProductRepositoryTests {

    @Autowired
    ProductRepository repository;

    @Test
    public void testInsert(){
        for (int i = 0; i < 10; i++) {
            Product p= Product.builder()
                    .pname("상품" +i)
                    .price(100*i)
                    .pdesc("상품 설명" +i)
                    .build();
            p.addImageString("image1.jpg");
            p.addImageString("image2.jpg");
            repository.save(p);
            log.info("==========================");
        }
    }

    @Test
    public void testInsert2(){
        int i =0;
        int cnt=0;
        do{
            Product p= Product.builder()
                    .pname("상품" +i)
                    .price(100*i)
                    .pdesc("상품 설명" +i)
                    .build();
            do{
                cnt=0;
                int z =(int)(Math.random()*100);
                if(z>=3 && z<=7){
                    for (int j = 0; j <z ; j++) {
                        p.addImageString(UUID.randomUUID() +"_image.jpg");
                        cnt++;
                    }
                    break;
                }
                log.info("i={},z={},cnt={}",i,z,cnt);
            } while (cnt<100);
            repository.save(p);
            log.info("==========================");
            i++;
        } while (i<100);
    }
    @Commit
    @Transactional
    @Test
    public void testDeleteImage(){
        repository.updateToDelete(11L,true);
    }

    @Test
    public void testRead2(){
        Optional<Product> result=repository.selectOne(11L);
        Product product = result.get();
        log.info("{}",product);
        log.info("{}",product.getImageList());
    }

    @Test
    public void testNaDto(){
        Long [] dtoList={11l,12l,13l,14l,15l};
        List<NaDto> naDtoList=new ArrayList<>();
        Arrays.stream(dtoList).map(i->repository.selectOne(i).get())
                .forEach(i->{
                     NaDto dto = NaDto.builder()
                    .pno(i.getPno())
                    .pname(i.getPname())
                    .price(i.getPrice())
                    .pdesc(i.getPdesc())
                    .fileNames(i.getImageList().stream().map(ProductImage::getFileName).toList())
                    .build();
                naDtoList.add(dto);
        });
        for(NaDto n :naDtoList){
            log.info("dtoList={}",n);
        }
    }
    @Test
    public void testDelete(){
        repository.deleteById(11L);
    }

    @Test
    public void testUpdate(){
        Long pno=12L;
        Product product = repository.selectOne(pno).get();
        product.changeName("12번 상품");
        product.changeDesc("12번 상품 설명입니다.");
        product.changePrice(3500);

        product.clearList();
        product.addImageString(UUID.randomUUID().toString()+"_"+"NewImage1.jpg");
        product.addImageString(UUID.randomUUID().toString()+"_"+"NewImage2.jpg");
        product.addImageString(UUID.randomUUID().toString()+"_"+"NewImage3.jpg");
        repository.save(product);
    }

    @Test
    public void testList(){
        for(int i = 0; i<repository.findAll().stream().toList().size()/10;i++){
            Pageable pageable = PageRequest.of(i,10, Sort.by("pno").descending());
            Page<Object[]> result = repository.selectList(pageable);
            result.getContent().forEach(j->log.info("data={}",j));
        }

    }
}