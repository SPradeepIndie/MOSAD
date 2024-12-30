import apiClient from './api_config/apiClient';

export const loginRequest=(data)=>{
    return apiClient.post('/login',data);
}

export const registerUser=(data)=>{
    return apiClient.post('/user/register',data);
}

export const updateUserDetails=(data)=>{
    return apiClient.post('',data);
}

export const deleteUser=(data)=>{
    return apiClient.post('',data)
}