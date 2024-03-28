"use client"
import axios, { AxiosError } from "axios";

const postData = async (url: any, data: any, header: any): Promise<any> => {
    if (url && data && header) {
        try {
            const response = await axios.post(url, data, header)
            console.log(response.data)
            return response.data;
        } catch (error) {
            const axiosError = error as AxiosError;
            console.error('Error :', axiosError.response?.data);
            return null;
        }
    }
    return;
}
export default postData;