package com.green.blue.red.controller;

import com.green.blue.red.dto.MemberDto;
import com.green.blue.red.dto.MemberModifyDto;
import com.green.blue.red.service.MemberService;
import com.green.blue.red.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SocialController {
    private final MemberService service;

    @GetMapping("api/member/kakao")
    public Map<String, Object> getMemberFromKakao(String accessToken){
        log.info("access token ={}",accessToken);

        // 1. DTO를 가져옵니다.
        MemberDto memberDto = service.getKakaoMember(accessToken);

        // 2. JWT 생성을 위한 claims를 DTO에서 가져옵니다.
        Map<String, Object> claims = memberDto.getClaims();
        log.info("JWT Claims={}", claims);

        // 3. JWT 생성
        String jwtAccessToken = JWTUtil.generateToken(claims, 10);
        String jwtRefreshToken = JWTUtil.generateToken(claims, 60*24);
        log.info("JWT jwtAccessToken={}", jwtAccessToken);
        log.info("JWT jwtRefreshToken={}", jwtRefreshToken);
        // 🚨 4. 최종 반환 객체 생성: DTO의 필드와 JWT를 담는 새로운 맵
        Map<String, Object> result = new HashMap<>();

        // DTO 필드를 명시적으로 복사하여 프론트엔드의 쿠키 로딩 로직을 충족시킵니다.
        result.put("email", memberDto.getEmail());
        result.put("pw", memberDto.getPw());
        result.put("nickname", memberDto.getNickname());
        result.put("social", memberDto.isSocial());
        result.put("roleNames", memberDto.getRoleNames());

        // JWT 토큰 추가
        result.put("accessToken", jwtAccessToken);
        result.put("refreshToken", jwtRefreshToken);

        return result;
    }

    @PutMapping("api/member/modify")
    public Map<String, String> modify(@RequestBody MemberModifyDto dto){
        log.info("수정 들어옴 {}",dto);
        service.modifyMember(dto);
        return Map.of("result","modify");
    }
}
