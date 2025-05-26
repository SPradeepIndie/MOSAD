import useApiClient from '../apiClientHooks/useApiClient';

export const useFetchAllBills = () => {
  const apiClient = useApiClient();

  const fetchAllBills = () => {
    return apiClient.get('/bills');
  };

  return fetchAllBills;
}

export const useUpdateItemQuantity = () => {
    const apiClient = useApiClient();
    
    const updateItemQuantity = (data) => {
        const { itemId, branchId, quantity } = data;
        
        // Pass data as query parameters
        const params = new URLSearchParams();
        params.append("itemId", itemId);
        params.append("branchId", branchId);
        params.append("quantity", quantity);
        
        // Make the PUT request with query parameters
        return apiClient.put('/bills/updatestock', null, { params });
    };
    
    return updateItemQuantity;
};

export const useCreateBill=()=>{
  const apiClient = useApiClient();

  const createBill = (data) =>{
    return apiClient.post('/bills',data)
  };

  return createBill;

};

export const useFetchNormalCustomer = () => {
  const apiClient = useApiClient();

  const fetchNormalCustomer = (params) => {
    return apiClient.get('/bills/getnormal', { params });
  };

  return fetchNormalCustomer;
};

export const useFetchRetailCustomer = () => {
  const apiClient = useApiClient();

  const fetchRetailCustomer = (params) => {
    return apiClient.get('/bills/getretail', { params });
  };

  return fetchRetailCustomer;
}



