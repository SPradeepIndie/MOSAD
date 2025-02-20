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
                    alert("Private api client: Unauthorized. \n"+error.response.data)  
                }
                else if (error.response.status === 404) {
                    alert("Private api client: Not found. \n"+error.response.data)
                }
                else if(error.response.status === 400){
                    alert("Private api client: Bad Request. \n"+error.response.data);
                }
                else if (error.request) {
                    alert("Private api client: Check network connectivity.");
                    console.log("Private api client: Check network connectivity.")
                    console.log(error.request);
                }
                console.error('An unexpected error occurred during the private API call.', error);
                
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
