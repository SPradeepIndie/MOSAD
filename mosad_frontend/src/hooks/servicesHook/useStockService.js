import useApiClient from '../apiClientHooks/useApiClient';

export const useFetchCategories = () => {
  const apiClient = useApiClient();

  const fetchCategories =  () => {
        return apiClient.get('/category');
  };

  return fetchCategories;
};

export const useAddCategory = () => {
  const apiClient = useApiClient();

  const addCategory =  (categoryName) => {
      return apiClient.post('/category', { categoryName });
  };

  return addCategory;
};

export const useFetchBrands = () => {
  const apiClient = useApiClient();

  const fetchBrands = (category) => {
    return apiClient.get(`/brand?catName=${category}`);
  };

  return fetchBrands;
};

export const useAddBrand = () => {
  const apiClient = useApiClient();

  const addBrand =  (data) => {
      return apiClient.post('/brand', data);
  };

  return addBrand;
};

export const useFetchItems = () => {
  const apiClient = useApiClient();

  const fetchItems = (data) => {
    return apiClient.get('/item/view', data);
  };

  return fetchItems;
};

export const useAddItem = () => {
  const apiClient = useApiClient();

  const addItem = (data) => {
    return apiClient.post('/item/add', data); 
  };

  return addItem;
};

export const useUpdateItem = () => {
  const apiClient = useApiClient();

  const updateItem = (data) => {
    return apiClient.put('/item/update', data);
  };

  return updateItem;
};

export const useDeleteItem = () => {
  const apiClient = useApiClient();

  const deleteItem = (itemId) => {
    return apiClient.delete('/item/delete', { params: { itemId } });
  };

  return deleteItem;
};

export const useFetchBrandAndSizeData = () => {
  const apiClient = useApiClient();

  const fetchBrandAndSizeData = (category,brand,name, size,branchId) => {
    return apiClient.get(`/item/search?category=${category}&brand=${brand}&name=${name}&tyreSize=${size}&branchId=${branchId}`);
  };

  return fetchBrandAndSizeData;
};

export const useFetchBranches = () => {
  const apiClient = useApiClient();

  const fetchBranches = () => {
    return apiClient.get('/item/allbranches');
  };

  return fetchBranches;
}

export const useFetchStockInHistory = () => {
  const apiClient = useApiClient();

  const fetchStockInHistory = (itemId) => {
    return apiClient.get(`item/stockin?itemId=${itemId}`);
  };

  return fetchStockInHistory;
}

export const useSendHelloWorldTemplate = () => {
  const apiClient = useApiClient();

  const sendHelloWorldTemplate = (phoneNumber) => {
    return apiClient.post(`/notifications/hello`, null, {
      params: {
        to: phoneNumber,
      },
    });
  };

  return sendHelloWorldTemplate;
};

export const useHomeStats = () => {
  const apiClient = useApiClient();

  const fetchHomeStats = () => {
    return apiClient.get('/homestats/cards');
  };

  return fetchHomeStats;
}

export const useHomeCalDueDates = () => {
  const apiClient = useApiClient();

  const fetchHomeCalDates = () => {
    return apiClient.get('/homestats/cal');
  };

  return fetchHomeCalDates;
}