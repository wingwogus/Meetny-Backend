import React from "react";
import { ReactComponent as PinIcon } from '../assets/point.svg';
import { ReactComponent as TimeIcon } from '../assets/time.svg';
import "../styles/review.css";

const MyActivityCard = ({ title, description, location, date, imageSrc }) => {
    return (
        <div className="activity-box">
            <img className="activity-img" src={imageSrc || "/logo.png"} alt={title} />
            <div className="activity-info">
                <div className="activity-title">{title}</div>
                <div className="activity-desc">{description}</div>
                <div className="activity-meta">
                    <div className="icon-text">
                        <PinIcon width={20} height={20} />
                        <span>{location}</span>
                    </div>
                    <div className="icon-text">
                        <TimeIcon width={20} height={20} />
                        <span>{date}</span>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default MyActivityCard;