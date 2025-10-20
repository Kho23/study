import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { loginPost } from "../api/MemberApi";
import { getCookie, removeCookie, setCookie } from "../utils/cookieUtil";
const initState = {
  email: "",
};
const loadMemberCookie = () => {
  //쿠키에서 로그인정보 로딩
  const memberInfo = getCookie("member"); //로그인시 저장된 정보를 가져온다
  console.log(memberInfo)
  if (memberInfo && memberInfo.nickname)
    memberInfo.nickname = decodeURIComponent(memberInfo.nickname);
  return memberInfo
};

const loginSlice = createSlice({
  name: "loginSlice",
  initialState: loadMemberCookie() || initState, //쿠키 없으면 초기값 사용
  reducers: {
    login: (state, action) => {
      console.log("login...");
      const data = action.payload;
      return { email: data.email };
    },
    logout: (state, action) => {
      console.log("logout...");
      removeCookie("member")
      return { ...initState };
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(loginPostAsync.fulfilled, (state, action) => {
        console.log("fulfilled");
        const payload = action.payload;
        //정상 로그인 시에만 저장
        if (!payload.error) setCookie("member", JSON.stringify(payload), 1);
        return action.payload;
      })
      .addCase(loginPostAsync.pending, (state, action) => {
        console.log("pending");
      })
      .addCase(loginPostAsync.rejected, (state, action) => {
        console.log("rejected");
      });
  },
});
//두번째 인자인 람다식을 createAsyncThunk가 비동기로 통신하도록 해줌
export const loginPostAsync = createAsyncThunk("loginPostAsync", (param) =>
  loginPost(param)
);
export const { login, logout } = loginSlice.actions;
export default loginSlice.reducer;
