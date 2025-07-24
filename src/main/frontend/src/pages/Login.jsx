// src/components/LoginForm.jsx
import React, { useState } from 'react';
import axios from 'axios';

const LoginForm = () => {
    const [credentials, setCredentials] = useState({
        username: '',
        password: ''
    });
    const [statusMsg, setStatusMsg] = useState('');

    // 입력값이 바뀔 때마다 credentials 상태 업데이트
    const handleChange = (e) => {
        const { name, value } = e.target;
        setCredentials(prev => ({ ...prev, [name]: value }));
    };

    // 폼 제출 시 로그인 API 호출
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const res = await axios.post('/api/login', credentials);
            // 예: res.data에 { accessToken, refreshToken, userInfo } 형태로 온다고 가정
            console.log('로그인 성공:', res.data);
            setStatusMsg('로그인 성공! 환영합니다.');
            // TODO: 토큰 저장(localStorage/cookie) 또는 리다이렉트 처리
        } catch (err) {
            console.error('로그인 오류:', err);
            setStatusMsg(
                err.response?.data?.message
                || '로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.'
            );
        }
    };

    return (
        <div style={{ maxWidth: 400, margin: '2rem auto' }}>
            <h2>로그인</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    아이디
                    <input
                        type="text"
                        name="username"
                        value={credentials.username}
                        onChange={handleChange}
                        required
                    />
                </label>
                <label>
                    비밀번호
                    <input
                        type="password"
                        name="password"
                        value={credentials.password}
                        onChange={handleChange}
                        required
                    />
                </label>
                <button type="submit">로그인</button>
            </form>
            {statusMsg && <p>{statusMsg}</p>}
        </div>
    );
};

export default LoginForm;