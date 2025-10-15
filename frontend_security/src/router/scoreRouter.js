import { Suspense } from "react";
import ScoreList from "../pages/score/ScoreListPage";
import ScoreGetOne from "../pages/score/ScoreGetOnePage";
const Loading = () => <div>Loading...</div>;
export const scoreRouter = () => {
  return [
    {
      path: "s/list",
      element: (
        <Suspense fallback={<Loading />}>
          <ScoreList />
        </Suspense>
      ),
    },
    {
      path: "s/:sno",
      element: (
        <Suspense fallback={<Loading />}>
          <ScoreGetOne />
        </Suspense>
      ),
    },
  ];
};
