// src/pages/Step1_Terms.jsx
import React, { useState } from "react";
import "../styles/RegisterStep1.css";

const RegisterStep1 = ({ currentStep, onNext }) => {
    const [allAgree, setAllAgree] = useState(false);
    const [agree1, setAgree1] = useState(false);
    const [agree2, setAgree2] = useState(false);
    const [agree3, setAgree3] = useState(false);
    const [agree4, setAgree4] = useState(false);

    const handleAllAgree = () => {
        const next = !allAgree;
        setAllAgree(next);
        setAgree1(next);
        setAgree2(next);
        setAgree3(next);
        setAgree4(next);
    };

    const handleSingleChange = (setter, value) => {
        setter(value);
        setAllAgree(
            setter === setAgree1
                ? value && agree2 && agree3 && agree4
                : setter === setAgree2
                    ? agree1 && value && agree3 && agree4
                    : setter === setAgree3
                        ? agree1 && agree2 && value && agree4
                        : agree1 && agree2 && agree3 && value
        );
    };

    return (
        <div className="step1-container">

            {/* 실제 약관동의 UI (절대 위치로 Figma와 동일하게 배치) */}
            <div className="step1-body">
                <div className="step1-title">
                    이용약관 동의 <span className="required-star">*</span>
                </div>
                <hr className="step1-divider" />

                <div className="step1-all-agree">
                    <label>
                        <input
                            type="checkbox"
                            checked={allAgree}
                            onChange={handleAllAgree}
                        />
                        필수 및 선택 약관에 모두 동의합니다.
                    </label>
                </div>

                <div className="step1-individual">
                    <div className="step1-item">
                        <label>
                            <input
                                type="checkbox"
                                checked={agree1}
                                onChange={(e) =>
                                    handleSingleChange(setAgree1, e.target.checked)
                                }
                            />
                            이용약관 동의 <span className="item-required">(필수)</span>
                        </label>
                        <button className="step1-view-btn">보기 &gt;</button>
                    </div>
                    <div className="step1-item">
                        <label>
                            <input
                                type="checkbox"
                                checked={agree2}
                                onChange={(e) =>
                                    handleSingleChange(setAgree2, e.target.checked)
                                }
                            />
                            개인정보 수집·이용 동의 <span className="item-required">(필수)</span>
                        </label>
                        <button className="step1-view-btn">보기 &gt;</button>
                    </div>
                    <div className="step1-item">
                        <label>
                            <input
                                type="checkbox"
                                checked={agree3}
                                onChange={(e) =>
                                    handleSingleChange(setAgree3, e.target.checked)
                                }
                            />
                            개인정보 수집·이용 동의 <span className="item-required">(선택)</span>
                        </label>
                        <button className="step1-view-btn">보기 &gt;</button>
                    </div>
                    <div className="step1-item">
                        <label>
                            <input
                                type="checkbox"
                                checked={agree4}
                                onChange={(e) =>
                                    handleSingleChange(setAgree4, e.target.checked)
                                }
                            />
                            본인은 만 14세 이상입니다. <span className="item-required">(필수)</span>
                        </label>
                        <button className="step1-view-btn">보기 &gt;</button>
                    </div>
                </div>

                <div className="step1-next-btn-wrapper">
                    <button
                        className={`step1-next-btn ${
                            agree1 && agree2 && agree4 ? "enabled" : "disabled"
                        }`}
                        onClick={() => {
                            if (agree1 && agree2 && agree4) {
                                onNext();
                            } else {
                                alert("필수 약관에 모두 동의해주세요.");
                            }
                        }}
                        disabled={!(agree1 && agree2 && agree4)}
                    >
                        다음으로
                    </button>
                </div>
            </div>
        </div>
    );
};

export default RegisterStep1;