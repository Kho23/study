package com.green.blue.red.security;

import com.green.blue.red.domain.Member;
import com.green.blue.red.dto.MemberDto;
import com.green.blue.red.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("---------------user 이름으로 가져옴-------------",username);
        Member member = memberRepository.getWithRole(username);
        if(member==null) {
            throw
                new UsernameNotFoundException("Not Found");
        }
        MemberDto memberDto = new MemberDto(
                member.getEmail(),
                member.getPw(),
                member.getNickname(),
                member.isSocial(),
                member.getMemberRoleList().stream().map(i->i.name()).toList()
                );
        log.info("loadUserByUsername memberDto={}",memberDto);
        return memberDto;
    };
}
