// src/api/axiosClient.js
import axios from 'axios'

const axiosClient = axios.create({
    baseURL: process.env.REACT_APP_API_BASE_URL,
    headers: { 'Content-Type': 'application/json' },
});

// 인증 제외 URL 목록
const skipAuthUrls = [
    '/api/auth/login',
    '/api/auth/reissue',
    '/api/auth/signup',
    '/api/auth/send-email',
    '/api/auth/verification'
];

axiosClient.interceptors.request.use(config => {
    const token = localStorage.getItem('accessToken');
    // don't attach token on login or token refresh requests
    const skip = skipAuthUrls.some(url => config.url.endsWith(url));
    if (token && !skip) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
});

axiosClient.interceptors.response.use(
  response => response,
  async error => {
    const originalRequest = error.config;
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      const accessToken = localStorage.getItem('accessToken');
      const refreshToken = localStorage.getItem('refreshToken');
      try {
        const { data } = await axiosClient.post('/api/auth/reissue', {
          accessToken,
          refreshToken
        });
        localStorage.setItem('accessToken', data.accessToken);
        localStorage.setItem('refreshToken', data.refreshToken);
        axiosClient.defaults.headers.common['Authorization'] = `Bearer ${data.accessToken}`;
        originalRequest.headers['Authorization'] = `Bearer ${data.accessToken}`;
        return axiosClient(originalRequest);
      } catch (refreshError) {
        // Clear tokens on refresh failure and redirect to login
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        window.location.href = `${window.location.origin}/login`;
        return Promise.reject(refreshError);
      }
    }
    return Promise.reject(error);
  }
);

export default axiosClient