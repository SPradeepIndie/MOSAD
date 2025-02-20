import { useEffect } from 'react';
import {apiClient} from '../../services/api_config/apiClient';
import useAuth from '../useAuth';
import useRefreshToken from './useRefreshToken';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const useApiClient = () => {
    const {auth}=useAuth()
    const refresh =useRefreshToken();
    const navigate=useNavigate();

    useEffect(()=>{
         //Handle Error using apiCLient with access token
        const apiClientResponseInterceptor=apiClient.interceptors.response.use(
            response => response,
            async error => {
                const originalRequest = error.config;
                // If the error status is 401 and there is no originalRequest._retry flag,
                // it means the token has expired and we need to refresh it
                if (error.response.status === 401 && !originalRequest._retry) {
                    originalRequest._retry = true;
                    try {
                        const access_token = await refresh();
                        originalRequest.headers.Authorization = `Bearer ${access_token}`;
                        return axios(originalRequest);
                    } catch (error) {
                        originalRequest._retry = false;
                        // Handle refresh token error (redirect to login)
                        console.error("Failed to refresh access token. Logging out."); 
                        alert("Your session has expired please log in again \n"+error.response.data)
                        localStorage.removeItem('auth'); 
                        navigate('/login', { replace: true });
                        return Promise.reject(error); 
                    }
                }

                else if (error.response.status === 404) {
                    alert("api client: Not found. Please check the requested URL. \n"+error.response.data)
                    return Promise.reject(error); 
                }
                else if (error.response.status === 400) {
                    alert("api client: Bad Request. Please review the request data. \n"+error.response.data); 
                    return Promise.reject(error); 
                }
                else if (error.request) {
                    alert("api client: Check network connectivity. \n");
                    console.log(error.request);
                } else {
                    console.error("An unexpected error occurred during the API call.",error); 
                    return Promise.reject(error);
                } 
            }
        );

        //Add access token to the request
        const apiClientRequestInterceptor=apiClient.interceptors.request.use(
            (config)=>{
                const token = auth.accessToken;
                if (token) {
                config.headers.Authorization = `Bearer ${token}`;
                }
                return config;
            },
            (error) => Promise.reject(error)
        );

        //clean-up fucntion - (Not to pile interceptors)
        return ()=>{
            apiClient.interceptors.response.eject(apiClientResponseInterceptor);
            apiClient.interceptors.request.eject(apiClientRequestInterceptor)
        }

    },[auth,refresh])

   
    return apiClient;
}

export default useApiClient