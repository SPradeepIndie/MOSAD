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


export const updateItem = (itemId, data) => {
    return apiClient.put(`/update/${itemId}`, data);
};


export const deleteItem = (category, brand, itemId) => {
    return apiClient.delete('/delete', { params: { category, brand, itemId } });
};

//stock redesign
export const fetchCategories =()=>{
    return apiClient.get('/category');
};

export const getBrands =(category)=>{
    return apiClient.get(`/brand?catName=${category}`);
};

export const addCategory =(categoryName) => {
    return apiClient.post('/category', { categoryName });
  };

  export const addItem=(data)=>{
    return apiClient.post('/item/add',data);
} 

