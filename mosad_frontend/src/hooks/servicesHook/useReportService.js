import useApiClient from '../apiClientHooks/useApiClient';

export const useItemCounts = () => {
    const apiClient = useApiClient();
  
    const fetchItemCount =  () => {
          return apiClient.get('/report');
    };
  
    return fetchItemCount;
  };