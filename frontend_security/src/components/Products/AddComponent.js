import React, { useRef, useState } from "react";
import { postAdd } from "../../api/ProductApi";
import FetchingModal from "../common/FetchingModal";
import ResultModal from "../common/ResultModal";
import useCustomMove from "../../hooks/useCustomMove";

const initState = { pname: "", pdesc: "", price: 0, files: [] };
const AddComponent = () => {
  const [product, setProduct] = useState({ ...initState });
  const [fetching, setFetching] = useState(false);
  const [result, setResult] = useState(null);
  const { moveToList } = useCustomMove();
  const uploadRef = useRef();

  const handleChangeProduct = (e) => {
    const { name, value } = e.target;
    product[name] = value;
    setProduct({ ...product });
  };
  const handleAddClick = (e) => {
    console.log(product);
    const files = uploadRef.current.files;
    const formData = new FormData();
    for (let i = 0; i < files.length; i++) {
      formData.append("files", files[i]); // {files: [<Multipar, <Multipar ]}
    }
    formData.append("pname", product.pname); // pname='a'&pdesc='b'
    formData.append("pdesc", product.pdesc); //
    formData.append("price", product.price);
    console.log(formData);
    setFetching(true);
    postAdd(formData).then((data) => {
      console.log("데이터 추가 후:", data);
      setFetching(false);
      setResult(data.결과);
    });
  };
  const closemodal = () => {
    setResult(null);
    moveToList({page: 1});
  };

  return (
    <div className="border-2 border-sky-200 mt-10 m-2 p-4">
      {fetching ? <FetchingModal></FetchingModal> : <></>}
      {result ? (
        <ResultModal
          title={"제품 추가 결과"}
          content={`${result} 번 등록 완료`}
          callbackFn={closemodal}
        />
      ) : (
        <></>
      )}
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">제품명</div>
          <input
            type="text"
            name="pname"
            value={product.pname}
            onChange={handleChangeProduct}
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
          />
        </div>
      </div>

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">상품설명</div>
          <textarea
            name="pdesc"
            value={product.pdesc}
            onChange={handleChangeProduct}
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md resize-y"
            rows="4"
          >
            {product.pdesc}
          </textarea>
        </div>
      </div>

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">가격</div>
          <input
            type="number"
            name="price"
            value={product.price}
            onChange={handleChangeProduct}
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
          />
        </div>
      </div>

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">파일</div>
          <input
            type="file"
            name="pname"
            ref={uploadRef}
            multiple={true}
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
          />
        </div>
        <div className="relative mb-4 flex p-4 flex-wrap items-stretch">
          <button
            type="button"
            className="rounded p-4 w-36 bg-blue-500 text-xl text-white"
            onClick={handleAddClick}
          >
            추가
          </button>
        </div>
      </div>
    </div>
  );
};

export default AddComponent;
