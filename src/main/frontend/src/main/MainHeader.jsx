import React from 'react';
import './styles/MainHeader.css';


export default function Header() {
    return (
        <header className="header">
            <div className="logo">MEETNY</div>
            <nav>
                <a href="#">로그인</a>
                <a href="#">고객센터</a>
                <button className="signup-btn">회원가입</button>
            </nav>
        </header>
    );
}