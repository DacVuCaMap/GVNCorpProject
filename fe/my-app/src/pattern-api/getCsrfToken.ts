import axios, { AxiosError } from "axios";

const getCsrfToken = async (): Promise<any> => {
    try {
      const response = await axios.get("http://localhost:8080/api/auth/csrf",{ withCredentials: true });
      console.log(response.data);
      return response.data;
    } catch (error) {
        let err = error as AxiosError;
        console.log('get error to get user by id: ', err.response);
        return null;
    }
  }
  
  export default getCsrfToken;