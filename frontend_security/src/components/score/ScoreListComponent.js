import React, { useEffect, useState } from "react";
import { getList } from "../../api/ScoreApi";
import { Link } from "react-router-dom";
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
const ScoreListComponent = () => {
  const [serverData, setServerData] = useState(initState);
  useEffect(() => {
    getList().then((d) => {
      console.log(d);
      console.log("d:", d);
      setServerData(d);
    });
  }, []);
  return   <div className="border-2 border-sky-200 mt-10 m-2 p-4">
      <div className="flex flex-wrap mx-auto justify-center p-6">
        {serverData.dtoList.map((i) => (
          <div>
            <div key={i.sno} className="w-full min-w-[400px] p-2 m-2 rounded shadow-md">
              <div className="flex">
                <div className="font-extrabold text-2xl p-2 w-1/12"><Link to={`/s/${i.sno}`}>{i.sno}</Link></div>
                <div className="font-extrabold text-2xl p-2 w-1/12">{i.eng}</div>
                <div className="font-extrabold text-2xl p-2 w-1/12">{i.math}</div>
                <div className="font-extrabold text-2xl p-2 w-1/12">{i.korea}</div>
                <div className="font-extrabold text-2xl p-2 w-1/12">{i.total}</div>
                <div className="font-extrabold text-2xl p-2 w-1/12">{i.avg}</div>
                <div className="font-extrabold text-2xl p-2 w-1/12">{i.grade}</div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>;
};

export default ScoreListComponent;
