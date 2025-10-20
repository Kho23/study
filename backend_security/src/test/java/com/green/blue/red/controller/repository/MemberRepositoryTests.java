package com.green.blue.red.controller.repository;

import com.green.blue.red.domain.Member;
import com.green.blue.red.domain.MemberRole;
import com.green.blue.red.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Slf4j
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void testInsertMember(){

        Member member = Member.builder()
                .email("aosrjf123@nate.com")
                .pw(encoder.encode("1111"))
                .nickname("이건호")
                .build();
        member.addRole(MemberRole.ADMIN);
        memberRepository.save(member);
    }
}

