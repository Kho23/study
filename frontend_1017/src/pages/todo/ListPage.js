import { useSearchParams } from "react-router-dom";
import ListComponent from "../../components/todo/ListComponent";
import PageComponent from "../../components/common/PageComponent";

const ListPage = () => {
  const [queryParams] = useSearchParams();
  const page = queryParams.get("page") ? parseInt(queryParams.get("page")) : 1;
  const size = queryParams.get("size") ? parseInt(queryParams.get("size")) : 10;

  return (
    <div className="p-4 w-full bg-orange-200">
      <div className="text-3xl font-extrabold">
        Todo List Page components {page}------------{size}
      </div>
      <ListComponent page={page} size={size} />

    </div>
  );
};

export default ListPage;