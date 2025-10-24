package com.green.blue.red.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

public class JWTUtil {

    // (데모용) 정확히 32바이트: 운영에서는 환경변수/시크릿으로 주입 권장
    private static final String RAW_SECRET_32B = "12345678901234567890123456789012"; // 32 bytes

    private static SecretKey getKey() {
        return Keys.hmacShaKeyFor(RAW_SECRET_32B.getBytes(StandardCharsets.UTF_8));
    }

    public static String generateToken(Map<String, Object> valueMap, int min) {
        //JWT 토큰 생성 함수
        SecretKey key = getKey();
        //RAW_SECRET_32B 바탕으로 키를 만듬
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setHeaderParam("type", "JWT") //헤더 파라미터를 JWT 토큰 타입으로 설정
                .setClaims(valueMap)//클레임은 인자로 받는 valueMap(실제 이용자 정보)
                .setIssuedAt(new Date(now))//토큰 생성시간은 현재시간으로(밀리초 기준)
                .setExpiration(new Date(now + min * 60_000L))//토큰 만료기간은
                .signWith(key, SignatureAlgorithm.HS256)  // 시그니처는 key 를 알고리즘 방식으로 설정
                .compact(); //JWT 컴팩트 룰에 따라 위 정보들을 바탕으로 최종 토큰을 만들어 리턴
    }

    public static Map<String, Object> validateToken(String token) {
        //토큰 유효성 검사하는 함수
        try {
            SecretKey key = getKey();
            //토큰 검증방식을 아래에 설정
            return Jwts.parserBuilder()//토큰 검증기를 설계한다
                    .setSigningKey(key)//시그니처를 검증하는데 사용될 토큰 키 설정
                    .build()//검증기 설계 완료
                    .parseClaimsJws(token)//인자로 받은 토큰을 바로 검증 시작
                    .getBody();//검증이 끝나면 최종적으로 클레임 반환
        } catch (MalformedJwtException e) {
            throw new CustomJWTException("MalFormed"); //Base64Url 형식이 아닐때
        } catch (ExpiredJwtException e) {
            throw new CustomJWTException("Expired"); //만료된 토큰일때
        } catch (InvalidClaimException e) {
            throw new CustomJWTException("Invalid");//토큰은 유효하지만 클레임이 유효하지 않을때
        } catch (JwtException e) {
            throw new CustomJWTException("JWTError");//JWT 연관 포괄적 오류
        } catch (Exception e) {
            throw new CustomJWTException("Error");//기타 모든 오류
        }
    }
}
