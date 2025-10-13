package com.green.blue.red.dto;

import com.green.blue.red.domain.AddressImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private Long ano;
    private int age;
    private String dong;
    private String gu;
    private String city;
    private String name;
    private List<AddressImage> imageList = new ArrayList<>();
}
