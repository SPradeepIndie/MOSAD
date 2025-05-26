import axios from 'axios';
//const BASE_URL= window?.configs?.apiUrl ? window.configs.apiUrl+"/api/v1" : import.meta.env.VITE_API_URL;
const BASE_URL= '313dce28-5c14-4990-8e70-661764f55665-dev.e1-us-east-azure.choreoapis.dev/mosad-deploying/mosadbackend/api/v1';
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





