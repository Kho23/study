import { useDispatch, useSelector } from "react-redux";
import { Navigate, useNavigate } from "react-router-dom";
import { loginPostAsync } from "../slices/LoginSlice";

const useCustomLogin = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const loginState = useSelector((state) => state.loginSlice);
  const isLogin = loginState.email ? true : false; //로그인여부
  const doLogin = async (loginParam) => {
    const action = await dispatch(loginPostAsync(loginParam));
    return action.payload;
  };
  const doLogout = () => dispatch(logout());
  const moveTopath = (path) => {
    navigate({ pathname: path }, { replace: true });
  };
  const moveToLogin = () => {
    navigate({ pathname: "/member/login" }, { replace: true });
  };
  const moveToLoginReturn = () => {
    return <Navigate replace to={"/member/login"} />;
  };
  
  return {
    loginState,
    isLogin,
    doLogin,
    doLogout,
    moveToLogin,
    moveToLoginReturn,
    moveTopath,
  };
};
export default useCustomLogin
