import apiClient from './api_config/apiClient';

export const fetchItems=()=>{
    return apiClient.get();
}

export const fetchBrands=()=>{
    return apiClient.get('/search/brands');
}

export const fetchBrandAndSizeData=(brand,size)=>{
    return apiClient.get('/search/brand',{params:{brand:brand,size:size}});
}


export const addItem=(cat_brand,data)=>{
    return apiClient.post(`/stock/${cat_brand}`,data);//need to update after implementing the backend
}

export const updateItem=(cat_brand,data)=>{
    return apiClient.put(`/stock/${cat_brand}`,data);//need to update after implementing the backend
}