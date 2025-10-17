import axios from 'axios'

export const API_SERVER_HOST='http://localhost:8080'
const prefix=`${API_SERVER_HOST}/score`

export const getList = async () =>{
    const {data} = await axios.get(`${prefix}/list`)
    console.log(data)
    return data
}

export const getOne = async (sno) =>{
    const {data} = await axios.get(`${prefix}/read/${sno}`)
    console.log(data)
    return data
}