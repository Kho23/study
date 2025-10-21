import axios from "axios";
import { getCookie, setCookie } from "./cookieUtil";
import { API_SERVER_HOST } from "../api/TodoApi";
import { original } from "@reduxjs/toolkit";

const jwtAxios = axios.create();

// JWT 갱신 API 호출 함수 (서버와의 통신)
const refreshJWT = async (accessToken, refreshToken) => {
  const host = API_SERVER_HOST;
  const header = { headers: { Authorization: `Bearer ${accessToken}` } };
  const res = await axios.get(
    `${host}/api/member?refreshToken=${refreshToken}`,
    header
  );
  console.log("===================");
  console.log(res.data);
  return res.data; // 갱신된 토큰 정보를 반환한다고 가정
};

// before request: 요청 전 쿠키에서 토큰을 추출하여 헤더에 추가
const beforeReq = (config) => {
  console.log("before request...");
  const memberInfo = getCookie("member");
  console.log("멤버정보",memberInfo)
  
  // 쿠키에 로그인 정보가 없으면 요청을 거부하고 REQUIRE_LOGIN 오류를 던집니다.
  if (!memberInfo) {
    console.log("일치하는 회원 없음");
    return Promise.reject({ response: { data: { error: "REQUIRE_LOGIN" } } });
  }
  
  // 쿠키에서 accessToken을 추출하여 헤더에 담습니다.
  const { accessToken } = memberInfo;
  console.log("accessToken", accessToken);
  config.headers.Authorization = `Bearer ${accessToken}`;
  return config;
};

// fail request: 요청 실패 처리
const requestFail = (err) => {
  console.log("request error...");
  return Promise.reject(err);
};

// response 보내기 전: 토큰 만료(401) 처리 및 재발급 시도
const beforeRes = async (res) => {
  console.log("before return response...");
  console.log(res);
  const data = res.data;

  // 서버에서 토큰 만료 오류 메시지(ERROR_ACCESS_TOKEN)를 받은 경우 토큰 갱신 시도
  if (data && data.error === "ERROR_ACCESS_TOKEN") { 
    const memberCookieValue = getCookie("member");
    try {
        const result = await refreshJWT(
          memberCookieValue.accessToken,
          memberCookieValue.refreshToken
        );
        console.log("refresh JWT 결과", result);
        
        // 갱신된 토큰 정보로 쿠키 업데이트
        memberCookieValue.accessToken = result.accessToken;
        memberCookieValue.refreshToken = result.refreshToken;
        setCookie("member", JSON.stringify(memberCookieValue), 1);
        
        // 원본 요청에 새 토큰을 담아 재시도
        const originalRequest = res.config;
        originalRequest.headers.Authorization = `Bearer ${result.accessToken}`;
        return await axios(originalRequest);
    } catch (refreshError) {
        // 리프레시 실패 시 로그인 필요 오류를 던집니다.
        return Promise.reject({ response: { data: { error: "REQUIRE_LOGIN" } } });
    }
  }
  return res;
};

// fail response: 서버 응답 실패 처리 (401, 403 처리 핵심)
const responseFail = (err) => {
  console.log("response fail error");
  
  const response = err.response;

  // 🚨 401 Unauthorized 또는 403 Forbidden 응답을 받았을 때 REQUIRE_LOGIN 오류로 변환
  if (response && (response.status === 401 || response.status === 403)) {
    // 이 오류를 던져서 useCustomLogin의 exceptionHandle이 잡도록 합니다.
    return Promise.reject({ response: { data: { error: "REQUIRE_LOGIN" } } });
  }

  return Promise.reject(err);
};

jwtAxios.interceptors.request.use(beforeReq, requestFail);
jwtAxios.interceptors.response.use(beforeRes, responseFail);

export default jwtAxios;