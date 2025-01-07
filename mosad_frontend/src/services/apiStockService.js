import apiClient from './api_config/apiClient';

export const fetchItems=()=>{
    return apiClient.get();
}

export const fetchBrands=()=>{
    return apiClient.get('/search/brands');
}