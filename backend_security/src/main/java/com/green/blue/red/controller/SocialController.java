package com.green.blue.red.controller;

import com.green.blue.red.domain.Member;
import com.green.blue.red.dto.MemberDto;
import com.green.blue.red.dto.MemberModifyDto;
import com.green.blue.red.service.MemberService;
import com.green.blue.red.util.JWTUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SocialController {
    private final MemberService service;

    @GetMapping("api/member/kakao")
    public Map<String, Object> getMemberFromKakao(String accessToken){
        log.info("access token ={}",accessToken);
        MemberDto data =service.getKakaoMember(accessToken);
        Map<String, Object> claims = service.getKakaoMember(accessToken).getClaims();
        log.info("claims={}",claims);
        String jwtAccessToken = JWTUtil.generateToken(claims,10);
        String jwtRefreshToken = JWTUtil.generateToken(claims,60*24);
        claims.put("accessToken",jwtAccessToken);
        claims.put("refreshToken",jwtRefreshToken);
        return claims;
    }

    @PutMapping("api/member/modify")
    public Map<String, String> modify(@RequestBody MemberModifyDto dto){
        log.info("수정 들어옴 {}",dto);
        service.modifyMember(dto);
        return Map.of("result","modify");
    }
}
