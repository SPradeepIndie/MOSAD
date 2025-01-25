import apiClient from './api_config/apiClient';

export const fetchAllBranchNames=()=>{
    return apiClient.get('/branch/view/all');
}

export const fetchBranchDetailsByName=(data)=>{
    return apiClient.get('/branch/view',{params:{branchName:data}});
}

export const udpateBranch=(data)=>{
    return apiClient.post('/branch/update',JSON.stringify(data));
}

export const deleteBranch=(data)=>{
    return apiClient.post('/branch/delete',JSON.stringify(data));
}

export const addBranch=(data)=>{
    return apiClient.post('/branch/create',JSON.stringify(data));
}