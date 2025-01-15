import apiClient from './api_config/apiClient';

export const fetchPaymentHistory = (username, id) => {
    return apiClient.get('/retail/paymentHistory',{params:{username:username,billId:id}});
};

export const fetchPurchaseHistory = (username) => {
    return apiClient.get('/retail/purchaseHistory',{params:{username:username}});
};

export const fetchIncompleteTransactions = (username) => {
    return apiClient.get('/retail/incompleteTransaction',{params:{username:username}});
};