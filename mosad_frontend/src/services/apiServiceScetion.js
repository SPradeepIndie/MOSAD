import useApiClient from '../apiClientHooks/useApiClient';

export const useFetchAllServices = () => {
    const apiClient = useApiClient();
  
    const fetchAllServices = () => {
      return apiClient.get('/services');
    };
  
    return fetchAllServices;
  }

  export const useCreateServices =()=>{
    const apiClient = useApiClient();
  
    const createServices = (data) =>{
      return apiClient.post('/services',data)
    };
  
    return createServices;
  
  };