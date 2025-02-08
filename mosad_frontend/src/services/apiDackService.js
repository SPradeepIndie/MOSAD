import apiClient from 'api_config/apiClient';


export const fetchRebuildTyres = () => {
    return apiClient.get('rebuild-tyres');
};


export const fetchRebuildTyresByContact = (contactNumber) => {
    return apiClient.get(`rebuild-tyres/contact/${contactNumber}`);
};


export const createRebuildTyre = (tyreData) => {
    return apiClient.post('rebuild-tyres', tyreData);
};


export const updateRebuildTyre = (id, tyreData) => {
    return apiClient.put(`rebuild-tyres/${id}`, tyreData);
};


export const deleteRebuildTyre = (id) => {
    return apiClient.delete(`rebuild-tyres/${id}`);
};
