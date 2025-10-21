package com.green.blue.red.security.filter;

import com.google.gson.Gson;
import com.green.blue.red.dto.MemberDto;
import com.green.blue.red.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Slf4j
public class JWTCheckFilter extends OncePerRequestFilter {
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String  path= request.getRequestURI();
        log.info("---------check uri:{}-------------" ,path);
        if(request.getMethod().equals("OPTIONS")) return true;
        if(path.startsWith("/api/member/")) return true;
        if(path.startsWith("/api/member/kakao")) return true;
        //이미지 조회 경로는 체크하지 않음
        if(path.startsWith("/api/products/view/")) return true;
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("-------JWTCheckFilter-----------");
        String authHeaderStr = request.getHeader("Authorization"); //권한
        log.info("authHeaderStr={}",authHeaderStr);
        if (authHeaderStr == null || !authHeaderStr.startsWith("Bearer ")) {
            log.info("헤더가 없음");
            filterChain.doFilter(request, response);
            return;
        }
        try {
            //Bearer accssToken..
            log.info("들어옴1");
            String accessToken = authHeaderStr.substring(7);
            log.info("들어옴2");
            Map<String,Object> claims = JWTUtil.validateToken(accessToken);
            log.info("들어옴3");
            log.info("JWT claims: {}" ,claims);
            String email = (String)  claims.get("email");
            String pw = (String)  claims.get("pw");
            String nickname = (String)  claims.get("nickname");
            Boolean social =(Boolean) claims.get("social");
            List<String> roleNames=(List<String>) claims.get("roleNames");
            MemberDto memberDTO= new MemberDto(email,pw,nickname,social,roleNames);
            log.info("---------------------memberdto: {} , authorities=> {}",memberDTO,memberDTO.getAuthorities());
            UsernamePasswordAuthenticationToken authenticationToken=
                    new UsernamePasswordAuthenticationToken(memberDTO,pw,memberDTO.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request,response);
        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            Gson gson = new Gson();
            String errorMsg = e.getMessage().contains("Expired") ? "ERROR_ACCESS_TOKEN_EXPIRED" : "ERROR_ACCESS_TOKEN";
            String msg = gson.toJson(Map.of("error", errorMsg));

            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();
        }

    }
}
