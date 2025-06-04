// Header.jsx
import React from 'react';
import logo from '../assets/logo.png';
import '../styles/PostCategorySidebar.css'; // CSS 안에 header 관련 스타일 포함됨

const PostHeader = () => {
    return (
        <header className="header">
            <div className="header__logo">
                <img src={logo} alt="MEETNY 로고" />
            </div>
            <div className="header__actions">
                <button className="header__button">로그인</button>
                <button className="header__button">고객센터</button>
                <button className="header__button header__button--primary">회원가입</button>
            </div>
        </header>
    );
};

export default PostHeader;