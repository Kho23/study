package com.green.blue.red.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name="tbl_product")
@Entity
@Getter
@ToString(exclude="imageList")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno; //primary key
    private String pname;
    private int price;
    private String pdesc;
    private boolean delFlag;

    @ElementCollection
    @Builder.Default
    private List<ProductImage> imageList = new ArrayList<>();
    //productImage 가 pno 를 primary key 로 갖는 foreign key

    public void changePrice(int price){
        this.price=price;
    }
    public void changeDesc(String desc){
        this.pdesc=desc;
    }
    public void changeName(String name){
        this.pname=name;
    }
    public void addImage(ProductImage image){
        image.setOrd(this.imageList.size());
        imageList.add(image);
    }
    public void addImageString(String fileName){
        ProductImage productImage = ProductImage.builder()
                .fileName(fileName)
                .build();
        addImage(productImage);
    }
    public void clearList(){
        this.imageList.clear();
    }
}
