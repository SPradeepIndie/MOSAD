import apiClient from './api_config/apiClient';



export const loginRequest=(data)=>{
    return apiClient.post('/login',JSON.stringify(data));
}

export const registerUser=(data)=>{
    return apiClient.post('/user/register',JSON.stringify(data));
}

export const updateUserDetails=(data)=>{
    return apiClient.put('/user/update',data);
}

export const deleteUser=()=>{
    return apiClient.delete('/user/delete',data)
}

export const getUserDetailsByUsername=()=>{
    return apiClient.get('/user/view',data)
}

export const getAllUsername=()=>{
    return apiClient.get('user/view/all');
}