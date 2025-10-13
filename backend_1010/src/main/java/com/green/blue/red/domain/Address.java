package com.green.blue.red.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "tbl_address")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ano;
    private int age;
    private String dong;
    private String gu;
    private String city;
    private String name;

    @ElementCollection
    @Builder.Default
    private List<AddressImage> imageList = new ArrayList<>();

}
