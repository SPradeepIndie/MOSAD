import axios from 'axios';
const BASE_URL= 'http://localhost:8080/api/v1';

export const apiClient = axios.create({
    baseURL:BASE_URL,
    headers: {
      'Content-Type': 'application/json',
    },
});

export const privateApiClient = axios.create({
  baseURL:BASE_URL,
  withCredentials:true,
  headers: {
    'Content-Type': 'application/json',
  },
});





