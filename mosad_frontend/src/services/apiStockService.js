import apiClient from './api_config/apiClient';

export const fetchItems=(data)=>{
    return apiClient.get('/item-view',data);
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

export const deleteItem = (category, brand, itemId) => {
    return apiClient.delete('/api/v1/delete', { params: { category, brand, itemId } });
};