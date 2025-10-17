import axios from "axios";
import {API_SERVER_HOST} from "../api/TodoApi"

export const host = `${API_SERVER_HOST}/api/products`;

export const getList = async (pageParam) => {
  console.log("product list 들어왔어요");
  const { page, size } = pageParam;
  var str = `${host}/list`;
  console.log("backend로부터 온 데이터 str: ", str);
  const res = await axios.get(str, { params: { page, size } });
  return res.data;
};

export const postAdd = async (product) => {
  // { files: [], pdesc: "", pname: "", price: 0}
  console.log(product);
  const header = { Headers: { "Content-Type": "multipart/form-data" } };
  const res = await axios.post(`${host}/`, product, header);
  console.log("backend로부터 온 데이터 res: ", res);
  return res.data;
};

export const getOne = async (pno) => {
  const res = await axios.get(`${host}/${pno}`)
  console.log(res)
  return res.data;
}

export const putProduct = async(pno,product)=>{
  const header = {headers:{"Content-Type":"multipart/form-data"}}
  const res = await axios.put(`${host}/${pno}`,product, header)
  return res.data
}

export const deleteProduct = async(pno) =>{
  const res = axios.delete(`${host}/${pno}`)
  return res.data
}