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
        //이 함수에 설정된 링크나 메서드는 필터체인이 요청을 가로채지 않고 바로 컨트롤러로 연결되게 해준다.
        String path = request.getRequestURI();//필터를 거치치 않고 통과할지 검사할 URI 링크
        log.info("---------check uri:{}-------------", path);
        if (request.getMethod().equals("OPTIONS")) return true;
        // OPTIONS 메소드 요청은 CORS Preflight 요청이므로 필터를 거치지 않음
        if (path.startsWith("/api/member/")) return true;
        if (path.startsWith("/api/member/kakao")) return true;
        // 위 두개는 회원가입, 로그인 요청 경로이기 때문에 체크하지 않음
        if (path.startsWith("/api/products/view/")) return true;
        //이미지 조회 경로는 체크하지 않음
        //if 문 옆의 링크로 들어오는 요청은 필터를 거치지 않는다.
        return false;
        //그 외는 모두 필터를 거침
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //request : 요청 정보 저장된 객체
        //response : 응답 정보 저장된 객체
        //filterChain : 필터체인에서 다음 체인을 호출하거나 최종리소스 호출 시 사용
        //shouldNotFilter 에 설정되지 않은 컨트롤러 경로에 대한 요청은 이 함수가 가로채고 필터체인 후 다시 기존 컨트롤러로 보내준다
        log.info("-------JWTCheckFilter-----------");
        String authHeaderStr = request.getHeader("Authorization");
        //요청 정보에서 Authorization 이라는 이름의 header 를 authHeaderStr 에 저장
        log.info("authHeaderStr={}", authHeaderStr);
        if (authHeaderStr == null || !authHeaderStr.startsWith("Bearer ")) {
            //헤더가 비어있거나 Bearer 로 시작하지 않으면 토큰이 망가져있는 거라서 예외처리
            log.info("헤더가 없음");
            filterChain.doFilter(request, response);
            //예외처리되었기 때문에 인증 없이 다음 필터로 넘김, 아마 catch 문으로 가겠지?
            return;
        }
        try {
            //Bearer accssToken..
            log.info("필터체인 시작");
            String accessToken = authHeaderStr.substring(7);
            //헤더의 앞 7글자(Bearer )를 자름(JWT 토큰만 따옴)
            log.info("헤더에서 토큰 추출 토큰={}", accessToken);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);
            //JWT 토큰에서 실제 이용자 정보가 담긴 claims 를 꺼내옴
            log.info("토큰에서 클레임 추출 클레임={}", claims);
            String email = (String) claims.get("email");
            String pw = (String) claims.get("pw");
            String nickname = (String) claims.get("nickname");
            Boolean social = (Boolean) claims.get("social");
            List<String> roleNames = (List<String>) claims.get("roleNames");
            MemberDto memberDTO = new MemberDto(email, pw, nickname, social, roleNames);
            //클레임에서 실제 이용자 정보를 각각 꺼내고 이 정보를 바탕으로 새로운 MemberDto 만듦
            log.info("dto에 저장될 이용자정보: {} , 권한: {}", memberDTO, memberDTO.getAuthorities());
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(memberDTO, pw, memberDTO.getAuthorities());
            //UsernamePasswordAuthenticationToken 객체는 누가, 무엇으로, 어떤 권한인지를 차례대로 생성자 메서드로 주입해준다
            //위 코드에서는 클레임에서 받아온 실제 이용자 정보가 담긴 dto 가, 그 비밀번호로, 실제 이용자의 권한이 부여된다
            //일종의 신분증같은 역할을 한다고 보면 되겠다
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //콘텍스트에 authenticationToken 를 저장하면서 이 사용자가 인증된 사용자라고 인식하게 됨
            filterChain.doFilter(request, response); //필터를 모두 거쳐 컨트롤러에 보내준다.
        } catch (Exception e) {
            Gson gson = new Gson();
            String errorMsg = e.getMessage().contains("Expired") ? "ERROR_ACCESS_TOKEN_EXPIRED" : "ERROR_ACCESS_TOKEN";
            //토큰 유효기간 만료 에러는 왼쪽, 아니면 오른쪽 에러 메세지 보내줌
            String msg = gson.toJson(Map.of("error", errorMsg));
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();
        }

    }
}
