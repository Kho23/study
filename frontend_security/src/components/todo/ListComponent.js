import React, { useEffect, useState } from "react";
import { getList, getOne } from "../../api/TodoApi";
import makeDiv from "../../utils/autocss";
import useCustomMove from "../../hooks/useCustomMove";
import PageComponent from "../common/PageComponent";
import { useNavigate } from "react-router-dom";
const initState = {
  dtoList: [],
  pageNumList: [],
  pageRequestDto: null,
  prev: false,
  next: false,
  totalCount: 0,
  prevPage: 0,
  nextPage: 0,
  totalPage: 0,
  current: 0,
};
const ListComponent = () => {
  const [serverData, setServerData] = useState(initState);
  const { page,size, moveToRead,moveToList } = useCustomMove();
  const nav = useNavigate();
 
  useEffect(() => {
    getList({ page, size }).then((d) => {
      //pageResponseDTO<TodoDTO> 가 담겨옴 = d
      console.log(d);
      console.log('d:',d)
      setServerData(d);
    });
  }, [page, size]);
  return (
    <div className="border-2 border-sky-200 mt-10 m-2 p-4">
      <div className="flex flex-wrap mx-auto justify-center p-6">
        {serverData.dtoList.map((i) => (
          <div key={i.tno} className="w-full min-w-[400px] p-2 m-2 rounded shadow-md" onClick={()=>moveToRead(i.tno)}>
            <div key={i.tno} className="w-full min-w-[400px] p-2 m-2 rounded shadow-md">
              <div className="flex">
                <div className="font-extrabold text-2xl p-2 w-1/3">{i.tno}</div>
                <div className="font-extrabold text-2xl p-2 w-1/3">{i.title}</div>
                <div className="font-extrabold text-2xl p-2 w-1/3">{i.dueDate}</div>
              </div>
            </div>
          </div>
        ))}
      </div>
            <PageComponent serverData={serverData}
                           movePage={(i)=>moveToList(i)} />
    </div>
  );
};

export default ListComponent;
