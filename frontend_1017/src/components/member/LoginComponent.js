import React, { useState } from "react";

import { useDispatch } from "react-redux";
import { loginPostAsync } from "../../slices/loginSlice";
import { useNavigate } from "react-router-dom";

const initState = {
  email: "",
  pw: "",
};
const LoginComponent = () => {
  const navigate = useNavigate();
  const [loginParam, setLoginParam] = useState({ ...initState });
  const dispatch = useDispatch();
  const handleChange = (e) => {
    const { name, value } = e.target;
    loginParam[name] = value;
    setLoginParam({ ...loginParam });
  };
  const handleClickLogin = (e) => {
    //dispatch(login(loginParam)) 동기화된 호출
    dispatch(loginPostAsync(loginParam))//비동기호출
    .unwrap()
    .then((data)=>{
      console.log('after unwrap..')
      console.log(data)
      if(data.error) alert('이메일과 암호를 확인하세요')
      else alert('로그인 성공') 
    //뒤로가기 했을때 로그인 화면을 볼수 없게
      navigate({pathname:'/'},{replace:true})
    }) 
    
  };
  return (
    <div className="border-2 border-sky-200 mt-10 m-2 p-4">
      <div className="flex justify-center">
        <div className="text-4xl m-4 p-4 font-extrabold text-blue-500">
          로그인 component
        </div>
      </div>
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-2/5 p-6 text-right font-bold">이메일</div>
          <input
            type="text"
            name="email"
            value={loginParam.email}
            onChange={handleChange}
            className="w-3/5 p-6 rounded-r border border-solid bolneu500 shadow-md"
          ></input>
        </div>
      </div>
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-2/5 p-6 text-right font-bold">패스워드</div>
          <input
            type="text"
            name="pw"
            value={loginParam.pw}
            onChange={handleChange}
            className="w-3/5 p-6 rounded-r border border-solid bolneu500 shadow-md"
          ></input>
        </div>
      </div>
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full justify-center">
          <div className="w-2/5 p-6 flex justify-center font-bold">
            <button
              className="rounded p-4 w-36 bg-blue-500 text-xl text-white"
              onClick={handleClickLogin}
            >
              LOGIN
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginComponent;
