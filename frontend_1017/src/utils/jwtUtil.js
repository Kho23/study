import axios from "axios";
import { getCookie, setCookie } from "./cookieUtil";
import { API_SERVER_HOST } from "../api/TodoApi";

const jwtAxios = axios.create();
const refreshJWT = async (accessToken, refreshToken) => {
  const host = API_SERVER_HOST;
  const header = { headers: { Authorization: `Bearer ${accessToken}` } };
  const res = await axios.get(
    `${host}/api/member?refreshToken=${refreshToken}`,
    header
  );
  console.log("===================");
  console.log(res.data);
};
//before request
const beforeReq = (config) => {
  console.log("before request...");
  const memberInfo = getCookie("member");
  if (!memberInfo) {
    console.log("일치하는 회원 없음");
    return Promise.reject({ response: { data: { error: "Required Login" } } });
  }
  const { accessToken } = memberInfo;
  console.log("accessToken", accessToken);
  config.headers.Authorization = `Bearer ${accessToken}`;
  return config;
};
//fail request
const requestFail = (err) => {
  console.log("request error...");
  return Promise.reject(err);
};
//response 보내기 전
const beforeRes = async (res) => {
  console.log("before return response...");
  const data = res.data;
  if (data && data.error == "ERROR_ACCESS_TOKEN") {
    const memberCookieValue = getCookie("member");
    const result = await refreshJWT(
      memberCookieValue.accessToken,
      memberCookieValue.refreshToken
    );
    console.log("refresh JWT 결과", result);
    memberCookieValue.accessToken = result.accessToken;
    memberCookieValue.refreshToken = result.refreshToken;
    setCookie("member", JSON.stringify(memberCookieValue), 1);
  }
  return res;
};
//fail response
const responseFail = (err) => {
  console.log("response fail error");
  return Promise.reject(err);
};
jwtAxios.interceptors.request.use(beforeReq, requestFail);
jwtAxios.interceptors.response.use(beforeRes, responseFail);

export default jwtAxios;
