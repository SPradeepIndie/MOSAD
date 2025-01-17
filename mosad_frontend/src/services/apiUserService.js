import apiClient from './api_config/apiClient';

export const loginRequest=(data)=>{
    return apiClient.post('/login',JSON.stringify(data));
}

export const registerUser=(data)=>{
    return apiClient.post('/user/register',JSON.stringify(data));
}

export const updateUserDetails=(data,param)=>{
    return apiClient.put('/user/update',data,param);
}

export const deleteUser=(data)=>{
    return apiClient.delete('/user/delete',data)
}

export const getUserDetailsByUsername=(data)=>{
    return apiClient.get('/user/view',data)
}

export const getAllUsername=()=>{
    return apiClient.get('user/view/all');
}

//TODO Should have to create endpoint for  forgotpassword email , otp