import apiClient from './api_config/apiClient';

export const fetchAllBranchNames=()=>{
    return apiClient.get('/branch/view/all');
}

export const fetchBranchDetailsByName=(data)=>{
    return apiClient.get('/branch/view',{params:{branchName:data}});
}

export const udpateBranch=(branchData,branchName)=>{
    return apiClient.put('/branch',JSON.stringify(branchData),{params:{branchName:branchName}});
}

export const deleteBranch=(data)=>{
    return apiClient.delete('/branch',{params:{branchName:data}});
}

export const addBranch=(data)=>{
    return apiClient.post('/branch',data);
}