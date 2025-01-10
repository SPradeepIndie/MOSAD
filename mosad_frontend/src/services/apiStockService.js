import apiClient from './api_config/apiClient';

export const fetchItems=(data)=>{
    return apiClient.get('/item-view',data);
}

export const fetchBrands=()=>{
    return apiClient.get('/search/brands');
}


export const addItem=(cat_brand,data)=>{
    return apiClient.post(`/stock/${cat_brand}`,data);//need to update after implementing the backend
}

export const updateItem=(cat_brand,data)=>{
    return apiClient.put(`/stock/${cat_brand}`,data);//need to update after implementing the backend
}