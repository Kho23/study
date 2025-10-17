package com.green.blue.red.security.handler;

import com.google.gson.Gson;
import com.green.blue.red.dto.MemberDto;
import com.green.blue.red.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //앞에 on 이 붙으면 성공했을때 이벤트핸들러
        log.info("로그인 성공 신난다 authentication={}",authentication);
        log.info("-----------------------------");
        MemberDto memberDto =(MemberDto) authentication.getPrincipal();
        Map<String,Object> claims =memberDto.getClaims();

        String accessToken = JWTUtil.generateToken(claims,10);
        String refreshToken = JWTUtil.generateToken(claims,60*24);
        claims.put("accessToken",accessToken);
        claims.put("refreshToken",refreshToken);

        Gson gson = new Gson();
        String jsonStr = gson.toJson(claims);
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();
    }
}
