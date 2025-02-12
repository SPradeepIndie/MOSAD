import apiClient from "./apiClient";

export const apiClientErrorHandle = () => {
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
  
}
