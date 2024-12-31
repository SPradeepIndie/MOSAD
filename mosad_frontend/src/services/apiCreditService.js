import apiClient from "./api_config/apiClient";


export const fetchAllCreditDetails = () => {
    return apiClient.get("/credit/all-credit-details");
};

export const addRepayment = (creditId, repayment) => {
  return apiClient.post("/credit/add-repayment", {
    creditId,
    ...repayment,
  });
};
