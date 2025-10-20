import React, { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { getAccessToken, getMemberWithAccessToken } from "../../api/kakaoApi";
import { useDispatch } from "react-redux";
import { login } from "../../slices/loginSlice";
import useCustomLogin from "../../hooks/useCustomLogin";

const KakaoRedirectPage = () => {
  const [searchParams] = useSearchParams();
  const authCode = searchParams.get("code");
  const {moveTopath} = useCustomLogin();
  console.log(authCode);
  const dispatch = useDispatch()

  useEffect(() => {
    if(!authCode) return;
    (async()=>{
      try {
        const accessToken = await getAccessToken(authCode);
        console.log("kakao access_token", accessToken)
        getMemberWithAccessToken(accessToken).then((memberInfo)=>{
          console.log("memberInfo",memberInfo)
          dispatch(login(memberInfo))
          if(memberInfo&&memberInfo.social){
            moveTopath("/")
          }else{
            moveTopath("/member/modify")
          }
        })
      } catch (e) {
        alert("카카오 토큰 교환 실패")
      }
    })();
  }, [authCode])
  
  return (
    <div>
      <div>Kakao Login Redirect</div>
    </div>
  );
};

export default KakaoRedirectPage;
