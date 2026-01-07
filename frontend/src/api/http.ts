import axios, { AxiosError } from 'axios';

export const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 30000,
});

// Add response interceptor for better error handling
api.interceptors.response.use(
  (response) => response,
  (error: AxiosError) => {
    if (error.response) {
      // Server responded with error status
      const status = error.response.status;
      const data = error.response.data as any;
      
      if (status === 404) {
        console.error('404 Error - Endpoint not found:', {
          url: error.config?.url,
          method: error.config?.method,
          responseData: data
        });
      }
    } else if (error.request) {
      // Request made but no response received
      console.error('Network Error - No response received:', {
        url: error.config?.url,
        message: 'Backend server may not be running on http://localhost:8080'
      });
    }
    
    return Promise.reject(error);
  }
);
