import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8080/api/v1/',
    headers: {
      'Content-Type': 'application/json',
    },
  });
  
apiClient.interceptors.response.use(
  response => response,
  async error => {
    // If the error status is 401 and there is no originalRequest._retry flag,
    // it means the token has expired and we need to refresh it
    if (error.response.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        const refreshToken = localStorage.getItem('refreshToken');
        const response = await axios.post('/api/refresh-token', { refreshToken });
        const { token } = response.data;

        localStorage.setItem('token', token);

        // Retry the original request with the new token
        originalRequest.headers.Authorization = `Bearer ${token}`;
        return axios(originalRequest);
      } catch (error) {
        // Handle refresh token error or redirect to login
      }
    }
    // User login unauthrized area
    else if (error.response.status === 401) {
      alert("Unauthorized")
    } 
    else if (error.response.status === 404) {
      alert(" Not found")
    }
    else if(error.response.status === 400){
      alert(error.response.data);
    }
    else if (error.request) {
      // The request was made but no response was received
      console.log(error.request);
    }
    console.error('API call failed:', error);
    
    return Promise.reject(error);
  }
);

//interceptor for request add token for every request except login
apiClient.interceptors.request.use(
  (config)=>{
    const token = localStorage.getItem('token');
    if (token && config.url !== '${apiClient.defaults.baseURL}/login') {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
  
);


export default apiClient;
