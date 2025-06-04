import React from "react";
import logo from "../assets/logo.png";
import "../styles/PostSearchBox.css";
import date from "../assets/Frame.svg";
import location from "../assets/location.svg";
import filter from "../assets/filter.svg";

export default function PostSearchBox() {
    return (
        <div className="post-searchbox">
            <div className="searchbox-wrapper">
            <div className="searchbox-header">
                <div className="searchbox-input-wrapper">
                    <div className="searchbox-placeholder">찾고 싶은 동행을 검색해보세요!</div>
                    <div className="searchbox-location-group">
                        <div className="location-box">
                            <img className="location-icon" alt="Location" src={location} />
                            <div className="location-text">합정동</div>
                        </div>
                    </div>
                </div>

                <div className="searchbox-icon-wrapper">
                    <div className="searchbox-icon">
                        <img className="icon-img" alt="Search Icon" src={date} />
                    </div>
                </div>
            </div>

                <div className="searchbox-toolbar">
                    <div className="searchbox-subtitle">인기 키워드</div>

                    <div className="tag-wrapper">
                        <div className="tag">축구</div>
                        <div className="tag">J-POP</div>
                        <div className="tag">락</div>
                        <div className="tag">발라드</div>
                        <div className="tag">박물관</div>
                    </div>

                    <div className="searchbox-filter-group">
                        <div className="searchbox-filter">필터 설정</div>
                        <img className="dropdown-icon" alt="Vector" src={filter} />
                    </div>
                </div>
        </div>
        </div>
    );
}