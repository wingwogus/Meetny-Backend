import React from 'react';
import logoImg from '../assets/logo-t.png';
import '../styles/AppHeader.css';

export default function AppHeader() {
    return (
        <header className="app-header">
            {/* 왼쪽 로고 */}
            <div className="app-header__logo">
                <img src={logoImg} alt="Meetny Logo" />
            </div>

            {/* 우측 내비게이션 아이콘 */}
            <nav className="app-header__nav">
                <div className="icon icon--chat" />
                <div className="icon icon--bell" />
                <div className="icon icon--heart" />
            </nav>

            {/* 우측 사용자 아바타 */}
            <img
                src={localStorage.getItem("profileImg")}      // 실제 유저 아바타 경로로 교체
                alt="User Avatar"
                className="app-header__avatar"
            />
        </header>
    );
}