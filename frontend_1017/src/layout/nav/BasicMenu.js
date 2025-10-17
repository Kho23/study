import React from "react";
import { Link } from "react-router-dom";

const BasicMenu = () => {
  return (
    <nav id="navbar" className="glex bg-blue-300">
      <div className="w-4/5 bg-gray-500">
        <div className="flex p-4 text-white font-bold">
          <ul className="flex p-4 text-white font-bold">
            <li className="pr-6 text-2xl">
              <Link to={"/"}>Main</Link>
            </li>
            <li className="pr-6 text-2xl">
              <Link to={"/about"}>about</Link>
            </li>
            <li className="pr-6 text-2xl">
              <Link to={"/todo"}>todo</Link>
            </li>
            <li className="pr-6 text-2xl">
              <Link to={"/products"}>products</Link>
            </li>
          </ul>
        </div>
        <div className="w-1/5 flex justify-end bg-orange-300 p-4 font-medium">
          <div className="text-white text-sm m-1 rounded">
            <Link to={"/member/login"}>Login</Link>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default BasicMenu;
