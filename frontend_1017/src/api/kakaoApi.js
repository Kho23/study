// src/api/kakaoApi.js
import axios from "axios";
import { API_SERVER_HOST } from "./TodoApi";

const rest_api_key = `d4b7ba47956bf60b156cf34b99817bca`;
const redirect_uri = `http://localhost:3000/member/kakao`;

const access_token_url = `https://kauth.kakao.com/oauth/token`;

export const getKakaoLoginLink = () => {
  const scope = encodeURIComponent("profile_nickname account_email");
  return (
    "https://kauth.kakao.com/oauth/authorize" +
    `?client_id=${rest_api_key}` + // ✅ REST API 키 사용
    `&redirect_uri=${encodeURIComponent(redirect_uri)}` + // ✅ 콘솔 등록값과 완전 일치
    `&response_type=code` +
    `&scope=${scope}` +
    `&prompt=consent` // 재동의 강제 (선택)
  );
};

export const getAccessToken = async (authCode) => {
  try {
    const params = new URLSearchParams();
    params.append("grant_type", "authorization_code");
    params.append("client_id", rest_api_key);
    params.append("redirect_uri", redirect_uri);
    params.append("code", authCode);
    // params.append("client_secret", "YOUR_CLIENT_SECRET");

    const { data } = await axios.post(access_token_url, params, {
      headers: {
        "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
      },
    });

    // 카카오 응답 예시: { access_token, token_type, refresh_token, expires_in, ... }
    return data.access_token;
  } catch (err) {
    console.error("kakao token error:", err);
    throw err;
  }
};

export const getMemberWithAccessToken = async (accessToken) => {
  const res = await axios.get(
    `${API_SERVER_HOST}/api/member/kakao?accessToken=${accessToken}`
  );
  return res.data;
};
