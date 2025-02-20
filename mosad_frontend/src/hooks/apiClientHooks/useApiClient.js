import {apiClient} from '../../services/api_config/apiClient';
import useAuth from '../useAuth';
import useRefreshToken from './useRefreshToken';

const useApiClient = () => {
    const {auth}=useAuth()
    const refresh =useRefreshToken();
    //Handle Error using apiCLient with access token
    apiClient.interceptors.response.use(
        response => response,
        async error => {
            const originalRequest = error.config;
        
            // If the error status is 401 and there is no originalRequest._retry flag,
            // it means the token has expired and we need to refresh it
            if (error.response.status === 401 && !originalRequest._retry) {
                originalRequest._retry = true;
                try {
                    const access_token = refresh(); 
                    console.log(access_token)
                    // Retry the original request with the new token
                    originalRequest.headers.Authorization = `Bearer ${access_token}`;
                    return axios(originalRequest);
                } catch (error) {
                    originalRequest._retry = false;
                    alert("Unauthorized here")
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

    //Add access token to the request
    apiClient.interceptors.request.use(
        (config)=>{
            const token = auth.accessToken;
            if (token && config.url !== '${apiClient.defaults.baseURL}/login' && config.url !== '${apiClient.defaults.baseURL}/refresh_token') {
            config.headers.Authorization = `Bearer ${token}`;
            }
            return config;
        },
        (error) => Promise.reject(error)
    );

        
    return apiClient;
}

export default useApiClient