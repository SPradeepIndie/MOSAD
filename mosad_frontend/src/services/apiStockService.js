import apiClient from '../apiConfig/apiClient';

export const fetchItems=()=>{
    return apiClient.get()
}