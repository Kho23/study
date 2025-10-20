import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import useCustomLogin from "../../hooks/useCustomLogin"
const BasicMenu = () => {
  const {loginState, isLogin} = useCustomLogin();
  console.log("베이직 메뉴 로그인상태:",loginState)
  console.log(isLogin)
  return (
    <nav id="navbar" className="flex bg-blue-300">
      <div className="w-4/5 bg-gray-500">
        <ul className="flex p-4 text-white font-bold">
          <li className="pr-6 text-2xl">
            <Link to={"/"}>Main</Link>
          </li>
          <li className="pr-6 text-2xl">
            <Link to={"/about"}>About</Link>
          </li>
          {loginState.email ? (
            <>
              <li className="pr-6 text-2xl">
                <Link to={"/todo"}>Todo</Link>
              </li>
              <li className="pr-6 text-2xl">
                <Link to={"/product"}>제품</Link>
              </li>
            </>
          ) : (
            <></>
          )}
        </ul>
      </div>
      <div className="w-1/5 flex justify-end bg-orange-300 p-4 font-medium">
        <div className="text-white text-sm m-1 rounded">Login</div>
      </div>
      <div className="w-1/5 flex justify-end bg-orange-300 p-4 font-medium">
        {!isLogin ? (
          <div className="text-white text-sm m-1 rounded">
            <Link to={"/member/login"}>로그인</Link>
          </div>
        ) : (
          <div className="text-white text-sm m-1 rounded">
            <Link to={"/member/logout"}>로그아웃</Link>
          </div>
        )}
      </div>
    </nav>
  );
};

export default BasicMenu;
