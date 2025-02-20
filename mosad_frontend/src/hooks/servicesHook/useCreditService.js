import useApiClient from '../apiClientHooks/useApiClient';

export const useFetchAllCreditDetails = () => {
    const apiClient = useApiClient();

    const fetchAllCreditDetails = (customerType) => {
        return apiClient.get(`/credit/all-credit-details/${customerType}`);
    };

    return fetchAllCreditDetails;
};

export const useAddRepayment = () => {
    const apiClient = useApiClient();

    const addRepayment = (creditId, repayment) => {
        return apiClient.post("/credit/add-repayment", {
            creditId,
            ...repayment,
        });
    };

    return addRepayment;
};

export const useDeleteRepayment = () => {
    const apiClient = useApiClient();

    const deleteRepayment = (repaymentId) => {
        return apiClient.delete("/credit/delete-repayment", {params: {repaymentId}});
    }

    return deleteRepayment;
};

export const useUpdateRepayment = () => {
    const apiClient = useApiClient();

    const updateRepayment = (repaymentUpdate) => {
        return apiClient.put("/credit/update-repayment", {repaymentUpdate});
    }

    return updateRepayment;
};