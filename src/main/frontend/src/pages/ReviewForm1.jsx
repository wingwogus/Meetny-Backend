import React from "react";
import line3 from "./line-3.svg";
import rectangle170 from "./rectangle-170.png";
import rectangle192 from "./rectangle-192.png";
import vector3 from "./vector-3.svg";
import vector32 from "./vector-3-2.svg";
import vector33 from "./vector-3-3.svg";
import vector4 from "./vector-4.svg";
import vector42 from "./vector-4-2.svg";
import vector43 from "./vector-4-3.svg";
import vector5 from "./vector-5.svg";
import vector52 from "./vector-5-2.svg";
import vector53 from "./vector-5-3.svg";
import "./style.css";

const Tag = ({ label, icon, top, left }) => (
    <div className="tag-container" style={{ top, left }}>
        <div className="tag-icon active">
            <img className="vector" alt="icon" src={icon} />
        </div>
        <span className="sub-label">{label}</span>
    </div>
);

export const ReviewPage = () => {
    const tags = [
        { label: "동행 시간 약속을 잘 지켜요.", icon: vector3, top: "691px", left: "197px" },
        { label: "동행 시간 약속을 잘 지켜요.", icon: vector32, top: "691px", left: "463px" },
        { label: "동행 시간 약속을 잘 지켜요.", icon: vector33, top: "691px", left: "728px" },
        { label: "친절하고 매너가 좋아요.", icon: vector4, top: "742px", left: "197px" },
        { label: "친절하고 매너가 좋아요.", icon: vector42, top: "742px", left: "463px" },
        { label: "친절하고 매너가 좋아요.", icon: vector43, top: "742px", left: "728px" },
        { label: "동행 채팅 응답이 빨라요", icon: vector5, top: "793px", left: "197px" },
        { label: "동행 채팅 응답이 빨라요", icon: vector52, top: "793px", left: "463px" },
        { label: "동행 채팅 응답이 빨라요", icon: vector53, top: "793px", left: "728px" },
    ];

    return (
        <div className="screen">
            <div className="div">
                <div className="review-box" />
                <div className="section-header" style={{ top: "860px", left: "109px" }}>동행 후기</div>
                <div className="section-subtext" style={{ top: "922px", left: "133px" }}>동행에 대한 후기를 입력해 주세요</div>

                <img className="line" alt="line" src={line3} style={{ position: "absolute", top: "364px", left: "109px" }} />
                <img className="img" alt="meeting" src={rectangle192} style={{ position: "absolute", top: "122px", left: "375px" }} />

                <div className="label" style={{ position: "absolute", top: "127px", left: "759px" }}>
                    💪축구/풋살 모임 : 풋살즈
                </div>

                <div className="sub-label" style={{ position: "absolute", top: "217px", left: "786px" }}>연남동</div>
                <div className="sub-label" style={{ position: "absolute", top: "256px", left: "785px" }}>25.03.18</div>

                <img className="rectangle-5" alt="user-profile" src={rectangle170} style={{ position: "absolute", top: "126px", left: "157px" }} />
                <div className="label" style={{ position: "absolute", top: "274px", left: "188px" }}>강민석</div>
                <p className="sub-label" style={{ position: "absolute", top: "176px", left: "759px" }}>
                    적당한 거리의 축구모임 : 다함께 ...
                </p>

                <div className="section-header" style={{ top: "424px", left: "109px" }}>강민석님과의 동행은 어떠셨나요?</div>
                <div className="section-header" style={{ top: "619px", left: "109px" }}>강민석님의 어떤 점이 좋으셨나요?</div>

                {tags.map((tag, idx) => (
                    <Tag key={idx} {...tag} />
                ))}

                <div className="review-button">후기 보내기</div>
            </div>
        </div>
    );
};
