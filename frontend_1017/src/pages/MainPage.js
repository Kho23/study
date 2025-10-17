import React from "react";
import { Link } from "react-router-dom";
import BasicLayout from "../layout/BasicLayout";

const MainPage = () => {
  return (
    <BasicLayout>
      <div>
        <div className="flex">
          <Link to={"/about"}>About</Link>
        </div>
        <div className="text-3xl">
          <div>MainPage</div>
        </div>
      </div>
    </BasicLayout>
  );
};

export default MainPage;
