package com.green.blue.red.controller.repository;

import com.green.blue.red.domain.Todo;
import com.green.blue.red.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.time.LocalDate;
import java.util.*;

@SpringBootTest
@Slf4j
public class TodoRepositoryTests {
    @Autowired
    private TodoRepository t;

    @Test
    public void insertDummy(){
        //더미 200개 추가
        String[] str={"이","박","김","송","조"};
        String[] str2={"건호","종민","길동","수영","자욱"};

        boolean[] boo={true, false,true,false};
        for(int i=0;i<200;i++){
            Todo s = new Todo();
            s.setWriter(str[(int)(Math.random()*5)]+str2[(int)(Math.random()*5)]);
            s.setTitle(i+"번째 데이터입니다.");
            s.setComplete(boo[(int)(Math.random()*4)]);
            s.setDueDate(LocalDate.now());
            t.save(s);
            //LongStream 기능이 위와 비슷하게 작동하니 나중에 연습해보기
        }
    }
    @Test
    public void read(){
        Map<String, List<Todo>> map=new HashMap<>();
        map.put("짝수",new ArrayList<>());
        map.put("홀수",new ArrayList<>());
        t.findAll().forEach(i->{
            if(i.getTno()%2==0) map.get("짝수").add(i);
            else map.get("홀수").add(i);
        });

        System.out.println(map);
    }
    @Test
    public void testModify(){
        List<Todo> result = (t.findAll().stream().toList());
        result.forEach(i->{
            i.changeTitle(i.getTno()+i.getTitle()+"매롱");
            i.changeComplete(!i.isComplete());
            t.save(i);
        });
    }
    @Test
    public void testDelete(){
        t.findAll().forEach(i->{
            if(i.getTitle().length()%2==0) t.deleteById(i.getTno());
        });
    }

    @Test
    public void testPaging(){
        String[] str = {"tno","title","writer"};
        for(int i=0;i<4;i++){
            int size = 10*(i+1);
            int index=0;
            Pageable pageable = PageRequest.of(t.findAll().size()/size,size, Sort.by(str[index++/str.length]).descending());
            Page<Todo> result = t.findAll(pageable);
            result.getContent().stream().forEach(t-> log.info("page={} data={}",size,t));
        }
    }
}
