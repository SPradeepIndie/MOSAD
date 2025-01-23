import apiClient from './api_config/apiClient';

export const fetchPaymentHistory = (data) => {
    return apiClient.get('/retail/paymentHistory',{params:{username:data}});
};

export const fetchPurchaseHistory = (data) => {
    return apiClient.get('/retail/purchaseHistory',{params:{username:data}});
};

export const fetchIncompleteTransactions = (data) => {
    return apiClient.get('/retail/incompleteTransaction',{params:{username:data}});
};