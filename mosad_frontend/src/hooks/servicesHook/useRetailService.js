import useApiClient from "../apiClientHooks/useApiClient";

export const useFetchPaymentHistory = () => {
    const apiClient=useApiClient();

    const fetchPaymentHistory=(data)=>{
        return apiClient.get('/retail/paymentHistory',{params:{username:data}});
    }

    return fetchPaymentHistory;
};

export const useFetchPurchaseHistory = () => {
    const apiClient=useApiClient();

    const fetchPurchaseHistory=(data)=>{
        return apiClient.get('/retail/purchaseHistory',{params:{username:data}});
    }
    return fetchPurchaseHistory;
};

export const useFetchIncompleteTransactions = () => {
    const apiClient=useApiClient();
    
    const fetchIncompleteTransactions=(data)=>{
        return apiClient.get('/retail/incompleteTransaction',{params:{username:data}});
    }
    return fetchIncompleteTransactions;
};