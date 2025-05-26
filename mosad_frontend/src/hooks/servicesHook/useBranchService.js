import useApiClient from '../apiClientHooks/useApiClient';

export const useFetchAllBranchNames = () => {
  const apiClient = useApiClient();

  const fetchAllBranchNames = () => {
    return apiClient.get('/branch/view/all');
  };

  return fetchAllBranchNames;
};

export const useFetchBranchDetailsByName = () => {
  const apiClient = useApiClient();

  const fetchBranchDetailsByName = (data) => {
    return apiClient.get('/branch/view', { params: { branchName: data } });
  };

  return fetchBranchDetailsByName;
};

export const useUdpateBranch = () => {
  const apiClient = useApiClient();

  const udpateBranch = (branchData, branchName) => {
    return apiClient.put('/branch', JSON.stringify(branchData), { params: { branchName: branchName } });
  };

  return udpateBranch;
};

export const useDeleteBranch = () => {
  const apiClient = useApiClient();

  const deleteBranch = (data) => {
    return apiClient.delete('/branch', { params: { branchName: data } });
  };

  return deleteBranch;
};

export const useAddBranch = () => {
  const apiClient = useApiClient();

  const addBranch = (data) => {
    return apiClient.post('/branch', data);
  };

  return addBranch;
};