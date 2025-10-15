// root.js

import { lazy, Suspense } from "react";
import { todoRouter } from "./todoRouter";
import { scoreRouter } from "./scoreRouter"; // 👈 import 됨
import { productRouter } from "./productRouter";

const { createBrowserRouter } = require("react-router-dom");
const Loading = () => <div>Loading...</div>;
const Main = lazy(() => import("../pages/MainPage"));
const About = lazy(() => import("../pages/AboutPage"));
const TodoIndex = lazy(() => import("../pages/todo/IndexPage"));
const ProductIndex = lazy(()=>import("../pages/product/IndexPage"))

const root = createBrowserRouter([
  {
    path: "",
    element: (
      <Suspense fallback={<Loading />}>
        <Main />
      </Suspense>
    ),
  },
  {
    path: "about",
    element: (
      <Suspense fallback={<Loading />}>
        <About />
      </Suspense>
    ),
  },
  {
    path: "todo",
    element: (
      <Suspense fallback={<Loading />}>
        <TodoIndex />
      </Suspense>
    ),
    children: todoRouter(),
  },
  {
    path: "product",
    element: (
      <Suspense fallback={<Loading />}>
        <ProductIndex />
      </Suspense>
    ),
    children: productRouter(),
  },
]);

export default root;
