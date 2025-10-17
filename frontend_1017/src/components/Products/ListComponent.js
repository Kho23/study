import { useEffect, useState } from "react";
import { getList } from "../../api/ProductApi";

import useCustomMove from "../../hooks/useCustomMove";
import PageComponent from "../common/PageComponent";
import FetchingModal from "../common/FetchingModal";
import { host } from "../../api/ProductApi";
const initState = {
  dtoList: [],
  pageNumList: [],
  pageRequestDTO: null,
  prev: false,
  next: false,
  totalCount: 0,
  prevPage: 0,
  nextPage: 0,
  totalPage: 0,
  current: 0,
};
// export const API_SERVER_HOST = "http://localhost:8080";
// const prefix = `${API_SERVER_HOST}/api/products`;
const ListComponent = () => {
  const [serverData, setServerData] = useState(initState);
  const { page, size, moveToRead, refresh, moveToList } = useCustomMove();
  const [fetching, setFetching] = useState(false);
  useEffect(() => {
    getList({ page, size }).then((d) => {
      setFetching(true);
      console.log(d);
      setServerData(d);
      setFetching(false);
    });
  }, [page, size, refresh]);
  const test = (i) => {
    console.log(i);
  };
  return (
    <div className="border-2 border-blue-100 mt-10 mr-2 ml-2">
      <h1> ProductList components</h1>
      {fetching ? <FetchingModal /> : <></>}
      <div className="flex flex-wrap mx-auto p-6">
        {serverData['dtoList'].map((i) => (
          <div
            key={i.pno}
            className="w-1/2 p-1 rounded shadow-md"
            onClick={()=>moveToRead(i.pno)}
          >
            <div className="flex flex-col h-full">
              <div className="font-extrabold text-2xl p-2 w-full">{i.pno}</div>
              <div className="text-1xl m-1 p-2 w-full flex flex-col">
                <div className="w-full overflow-hidden">
                  <img
                    alt="product"
                    className="m-auto rounded w-60"
                    src={`${host}/view/s_${i.uploadFileName[0]}`}
                  />
                </div>
                <div className="bottom-0 font-extrabold bg-white">
                  <div className="text-center p-1">이름 :{i.pname}</div>
                  <div className="text-center p-1">가격 :{i.price}</div>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
      <PageComponent serverData={serverData} movePage={()=>moveToList()}/>
    </div>
  );
};

export default ListComponent;
