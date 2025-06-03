import React from "react";
import "../styles/review.css";

const CompanionCard = ({ name, imageSrc }) => {
    return (
        <div className="companion-card">
            <img className="companion-img" src={imageSrc || "/logo.png"} alt={name} />
            <div className="companion-name">{name}</div>
        </div>
    );
};

export default CompanionCard;