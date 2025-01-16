import apiClient from "./api_config/apiClient";


export const fetchAllCreditDetails = (customerType) => {
    return apiClient.get(`/credit/all-credit-details/${customerType}`);
};

export const addRepayment = (creditId, repayment) => {
  return apiClient.post("/credit/add-repayment", {
    creditId,
    ...repayment,
  });
};
