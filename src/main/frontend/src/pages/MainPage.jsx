import React from "react";
import { ReactComponent as PinIcon } from '../assets/point.svg';
import { ReactComponent as CalIcon } from "../assets/cal.svg";
import "../styles/main.css";
import { Link } from 'react-router-dom';


function MainPage() {
    return (
        <div className="screen">

            <div className="header">
                <div className="header-center">
                    <img src="/logo.png" alt="MEETNY Logo" className="logo-image" />
                </div>
                <div className="header-right">
                    <Link to="/login">
                        <button className="text-button">로그인</button>
                    </Link>
                    <button className="text-button">고객센터</button>
                    <Link to="/register">
                        <button className="signup-button">회원가입</button>
                    </Link>
                </div>
            </div>

            <div className="search-section">
                <div className="search-background"
                     style={{ backgroundImage: `url("/background.png")`}} />
                <div className="search-overlay">
                    <p className="search-title">취미를 함께, 순간을 같이</p>

                    <div className="search-bar">
                        <div className="location-tag">
                            <PinIcon width={20} height={20} />
                            <span>합정동</span>
                        </div>
                        <span className="search-text">나를 기다리는 모임이 있어요!</span>
                    </div>
                </div>
            </div>

            <div className="divider"></div>

            <div className="meet-section">
                <h2 className="section-title">모임을 동행할 사람들 찾기</h2>
                <div className="card-container">
                    <div className="card">
                        <img src="/1.png" alt="meeting Img" className="card-img" />
                        <p className="card-text">관심사가 같은 사람들과</p>
                    </div>
                    <div className="card">
                        <img src="/2.png" alt="meeting Img" className="card-img" />
                        <p className="card-text">언제 어디서든 빠르게 모여</p>
                    </div>
                    <div className="card">
                        <img src="/3.png" alt="meeting Img" className="card-img" />
                        <p className="card-text">삶에 활기를 주는 일상 속 특별함</p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default MainPage;