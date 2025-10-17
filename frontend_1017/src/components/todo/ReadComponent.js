import React, { useEffect, useState } from "react";
import { getOne } from "../../api/TodoApi";
import  makeDiv  from "../../utils/autocss";
import useCustomMove from "../../hooks/useCustomMove";
const initState = {
  tno: 0,
  title: "",
  writer: "",
  dueDate: null,
  complete: false,
};
const ReadComponent = ({ tno }) => {
  const [todo, setTodo] = useState(initState);
  const {moveToList, moveToModify, page, size} = useCustomMove();
  useEffect(() => {
    getOne(tno).then((d) => {
      console.log(d);
      setTodo(d);
    });
  }, [tno]);
  

  return <div className="border-2 border-sky-200 mt-10 m-2 p-4">
    {makeDiv('번호',todo.tno)}
    {makeDiv('작성자',todo.writer)}
    {makeDiv('제목',todo.title)}
    {makeDiv('완료여부',todo.complete?"완료":"미완료")}
    <div className="flex justify-end p-4">
      <button type="button" className="rounded p-4 m-2 text-xl text-white bg-blue-500" onClick={()=>moveToList({page, size})}>목록</button>
      <button type="button" className="rounded p-4 m-2 text-xl text-white bg-blue-500" onClick={()=>moveToModify(tno)}>수정</button>
    </div>
  </div>;
};

export default ReadComponent;
