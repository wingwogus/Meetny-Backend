// src/components/RegisterHeader.jsx
import React from "react";
import ProgressBar from "./ProgressBar.jsx";
import "../styles/RegisterHeader.css";

import LogoText from "../assets/group-text.png";
import LogoIcon from "../assets/group-icon.png";

const RegisterHeader = ({ currentStep }) => {
    return (
        <div className="register-header-container">
            {/* 로고 섹션 */}
            <div className="register-logo-section">
                <img className="register-logo-icon" src={LogoIcon} alt="로고 아이콘" />
                <img className="register-logo-text" src={LogoText} alt="MEETNY" />
            </div>

            {/* 프로그레스바 섹션 */}
            <div className="register-progressbar-wrapper">
                <ProgressBar currentStep={currentStep} />
            </div>
        </div>
    );
};

export default RegisterHeader;