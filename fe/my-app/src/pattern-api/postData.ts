"use client"
import axios, { AxiosError } from "axios";

const postData = async (url: any, data: any, header: any): Promise<any> => {
    console.log("url: ",url)
    console.log("data: ",data)
    try {
        const response = await axios.post(url, data, header)
        console.log(response.data)
        return response.data;
    } catch (error) {
        const axiosError = error as AxiosError;
        console.log("error: ",error)
        console.error('Error :', axiosError.response?.data);
        return axiosError.response;
    }
}
export default postData;