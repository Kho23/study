package com.green.blue.red.service;

import com.green.blue.red.domain.Member;
import com.green.blue.red.domain.MemberRole;
import com.green.blue.red.dto.MemberDto;
import com.green.blue.red.dto.MemberModifyDto;
import com.green.blue.red.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    public MemberDto getKakaoMember(String accessToken) {
        String email = getEmailFromKakaoAccessToken(accessToken);
        log.info("email:{}",email);
        Optional<Member> result = memberRepository.findById(email);
        if(result.isPresent()) return entityToDto(result.get());
        Member socialMember = makeSocialMember(email);
        memberRepository.save(socialMember);
        return entityToDto(socialMember);
    }

    @Override
    public void modifyMember(MemberModifyDto memberModifyDto) {
        Optional<Member> result = memberRepository.findById(memberModifyDto.getEmail());
        Member member = result.orElseThrow();
        member.changePw(encoder.encode(memberModifyDto.getPw()));
        member.changeSocial(false);
        member.changeNickname(memberModifyDto.getNickname());
        memberRepository.save(member);
    }

    private String makeTempPassword(){
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i<10 ; i++){
            buffer.append((char)(int)(Math.random()*55)+65);
        }
        return buffer.toString();
    }

    private Member makeSocialMember(String email){
        String tempPassword = makeTempPassword();
        log.info("temp Password ={}" , tempPassword);
        String nickname = "소셜회원";
        Member member = Member.builder()
                .email(email)
                .pw(encoder.encode(tempPassword))
                .nickname(nickname)
                .social(true)
                .build();
        member.addRole(MemberRole.USER);
        return member;
    }

    private String getEmailFromKakaoAccessToken(String accessToken){
        String kakaoGetUserURL = "https://kapi.kakao.com/v2/user/me";
        if(accessToken==null) throw new RuntimeException("Access Token is null");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+accessToken);
        headers.add("Content-Type","application/x-www-form-urlencoded");
        headers.add("Content-Type","application/x-www-form-urlencoded");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoGetUserURL).build();
        ResponseEntity<LinkedHashMap> response = restTemplate.exchange(uriBuilder.toString(), HttpMethod.GET, entity, LinkedHashMap.class);
        log.info("res={}",response);
        LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();
        log.info("bodyMap={}",bodyMap);

        LinkedHashMap<String, String> kakaoAccount = bodyMap.get("kakao_account");
        log.info("kakaoAccount={}",kakaoAccount);
        return kakaoAccount.get("email");
    }
}
