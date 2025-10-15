import { lazy, Suspense } from "react";
import { Navigate } from "react-router-dom";
const Loading = () => <div>Loading...</div>;
const ProductList = lazy(() => import("../pages/product/ListPage"));
const ProductAdd = lazy(() => import("../pages/product/AddPage"));
const ProductRead = lazy(() => import("../pages/product/ReadPage"));
const ProductModify = lazy(() => import("../pages/product/ModifyPage"));
export const productRouter = () => {
  return [
    {
      path: "list",
      element: (
        <Suspense fallback={<Loading />}>
          <ProductList />
        </Suspense>
      ),
    },
    {
      path: "",
      element: <Navigate replace to="/products/list" />,
    },
    {
      path: "add",
      element: (
        <Suspense fallback={<Loading />}>
          <ProductAdd />
        </Suspense>
      ),
    },
    {
      path: "read/:pno",
      element: (
        <Suspense fallback={<Loading />}>
          <ProductRead />
        </Suspense>
      ),
    },
    {
      path: "modify/:pno",
      element: (
        <Suspense fallback={<Loading />}>
          <ProductModify />
        </Suspense>
      ),
    },
  ];
};
