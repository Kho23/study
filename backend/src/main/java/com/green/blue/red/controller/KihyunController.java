//package com.green.blue.red.controller;
//
//import com.green.blue.red.dto.ScoreDto;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@Slf4j
//public class KihyunController {
//    Long sno=1L;
//    List<ScoreDto> dtoList = new ArrayList<>();
//    public void ss(){  for(int i=0;i<200;i++){
//        ScoreDto dto = new ScoreDto();
//        dto.setSno(sno++);
//        dto.setEng((int)(Math.random()*100));
//        dto.setKorea((int)(Math.random()*100));
//        dto.setMath((int)(Math.random()*100));
//        dto.setTotal(dto.getEng()+dto.getMath()+dto.getKorea());
//        dto.setAvg(dto.getTotal()/3);
//        if(dto.getAvg()>=90) dto.setGrade("A");
//        else if (dto.getAvg()>=80) dto.setGrade("B");
//        else if (dto.getAvg()>=70) dto.setGrade("C");
//        else dto.setGrade("D");
//        dtoList.add(dto);
//    };}
//
//
//
//    @GetMapping("/todo/read/{no}")
//    public ScoreDto read (@PathVariable("no") Long no){
//        System.out.println("emf");
//        ScoreDto dte = null;
//        for(ScoreDto d : dtoList){
//            if(d.getSno()==no) dte = d;
//        }
//        return dte;
//    }
//    @GetMapping("todo/list")
//    public List<ScoreDto> list(){
//        ss();
//        System.out.println(dtoList);
//        return dtoList;
//    }
//
//}
