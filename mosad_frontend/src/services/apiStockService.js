import apiClient from './api_config/apiClient';

export const fetchItems=(data)=>{
    return apiClient.get('/item-view',data);
}

export const fetchBrands=()=>{
    return apiClient.get('/search/brands');
}

