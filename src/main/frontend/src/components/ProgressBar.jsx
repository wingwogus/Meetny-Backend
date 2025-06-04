// src/components/ProgressBar.jsx
import React from "react";
import "../styles/ProgressBar.css";

const ProgressBar = ({ currentStep }) => {
    // 총 스텝 개수 → 3
    // currentStep (1, 2, 3)에 따라 비율 계산
    const percent =
        currentStep === 1 ? 33
            : currentStep === 2 ? 66
                : 100;

    return (
        <div className="progressbar-container">
            {/* 배경 바 */}
            <div className="progressbar-bg" />
            {/* 채워진 바 */}
            <div
                className="progressbar-filled"
                style={{ width: `${percent}%` }}
            />
            {/* 동그란 인디케이터(선택) */}
            <div
                className="progressbar-indicator"
                style={{ left: `calc(${percent}% - 8px)` }}
            >
                <div className="progressbar-indicator-dot" />
            </div>
        </div>
    );
};

export default ProgressBar;