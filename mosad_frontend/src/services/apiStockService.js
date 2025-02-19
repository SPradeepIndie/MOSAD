import apiClient from './api_config/apiClient';

export const fetchBranches =()=>{
    return apiClient.get('/item/allbranches');
};

export const fetchCategories =()=>{
    return apiClient.get('/category');
};

export const addCategory =(categoryName) => {
    return apiClient.post('/category', { categoryName });
  };

export const fetchBrands =(category)=>{
    return apiClient.get(`/brand?catName=${category}`);
};

export const addBrand =(data)=>{
    return apiClient.post('/brand', data);
};

export const fetchItems=(data)=>{
    return apiClient.get('/item/view',data);
}

export const addItem=(data)=>{
    return apiClient.post('/item/add',data);
} 

export const updateItem=(data)=>{
    return apiClient.put('/item/update',data);
} 

export const deleteItem = (itemId) => {
    return apiClient.delete('/item/delete', { params: { itemId } });
};

export const fetchBrandAndSizeData = (category,brand,name, size,branchId) => {
    return apiClient.get(`/item/search?Category=${category}&Brand=${brand}&name=${name}&tyreSize=${size}&branchId=${branchId}`);
  };