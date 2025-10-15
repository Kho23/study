import React from "react";
import { useParams } from "react-router-dom";
import ScoreGetOneComponent from "../../components/score/ScoreGetOneComponent";

const ScoreGetOnePage = () => {
  const { sno } = useParams();
  return (
    <div>
      <ScoreGetOneComponent sno={sno} />
    </div>
  );
};

export default ScoreGetOnePage;
