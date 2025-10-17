import axios from "axios";

export const API_SERVER_HOST = "http://localhost:8080";
const prefix = `${API_SERVER_HOST}/todo`;

export const getOne = async (tno) => {
  const { data } = await axios.get(`${prefix}/read/${tno}`);
  return data;
};

export const getList = async ({ page, size }) => {
  var str = `${prefix}/list?page=${page}&size=${size}`;
  const { data } = await axios.get(str);
  console.log(data);
  return data;
};

export const postAdd = async (todoObj) => {
  const res = await axios.post(`${prefix}/`, todoObj);
  return res.data;
};
// 10/10 이후 학습내용
//삭제기능
export const deleteOne = async (tno) => {
  console.log("tno", tno);
  const res = await axios.delete(`${prefix}/${tno}`);
  console.log(res);
  return res.data;
};
//수정기능
export const putOne = async(todo)=>{
  console.log('todo',todo)
  const res=await axios.put(`${prefix}/${todo.tno}`,todo)
  console.log(res)
  return res.data
}
