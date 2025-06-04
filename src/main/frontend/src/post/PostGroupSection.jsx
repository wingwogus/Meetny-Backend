import React from 'react';
import "../styles/PostGroupSection.css";

const PostGroupSection = ({ title, onMoreClick, children }) => {
    return (
        <div className="group-section">
            <div className="group-section-header">
                <h3>{title}</h3>
                <button className="more-button" onClick={onMoreClick}>더보기</button>
            </div>
            <div className="group-list">
                {children}
            </div>
        </div>
    );
};

export default PostGroupSection;