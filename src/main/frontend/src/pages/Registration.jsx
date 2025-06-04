// src/pages/RegistrationFlow.jsx
import React, { useState } from "react";
import RegisterHeader from "../components/RegisterHeader";
import RegisterStep1 from "../components/RegisterStep1";
import "../styles/RegistrationFlow.css";

const RegistrationFlow = () => {
    // 1 → 약관동의, 2 → 회원정보입력, 3 → 태그선택
    const [currentStep, setCurrentStep] = useState(1);

    const goToNextStep = () => {
        if (currentStep < 3) {
            setCurrentStep((prev) => prev + 1);
        }
    };

    return (
        <div className="registration-flow-wrapper">
            <RegisterHeader currentStep={currentStep} />
            {currentStep === 1 && (
                <RegisterStep1 currentStep={currentStep} onNext={goToNextStep} />
            )}
        </div>
    );
};

export default RegistrationFlow;