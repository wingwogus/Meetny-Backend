import React from 'react';
import './HeroSection.css';

export default function HeroSection() {
    return (
        <section className="hero">
            <div className="overlay">
                <h1>취미를 함께, 순간을 같이</h1>
                <div className="search-bar">
                    <span className="location">📍 합정동</span>
                    <input type="text" placeholder="나를 기다리는 모임이 있어요!" />
                    <button className="calendar-btn">📅</button>
                </div>
                <div className="tags">
                    <span>러닝</span>
                    <span>독서</span>
                    <span>러닝</span>
                    <span>독서</span>
                    <span>러닝</span>
                </div>
            </div>
        </section>
    );
}