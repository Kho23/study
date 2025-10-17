import { Suspense } from "react";
import TodoList from "../pages/todo/ListPage";
import TodoRead from "../pages/todo/ReadPage";
import TodoAdd from "../pages/todo/AddPage";
import TodoModify from "../pages/todo/ModifyPage";
import { Navigate } from "react-router-dom";
const Loading = () => <div>Loading...</div>;
export const todoRouter = () => {
  return [
    {
      path: "list",
      element: (
        <Suspense fallback={<Loading />}>
          <TodoList />
        </Suspense>
      ),
    },
    {
      path: "",
      element: <Navigate replace to="/todo/list"></Navigate>,
    },
    {
      path: "read/:tno",
      element: (
        <Suspense fallback={<Loading />}>
          <TodoRead />
        </Suspense>
      ),
    },
    ,
    {
      path: "add",
      element: (
        <Suspense fallback={<Loading />}>
          <TodoAdd />
        </Suspense>
      ),
    },
    {
      path: "modify/:tno",
      element: (
        <Suspense fallback={<Loading />}>
          <TodoModify />
        </Suspense>
      ),
    },
  ];
};
