import apiClient from './api_config/apiClient';

export const fetchItems=()=>{
    return apiClient.get();
}

export const fetchBrands=()=>{
    return apiClient.get('/search/brands');
}

export const fetchBrandAndSizeData=(brand,size)=>{
    return apiClient.get('/search/brand?brand=atlander&size=100x90x17');
}

