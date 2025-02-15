import useApiClient from '../apiClientHooks/useApiClient'

export const useGetUserDetailsByUsername = () => {
    const apiClient=useApiClient();

    const getUserDetails = (data) => { 
        return apiClient.get('/user/view',data);
       
    };
    return getUserDetails
};

export const useLogin = () => {
  const apiClient = useApiClient();

  const login = (data) => {
    return apiClient.post('/login', JSON.stringify(data));
  };

  return login;
};

export const useRegister = () => {
  const apiClient = useApiClient();

  const register = (data) => {
    return apiClient.post('/user/register', JSON.stringify(data));
  };

  return register;
};

export const useUpdateUserDetails = () => {
  const apiClient = useApiClient();

  const updateUserDetails = (data, param) => {
    return apiClient.put('/user/update', data, param);
  };

  return updateUserDetails;
};

export const useDeleteUser = () => {
  const apiClient = useApiClient();

  const deleteUser = (data) => {
    return apiClient.delete('/user/delete', data);
  };

  return deleteUser;
};

export const useGetAllUsername = () => {
  const apiClient = useApiClient();

  const getAllUsername = () => {
    return apiClient.get('/user/view/all');
  };

  return getAllUsername;
};

export const useLogout = () => {
  const apiClient = useApiClient();

  const logout = (data) => {
    return apiClient.post('/logout', data);
  };

  return logout;
};

export const useFgtPwdMailCheckAndOtpSend = () => {
  const apiClient = useApiClient();

  const fgtPwdMailCheckAndOtpSend = (emailData) => {
    return apiClient.get('/user/forgot-pwd/email', { params: { email: emailData } });
  };

  return fgtPwdMailCheckAndOtpSend;
};

export const useVerifyOtp = () => {
  const apiClient = useApiClient();

  const verifyOtp = (otpData, emailData) => {
    return apiClient.get('/user/forgot-pwd/otp', { params: { otp: otpData, email: emailData } });
  };

  return verifyOtp;
};

export const useResendOtp = () => {
  const apiClient = useApiClient();

  const resendOtp = (emailData) => {
    return apiClient.get('/user/forgot-pwd/otp/resend', { params: { email: emailData } });
  };

  return resendOtp;
};

export const useChangePassword = () => {
  const apiClient = useApiClient();

  const changePwd = (emailData, data) => {
    return apiClient.post('/user/forgot-pwd/change', data, { params: { email: emailData } });
  };

  return changePwd;
};

