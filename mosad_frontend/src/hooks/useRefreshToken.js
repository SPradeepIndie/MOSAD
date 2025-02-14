import useAuth from './useAuth'
import { usePrivateApiClient } from './usePrivateApiClient';

const useRefreshToken = () => {
    const {setAuth}= useAuth();
    const privateApiClient= usePrivateApiClient();

    const refresh = async ()=>{
        const response = (await privateApiClient.post('/refresh_token'));
        const { access_token } = response.data;
        console.log(access_token)
        setAuth(prev=>{
            return{
                ...prev,accessToken:access_token
            }
        })
        return access_token;
    }

    return refresh;
}

export default useRefreshToken