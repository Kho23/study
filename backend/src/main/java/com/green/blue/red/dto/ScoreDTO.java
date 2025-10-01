package com.green.blue.red.dto;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ScoreDTO {

    private Long sno;

    private int math;
    private int korea;
    private int eng;
    private float total;
    private float avg;
    private String grade;

    public String calcGrade(float avg){
        String r="";
        if(avg>=90) r="A";
        else if (avg>=80) r="B";
        else if (avg>=70) r="C";
        else  r="D";
        return r;
    }

}
