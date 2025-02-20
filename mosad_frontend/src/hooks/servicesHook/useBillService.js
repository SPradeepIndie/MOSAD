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



