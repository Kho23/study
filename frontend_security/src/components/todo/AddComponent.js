import React, { useState } from "react";
import { postAdd } from "../../api/TodoApi";
import ResultModal from "../common/ResultModal";
import useCustomMove from "../../hooks/useCustomMove";
const initState = {
  title: "",
  writer: "",
  dueDate: "",
};
const AddComponent = () => {
  const [todo, setTodo] = useState(initState);
  const [result, setResult] = useState(null);
  const { moveToList } = useCustomMove();
  
  // 요청하신 대로 로직을 그대로 유지합니다.
  const handleChange = (e) => {
    const { name, value } = e.target;
    todo[name] = value;
    setTodo({ ...todo });
  };
  
  const handleClickAdd = () => {
    console.log("추가 버튼 눌림");
    postAdd(todo)
      .then((res) => {
        console.log(res);
        setResult(res.todo.tno); //결과 데이터 변경
        setTodo({...initState});
      })
      .catch((e) => console.error(e));
  };
  const closeModal = () => {
    setResult(null);
    moveToList({ page: 1, size: 10 });
  };
  return (
    <div className="border-2 border-sky-200 mt-10 m-2 p-4">
      {/* 모달처리 */}
      {result ? (
        <ResultModal
          title={"결과 추가"}
          content={`새로운 ${result} 가 추가되었어요.`}
          callbackFn={closeModal}
        />
      ) : (
        <></>
      )}
      
      {/* ⭐⭐⭐ 레이아웃 수정 시작 ⭐⭐⭐ */}
      
      {/* 1. 제목 입력 필드 */}
      {/* 이 부분을 다른 항목들과 동일한 flex 구조로 변경했습니다. */}
      <div className="flex justify-center mb-4">
        <div className="w-1/5 p-6 text-right font-bold">제목</div>
        <input
          className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
          name="title"
          type="text"
          value={todo.title}
          onChange={handleChange}
        ></input>
      </div>

      {/* 2. 작성자 입력 필드 */}
      <div className="flex justify-center mb-4">
        <div className="w-1/5 p-6 text-right font-bold">작성자</div>
        <input
          className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
          name="writer"
          type="text"
          value={todo.writer}
          onChange={handleChange}
        ></input>
      </div>
      
      {/* 3. 마감일 입력 필드 */}
      <div className="flex justify-center mb-4">
        <div className="w-1/5 p-6 text-right font-bold">마감일</div>
        <input
          className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
          name="dueDate"
          type="date"
          value={todo.dueDate}
          onChange={handleChange}
        ></input>
      </div>
      
      {/* 4. 추가 버튼 (원래 구조 유지) */}
      <div className="flex justify-end">
        <div className="relative mb-4 flex p-4 flex-wrap items-stretch">
          <button
            type="button"
            onClick={handleClickAdd}
            className="rounded p-4 w-36 bg-blue-500 text-xl text-white"
          >
            추가
          </button>
        </div>
      </div>
      
      {/* ⭐⭐⭐ 레이아웃 수정 끝 ⭐⭐⭐ */}
    </div>
  );
};

export default AddComponent;