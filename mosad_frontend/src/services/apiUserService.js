import apiClient from './api_config/apiClient';

export const loginRequest=(data)=>{
    return apiClient.post('/login',JSON.stringify(data));
}

export const registerUser=(data)=>{
    return apiClient.post('/user/register',JSON.stringify(data));
}

export const updateUserDetails=(data)=>{
    return apiClient.post('/user/update',JSON.stringify(data));
}

export const deleteUser=(data)=>{
    return apiClient.post('/user/delete',JSON.stringify(data))
}

export const getUserbyUsername=(data)=>{
    return apiClient.post('/user/view',JSON.stringify(data))
}