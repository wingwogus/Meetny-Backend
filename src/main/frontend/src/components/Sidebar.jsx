import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Sidebar() {
    const [isOpen, setIsOpen] = useState(false);
    const navigate = useNavigate();

    const toggleSidebar = () => setIsOpen(!isOpen);

    return (
        <>
            {/* 햄버거 버튼 (모바일용) */}
            <div className="hamburger-button" onClick={toggleSidebar}>
                &#9776;
            </div>

            {/* 사이드바 본체 */}
            <div className={`mypage-sidebar ${isOpen ? "open" : ""}`}>
                {/* 메뉴 라벨 */}
                <div className="mypage-nav-travel-management">동행 관리</div>
                <div className="mypage-nav-home">마이페이지</div>
                <div className="mypage-nav-my-posts">내 게시글</div>
                <div className="mypage-nav-home-duplicate">마이페이지</div>
                <div className="mypage-nav-followers-following">팔로워/팔로잉</div>
                <div className="mypage-nav-liked-posts">좋아요 한 게시물</div>
                <div className="mypage-nav-reviews">동행 후기</div>
                <div className="mypage-nav-settings">설정</div>

                {/* 구분선 */}
                <div className="mypage-sidebar-divider" />

                {/* 프로필 관리 버튼 */}
                <div className="mypage-profile-manage-btn">
                    <div className="mypage-profile-manage-text">프로필 관리</div>
                </div>

                {/* 채팅 버튼 */}
                <div className="mypage-chat-btn" onClick={() => navigate("/chat")}>
                    <div className="mypage-chat-icon" />
                    <div className="mypage-chat-badge" />
                </div>
            </div>
        </>
    );
}