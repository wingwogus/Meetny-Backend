// src/pages/OAuth2RedirectHandler.jsx
import React, { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

const OAuth2RedirectHandler = () => {
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        const url = new URL(window.location.href);
        const accessToken = url.searchParams.get('accessToken');
        const refreshToken = url.searchParams.get('refreshToken');
        const email = url.searchParams.get('email') || "";

        console.log("✅ accessToken:", accessToken);
        console.log("✅ refreshToken:", refreshToken);
        console.log("✅ email:", email);
        console.log("📍 location.href:", window.location.href);


        if (accessToken && refreshToken) {
            localStorage.setItem('accessToken', accessToken);
            localStorage.setItem('refreshToken', refreshToken);

            // 사용자 정보 입력 필요 여부 확인
            fetch('/api/auth/check-profile', {
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                },
            })
                .then(res => res.json())
                .then(data => {
                    if (data.profileComplete) {
                        navigate('/information');
                    } else {
                        navigate('/social-register');
                    }
                })
                .catch(err => {
                    console.error("❌ check-profile 오류:", err);
                    navigate('/login');
                });
        } else {
            console.error("❌ 토큰 없음, 로그인 페이지로 이동");
            navigate('/login');
        }
    }, [navigate, location]);

    return <div>로그인 처리 중입니다...</div>;
};

export default OAuth2RedirectHandler;