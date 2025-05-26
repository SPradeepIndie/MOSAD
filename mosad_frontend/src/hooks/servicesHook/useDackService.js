import useApiClient from '../apiClientHooks/useApiClient';

export const useFetchRebuildTyres = () => {
    const apiClient = useApiClient();

    const fetchRebuildTyres = () => {
        return apiClient.get('/rebuild-tyres');
    };

    return fetchRebuildTyres;
};

export const useFetchRebuildTyresByContact = () => {
    const apiClient = useApiClient();

    const fetchRebuildTyresByContact = (contactNumber) => {
        return apiClient.get(`/rebuild-tyres/contact/${contactNumber}`);
    };

    return fetchRebuildTyresByContact;
};

export const useCreateRebuildTyre = () => {
    const apiClient = useApiClient();

    const createRebuildTyre = (tyreData) => {
        return apiClient.post('/rebuild-tyres', tyreData);
    };

    return createRebuildTyre;
};

export const useUpdateRebuildTyre = () => {
    const apiClient = useApiClient();

    const updateRebuildTyre = (id, tyreData) => {
        return apiClient.put(`/rebuild-tyres/${id}`, tyreData);
    };

    return updateRebuildTyre;
};

export const useDeleteRebuildTyre = () => {
    const apiClient = useApiClient();

    const deleteRebuildTyre = (id) => {
        return apiClient.delete(`/rebuild-tyres/${id}`);
    };

    return deleteRebuildTyre;
};