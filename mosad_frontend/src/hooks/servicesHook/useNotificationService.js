import useApiClient from '../apiClientHooks/useApiClient';

export const useFetchNotificationsByType = () => {
    const apiClient = useApiClient();
  
    const fetchNotificationsByType = (type) => {
      return apiClient.get(`/notifications/${type}`);
    };
  
    return fetchNotificationsByType;
  };
  
export const useAddRestockRequest = () => {
  const apiClient = useApiClient();

  const sendRestockRequest = (data) => {
    return apiClient.post(`/notifications/add`,data);
  };

  return sendRestockRequest;
};
