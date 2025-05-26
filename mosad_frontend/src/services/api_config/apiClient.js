import axios from 'axios';
//const BASE_URL= window?.configs?.apiUrl ? window.configs.apiUrl+"/api/v1" : import.meta.env.VITE_API_URL;
const BASE_URL= '/choreo-apis/mosad-deploying/mosadbackend/v1';
export const apiClient = axios.create({
    baseURL:BASE_URL,
    headers: {
      'Content-Type': 'application/json',
    },
});

export const privateApiClient = axios.create({
  baseURL:BASE_URL,
  withCredentials:true,
  headers: {
    'Content-Type': 'application/json',
  },
});





