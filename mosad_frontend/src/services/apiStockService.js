import apiClient from './api_config/apiClient';


export const fetchItems=(data)=>{
    return apiClient.get('/item/view',data);
}

export const fetchBrands=()=>{
    return apiClient.get('/search/brands');
}

export const fetchBrandAndSizeData=(brand,size)=>{
    return apiClient.get('/search/brand',{params:{brand:brand,size:size}});
}



export const deleteItem = (itemId) => {
    return apiClient.delete('/item/delete', { params: { itemId } });
};


//stock redesign


export const fetchCategories =()=>{
    return apiClient.get('/category');
};

export const addCategory =(categoryName) => {
    return apiClient.post('/category', { categoryName });
  };

export const getBrands =(category)=>{
    return apiClient.get(`/brand?catName=${category}`);
};

export const addBrand =(categoryName,brandName)=>{
    const payload = {
        brandDTO: {
          brandName: brandName,
        },
        category: {
          categoryName: categoryName,
        },
      };
    
      return apiClient.post('/brand', payload);
};

export const addItem=(data)=>{
    return apiClient.post('/item/add',data);
} 

export const updateItem=(data)=>{
    return apiClient.put('/item/update',data);
} 


