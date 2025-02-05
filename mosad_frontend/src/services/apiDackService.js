import apiClient from './api_config/apiClient';

// Fetch all rebuild tyre data
export const fetchRebuildTyres = () => {
    return apiClient.get('/api/rebuild-tyres');
};

// Fetch rebuild tyre by ID (if needed)
export const fetchRebuildTyreById = (id) => {
    return apiClient.get(`/api/rebuild-tyres/${id}`);
};

// Fetch rebuild tyres by status
export const fetchRebuildTyresByStatus = (status) => {
    return apiClient.get(`/api/rebuild-tyres/status/${status}`);
};