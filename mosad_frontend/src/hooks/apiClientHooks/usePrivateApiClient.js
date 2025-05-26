import useAuth from '../useAuth';
import { privateApiClient } from '../../services/api_config/apiClient';
import { useEffect } from 'react';


export const usePrivateApiClient = () => {
    const {auth} = useAuth()

    useEffect(()=>{
        const privateApiClientResponseInterceptor=privateApiClient.interceptors.response.use(
            response => response,
            async error => {
                if (error.response.status === 401  ) {
                    alert("Private api client: Unauthorized. \n"+error.response.data);
                    return Promise.reject(error);  
                }
                return Promise.reject(error);
            }
        );
        
        //clean-up fucntion ->(Not to pile interceptors)
        return()=>{
            privateApiClient.interceptors.response.eject(privateApiClientResponseInterceptor);
        }
    },[auth])
    
    
    return privateApiClient;
}
