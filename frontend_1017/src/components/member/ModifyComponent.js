import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import useCustomLogin from "../../hooks/useCustomLogin";
import ResultModal from "../common/ResultModal";
import {modifyMember} from "../../api/MemberApi"

const initState = { email: "", pw: "", nickname: "" };

const ModifyComponent = () => {
  const [member, setMember] = useState(initState);
  const [result, setResult] = useState();
  const loginInfo = useSelector((state) => state.loginSlice);
  const { moveToLogin } = useCustomLogin();
  useEffect(() => {
    setMember({ ...loginInfo, pw: "ABCD" });
  }, [loginInfo]);

  const handleChange = (e) => {
    const {name, value} = e.target;
    member[name] = value;
    setMember({ ...member });
  };

  const handleClickModify = () => {
    modifyMember(member).then((r) => setResult("Modified"));
  };
  const closeModal = () => {
    setResult(null);
    moveToLogin();
  };

  return (
    <div className="mt-6">
      {result ? (
        <ResultModal
          title={"회원정보"}
          content={"정보수정완료"}
          callbackFn={closeModal}
        />
      ) : (
        <></>
      )}
      <div className="flex justify-center">
        {console.log(member,loginInfo)}
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">이메일</div>
          <input
            type="email"
            name="email"
            value={member.email}
            readOnly
            className="w-4/5 p-6 rounded-r border border-solid border-x-neutral-300
            shadow-md"
          ></input>
        </div>
      </div>
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">암호</div>
          <input
            type="password"
            name="pw"
            value={member.pw}
            onChange={handleChange}
            className="w-4/5 p-6 rounded-r border border-solid border-x-neutral-300
            shadow-md"
          ></input>
        </div>
      </div>
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">별명</div>
          <input
            type="text"
            name="nickname"
            value={member.nickname}
            onChange={handleChange}
            className="w-4/5 p-6 rounded-r border border-solid border-x-neutral-300
            shadow-md"
          ></input>
        </div>
      </div>
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap justify-end">
          <button
            type="button"
            className="rounded p-4 m-2 text-xl w-32 text-white bg-blue-500"
            onClick={handleClickModify}
          >
            수정
          </button>
        </div>
      </div>
    </div>
  );
};

export default ModifyComponent;
