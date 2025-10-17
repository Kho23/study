import axios from "axios";
import { useCallback, useEffect, useState } from "react";
import {
  createSearchParams,
  useNavigate,
  useParams,
  useSearchParams,
} from "react-router-dom";
import ReadComponent from "../../components/todo/ReadComponent";

const ReadPage = () => {
  const { tno } = useParams();
  const navigate = useNavigate();
  const [queryParmas] = useSearchParams();
  const page = queryParmas.get("page") ? parseInt(queryParmas.get("page")) : 1;
  const size = queryParmas.get("size") ? parseInt(queryParmas.get("size")) : 10;
  const queryStr = createSearchParams({ page, size }).toString();
  const moveToModify = useCallback(
    (tno) => {
      navigate({ pathname: `/todo/modify/${tno}`, queryStr });
    },
    [tno, page, size]
  );

  console.log(tno);

  return (
    <div className="text-3xl font-extrabold bg-white mt-6">

      <div className="text-2xl">Todo Read Page Component {tno}</div>
      <ReadComponent tno={tno}/>
    </div>
  );
};

export default ReadPage;
