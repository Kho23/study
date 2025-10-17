import React, { useEffect, useRef, useState } from "react";
import { getOne, host, postAdd } from "../../api/ProductApi";
import FetchingModal from "../common/FetchingModal";
import ResultModal from "../common/ResultModal";
import useCustomMove from "../../hooks/useCustomMove";

const initState = { pname: "", pdesc: "", price: 0, files: [], uploadFileName:[] };
const ReadComponent = ({pno}) => {
  const [product, setProduct] = useState({ ...initState });
  const [fetching, setFetching] = useState(false);
  const { moveToList, moveToModify } = useCustomMove();

  useEffect(() => {
    setFetching(true);
    getOne(pno).then((data) => {
      setProduct(data);
      setFetching(false);
    });
  }, [pno]);

  return (
    <div className="border-2 border-sky-200 mt-10 m-2 p-4">
      {fetching ? <FetchingModal></FetchingModal> : <></>}
      <div className="flex justify-center mt-10">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">제품번호</div>
          <div className="w-4/5 p-6 rounded-r border border-solid shadow-md">
            {product.pno}
          </div>
        </div>
      </div>

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">제품명</div>
          {product.pname}
        </div>
      </div>

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">상품설명</div>
          {product.pdesc}
        </div>
      </div>

      <div className=" w-full flex justify-center flex-col m-auto items-center">
        {product.uploadFileName.map((imageFile,i)=>
        <img aly="product" key={i} className="p-4 w-1/2" src={`${host}/view/${imageFile}`} ></img>
        )}
      </div>
          <button
            type="button"
            className="rounded p-4 w-36 bg-blue-500 text-xl text-white"
            onClick={()=>moveToModify(pno)}
          >
            수정
          </button>
        </div>
  );
};

export default ReadComponent;
