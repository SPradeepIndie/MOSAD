import { useEffect } from 'react';
import {apiClient} from '../../services/api_config/apiClient';
import useAuth from '../useAuth';
import useRefreshToken from './useRefreshToken';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const useApiClient = () => {
    const {auth, setAuth}=useAuth()
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
                        alert("Your session has expired please log in again \n")
                        localStorage.removeItem('auth'); 
                        setAuth({});
                        navigate('/login', { replace: true });
                        return Promise.reject(error); 
                    }
                }
                return Promise.reject(error); // Pass other errors to the component
                 
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