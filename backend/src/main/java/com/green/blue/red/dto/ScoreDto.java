package com.green.blue.red.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ScoreDto {
    private Long sno;
    private String name;
    private int korea;
    private int math;
    private int eng;
    private float total;
    private float avg;
    private String grade;
}
