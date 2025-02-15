import useAuth from '../useAuth';
import { privateApiClient } from '../../services/api_config/apiClient';

export const usePrivateApiClient = () => {
    const {auth}=useAuth()

    privateApiClient.interceptors.request.use(
        (config)=>{
            const token = auth.refreshToken;
            console.log(token)
            if (token) {
                config.headers.Authorization = `Bearer ${token}`;
            }
            return config;
        },
        (error) => Promise.reject(error)
    );
    
    return privateApiClient;
}
