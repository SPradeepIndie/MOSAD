import apiClient from './apiClient'
import React from 'react'

export const apiRequestInterceptors = () => {
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
      

  return (
    <div>apiRequestInterceptors</div>
  )
}
