import React, { useCallback } from "react";
import ListComponent from "../../components/Products/ListComponent";
import { Outlet, useNavigate } from "react-router-dom";
import BasicLayout from "../../layout/BasicLayout";

const IndexPage = () => {
  const navigate = useNavigate();
  const handleClickList = useCallback(() => {
    navigate({ pathname: "list" });
  });
  const handleClickAdd = useCallback(() => {
    navigate({ pathname: "add" });
  });
  return (
    <BasicLayout>
      <div className="text-black font-extrabold mt-10">product menu</div>
      <div className="w-full flex m-2 p-2">
        <div
          className="text-xl m-1 p-2 w-20 font-extrabold text-center"
          onClick={handleClickList}
        >
          목록
        </div>
        <div
          className="text-xl m-1 p-2 w-20 font-extrabold text-center"
          onClick={handleClickAdd}
        >
          추가
        </div>
        <div className="flex flex-wrap w-full">
          <Outlet />
        </div>
      </div>
    </BasicLayout>
  );
};

export default IndexPage;
