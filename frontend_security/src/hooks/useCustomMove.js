import { useCallback } from "react";
import {
  createSearchParams,
  useNavigate,
  useSearchParams,
} from "react-router-dom";

const getNum = (param, defaultValue) => {
  if (!param) return defaultValue;
  return parseInt(param);
};
const useCustomMove = () => {
  const navigate = useNavigate();
  const [queryParams] = useSearchParams();
  const page = getNum(queryParams.get("page"), 1);
  const size = getNum(queryParams.get("size"), 10);
  const a = getNum(queryParams.get("a"), 10);
  const b = getNum(queryParams.get("b"), 10);
  const queryDefault = createSearchParams({ page, size }).toString();

  const moveToList = useCallback(
    ({ page, size }) => {
      console.log(page, size);
      let queryStr = "";
      
      if (page || size) {
        const pageNum = getNum(page, 1);
        const sizeNum = getNum(size, 10);
        queryStr = createSearchParams({ page: pageNum?pageNum:1, size: sizeNum?sizeNum:10 }).toString();
      } else {
        queryStr = queryDefault;
      }
      navigate({pathname:`../list`, search:queryStr})
    },
    [page, size]
  );

  const moveToModify = useCallback((num) => {
    console.log(queryDefault);
    navigate({ pathname: `../modify/${num}`, search: queryDefault });
  }, [navigate, queryDefault]);

  const moveToRead=(num)=>{
    console.log("여기는 moveToread와 " ,num)
    console.log(queryDefault)
    navigate({pathname:`../read/${num}`, search:queryDefault})
  }

  return { moveToList, moveToModify, moveToRead, page, size, a, b };
};

export default useCustomMove;
