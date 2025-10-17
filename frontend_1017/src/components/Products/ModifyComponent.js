import React, { useEffect, useRef, useState } from "react";
import { getOne, postAdd } from "../../api/ProductApi";
import FetchingModal from "../common/FetchingModal";
import useCustomMove from "../../hooks/useCustomMove";
import { host, deleteProduct, putProduct } from "../../api/ProductApi";
import { flushSync } from "react-dom";
import ResultModal from "../common/ResultModal";

const initState = { pname: "", pdesc: "", price: 0, files: [] };
const ModifyComponent = ({ pno }) => {
  const [product, setProduct] = useState({ ...initState });
  const [fetching, setFetching] = useState(false);
  const uploadRef = useRef();
  const { moveToList, moveToModify, moveToRead } = useCustomMove();
  const [result, setResult] = useState(false);
  useEffect(() => {
    console.log(pno);
    setFetching(true);
    getOne(pno).then((data) => {
      setProduct(data);
      setFetching(false);
    });
  }, [pno]);
  const closemodal = () => {
    if (result == "수정되었습니다") moveToRead(pno);
    else if(result=="삭제되었습니다") moveToList({});
  };
  const handleChangeProduct = (e) => {
    const { name, value } = e.target;
    product[name] = value;
    setProduct({ ...product });
  };
  const deleteOldImages = (imageName) => {
    const resulstFileName = product.uploadFileName.filter(
      (fileName) => fileName !== imageName
    );
    product.uploadFileName = resulstFileName;
    setProduct({ ...product });
  };
  const handleClickModify = () => {
    const files = uploadRef.current.files;
    const formData = new FormData();
    for (let i = 0; i < files.length; i++) {
      formData.append("files", files[i]);
    }
    formData.append("pname", product.pname);
    formData.append("pdesc", product.pdesc);
    formData.append("price", product.price);
    formData.append("delFlag", product.delFlag);
    for (let i = 0; i < product.uploadFileName.length; i++) {
      formData.append("uploadFileName", product.uploadFileName[i]);
    }
    putProduct(pno, formData).then((d) => {
      setResult("수정되었습니다");
      setFetching(false);
    });
  };
  const handleClickDelete = () => {
    setFetching(true);
    deleteProduct(pno).then((d) => {
      setResult("삭제되었습니다");
      setFetching(false);
    });
  };
  return (
    <div className="border-2 border-sky-200 mt-10 m-2 p-4">
      {fetching ? <FetchingModal></FetchingModal> : <></>}
      {result ? (
        <ResultModal
          title={`${pno}`}
          content={`${pno} 번 ${result}`}
          callbackFn={closemodal} 
        />
      ) : (
        <></>
      )}
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">Product Name</div>
          <input
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
            name="pname"
            type={"text"}
            value={product.pname}
            onChange={handleChangeProduct}
          ></input>
        </div>
      </div>
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">Desc</div>
          <textarea
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md resize-y"
            name="pdesc"
            rows="4"
            onChange={handleChangeProduct}
            value={product.pdesc}
          >
            {product.pdesc}
          </textarea>
        </div>
      </div>
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">Price</div>
          <input
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
            name="price"
            type={"number"}
            value={product.price}
            onChange={handleChangeProduct}
          ></input>
        </div>
      </div>

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">DELETE</div>
          <select
            name="delFlag"
            value={product.delFlag}
            onChange={handleChangeProduct}
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
          >
            <option value={false}>사용</option>
            <option value={true}>삭제</option>
          </select>
        </div>
      </div>

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">Files</div>
          <input
            ref={uploadRef}
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
            type={"file"}
            multiple={true}
          ></input>
        </div>
      </div>
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">Images</div>
          <div className="w-4/5 justify-center flex flex-wrap items-start">
            {product.uploadFileName &&
              product.uploadFileName.map((imgFile, i) => (
                <div className="flex justify-center flex-col w-1/3" key={i}>
                  <button
                    className="bg-blue-500 text-3xl text-white"
                    onClick={() => deleteOldImages(imgFile)}
                  >
                    DELETE
                  </button>
                  <img
                    alt="img"
                    src={`${host}/view/s_${imgFile}`}
                  />
                </div>
              ))}
          </div>
        </div>
      </div>

      <div className="flex justify-end p-4">
        <button
          type="button"
          className="rounded p-4 m-2 text-xl w-32 text-white bg-red-500"
          onClick={handleClickDelete}
        >
          Delete
        </button>

        <button
          type="button"
          className="inline-block rounded p-4 m-2 text-xl w-32  text-white bg-orange-500"
          onClick={handleClickModify}
        >
          Modify
        </button>

        <button
          type="button"
          className="rounded p-4 m-2 text-xl w-32 text-white bg-blue-500"
          onClick={moveToList}
        >
          List
        </button>
      </div>
    </div>
  );
};

export default ModifyComponent;
