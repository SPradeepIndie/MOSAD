import apiClient from './api_config/apiClient';

export const addItem=(data)=>{
    return apiClient.post('/user/register',JSON.stringify(data));
}

export const addBillDetails=()=>{
    return apiClient.post('/')
}