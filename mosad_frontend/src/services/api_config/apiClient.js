import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';
import useAuth from '../../hooks/useAuth';
const BASE_URL= 'http://localhost:8080/api/v1'

export const getUseAuth=()=>{
  const {auth}= useAuth();
  return auth;
};

export const navigateToLogin=()=>{
  const navigate = useNavigate();
  return navigate
}

const apiClient = axios.create({
    baseURL:BASE_URL,
    headers: {
      'Content-Type': 'application/json',
    },
});

const privateApiClient = axios.create({
  baseURL:BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

  
apiClient.interceptors.response.use(
  response => response,
  async error => {
    const originalRequest = error.config;

    // If the error status is 401 and there is no originalRequest._retry flag,
    // it means the token has expired and we need to refresh it
    if (error.response.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      try {
        const response = (await privateApiClient.post('/refresh_token'));
        const { access_token } = response.data;

        localStorage.setItem('token', access_token);

        // Retry the original request with the new token
        originalRequest.headers.Authorization = `Bearer ${access_token}`;
        return axios(originalRequest);
      } catch (error) {
        originalRequest._retry = false;
        alert("Unauthorized")
        // Used replace to prevent back navigation
        //TODO if logout happen naviagate to login page
      }
    }
   
    else if (error.response.status === 404) {
      alert(" Not found")
    }
    else if(error.response.status === 400){
      alert(error.response.data);
    }
    else if (error.request) {
      console.log("The request was made but no response was received")
      console.log(error.request);
    }
    console.error('API call failed:', error);
    
    return Promise.reject(error);
  }
);

//interceptor for requests and add token for every request except login and refersh_token
apiClient.interceptors.request.use(
  (config)=>{
    const token = localStorage.getItem('token');
    if (token && config.url !== '${apiClient.defaults.baseURL}/login' && config.url !== '${apiClient.defaults.baseURL}/refresh_token') {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
  
);

privateApiClient.interceptors.request.use(
  (config)=>{
    const token = localStorage.getItem('refresh_token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
  
);


export default apiClient;
