import React from "react";

const GroupCard = ({ title, description, date, place, memberCount, image }) => {
    return (
        <div className="group-card">
            <img src={image} alt="모임 이미지" />
            <h4>{title}</h4>
            <p>{description}</p>
            <div className="group-meta">
                <span>{place}</span> · <span>{date}</span> · <span>{memberCount}</span>
            </div>
        </div>
    );
};

export default GroupCard;