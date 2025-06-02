import React from 'react';
import './styles/Features.css';

export default function Features() {
    return (
        <section className="features">
            <h2>모임을 동행할 사람들 찾기</h2>
            <div className="cards">
                <div className="card">
                    <img src="/feature1.png" alt="관심사" />
                    <p>관심사가 같은 사람들과</p>
                </div>
                <div className="card">
                    <img src="/feature2.png" alt="빠르게 모여" />
                    <p>언제 어디서든 빠르게 모여</p>
                </div>
                <div className="card">
                    <img src="/feature3.png" alt="삶의 활기" />
                    <p>삶에 활기를 주는 일상 속 특별함</p>
                </div>
            </div>
        </section>
    );
}