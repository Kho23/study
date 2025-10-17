import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { loginPost } from "../api/MemberApi";
const initState = {
  email: "",
};

const loginSlice = createSlice({
  name: "loginSlice",
  initialState: initState,
  reducers: {
    login: (state, action) => {
      console.log("login...");
      const data = action.payload;
      return { email: data.email };
    },
    logout: (state, action) => {
      console.log("logout...");
      return { ...initState };
    },
  },
  extraReducers:builder=>{
    builder.addCase(loginPostAsync.fulfilled, (state, action)=>{
        console.log('fulfilled')
    }).addCase(loginPostAsync.pending, (state, action)=>{
        console.log('pending')
    }).addCase(loginPostAsync.rejected, (state,action)=>{
        console.log('rejected')
    })
  }
});
//두번째 인자인 람다식을 createAsyncThunk가 비동기로 통신하도록 해줌
export const loginPostAsync = createAsyncThunk("loginPostAsync", (param) =>
  loginPost(param)
);
export const { login, logout } = loginSlice.actions;
export default loginSlice.reducer;
