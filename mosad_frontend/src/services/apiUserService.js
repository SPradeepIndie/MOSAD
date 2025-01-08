import apiClient from './api_config/apiClient';
import useAuth from '../hooks/useAuth';



export const loginRequest=(data)=>{
    return apiClient.post('/login',JSON.stringify(data));
}

export const registerUser=(data)=>{
    return apiClient.post('/user/register',JSON.stringify(data));
}

export const updateUserDetails=()=>{
    // const {auth}=useAuth();
    return apiClient.put('/user/update',{param:{username:"auth.username"}});
}

export const deleteUser=()=>{
    // const {auth}=useAuth();
    return apiClient.delete('/user/delete',{param:{username:"auth.username"}})
}

export const getUserDetailsByUsername=()=>{
    // const {auth}=useAuth();
    return apiClient.get('/user/view',{param:{username:"auth.username"}})
}

export const getAllUsername=()=>{
    return apiClient.get('user/view/all');
}