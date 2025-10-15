import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getOne } from "../../api/ScoreApi";
import makeDiv from "../../utils/autocss";
const initState = {
    eng:0,
    math:0,
    korea:0,
    total:0.0,
    avg:0.0,
    grade:''
};
const ScoreGetOneComponent = ({ sno }) => {
  const [score, setScore] = useState(initState);
  const nav=useNavigate()
  useEffect(() => {
    getOne(sno).then((d) => {
      console.log(d);
      setScore(d);
    });
  }, [sno]);
  return (
    <div>
      {makeDiv("수학", score.math)}
      {makeDiv("영어", score.eng)}
      {makeDiv("국어", score.korea)}
      {makeDiv("총점", score.total)}
      {makeDiv("평균", score.avg)}
      {makeDiv("등급", score.grade)}
    <button onClick={()=>nav('/s/list')}>목록으로</button>
    </div>
  );
};

export default ScoreGetOneComponent;
