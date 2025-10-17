package com.green.blue.red.controller.repository;

import com.green.blue.red.domain.Member;
import com.green.blue.red.domain.MemberRole;
import com.green.blue.red.dto.MemberDto;
import com.green.blue.red.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void testInsertMember(){
        for (int i = 0; i < 10; i++) {
            Member member = Member.builder()
                    .email("user"+i+"@naver.com")
                    .pw(encoder.encode("1111"))
                    .nickname("USER"+i)
                    .build();
            member.addRole(MemberRole.USER);
            if(i>=5) member.addRole(MemberRole.MANAGER);
            if(i>=8) member.addRole(MemberRole.ADMIN);
            repository.save(member);
        }
    }
    @Test
    public void testRead(){
        List<String> userEmailList = new ArrayList<>();
        List<String> adminEmailList = new ArrayList<>();
        List<String> managerEmailList = new ArrayList<>();
        for(int i=0;i<10;i++){
            Member member = repository.getWithRole("user"+i+"@naver.com");
            MemberDto dto = MemberDto.builder()
                    .email(member.getEmail())
                    .nickname(member.getNickname())
                    .pw(member.getPw())
                    .social(member.isSocial())
                    .memberRoleList(member.getMemberRoleList())
                    .build();
            System.out.println(dto.getMemberRoleList());
//            if(dto.getMemberRoleList().contains(MemberRole.ADMIN)) adminEmailList.add(dto.getEmail());
//            else if(dto.getMemberRoleList().contains(MemberRole.MANAGER)) managerEmailList.add(dto.getEmail());
//            else userEmailList.add(dto.getEmail());
            for(MemberRole r : dto.getMemberRoleList()){
                switch (r){
                    case ADMIN :  adminEmailList.add(dto.getEmail());
                    break;
                    case MANAGER:  managerEmailList.add(dto.getEmail());
                    break;
                    case USER: userEmailList.add(dto.getEmail());
                    break;
                }
            }
        }
        log.info("userEmailList={}",userEmailList);
        log.info("managerEmailList={}",managerEmailList);
        log.info("adminEmailList={}",adminEmailList);
    }
}
