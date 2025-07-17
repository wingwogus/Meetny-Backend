// src/api/axiosClient.js
import axios from 'axios';

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

// ✅ 중복 refresh 요청 방지용 상태
let isRefreshing = false;
let refreshSubscribers = [];

function onRefreshed(token) {
    refreshSubscribers.forEach(cb => cb(token));
    refreshSubscribers = [];
}

function addRefreshSubscriber(callback) {
    refreshSubscribers.push(callback);
}

// 요청 인터셉터
axiosClient.interceptors.request.use(config => {
    const token = localStorage.getItem('accessToken');
    const skip = skipAuthUrls.some(url => config.url.endsWith(url));
    if (token && !skip) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

// 응답 인터셉터
axiosClient.interceptors.response.use(
    response => response,
    async error => {
        const originalRequest = error.config;

        if (error.response?.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;

            if (isRefreshing) {
                return new Promise((resolve) => {
                    addRefreshSubscriber(token => {
                        originalRequest.headers['Authorization'] = `Bearer ${token}`;
                        resolve(axiosClient(originalRequest));
                    });
                });
            }

            isRefreshing = true;

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

                onRefreshed(data.accessToken);
                isRefreshing = false;

                originalRequest.headers['Authorization'] = `Bearer ${data.accessToken}`;
                return axiosClient(originalRequest);
            } catch (refreshError) {
                isRefreshing = false;
                localStorage.removeItem('accessToken');
                localStorage.removeItem('refreshToken');
                window.location.href = '/login';
                return Promise.reject(refreshError);
            }
        }

        return Promise.reject(error);
    }
);

export default axiosClient;