import React from "react";

export default function TabSelector({ activeTab, setActiveTab }) {
    return (
        <>
            <div
                className={`mypage-tab-title-posts ${activeTab === "posts" ? "active" : ""}`}
                onClick={() => setActiveTab("posts")}
            >
                동행 게시글
            </div>

            <div
                className={`mypage-tab-title-participated ${activeTab === "participated" ? "active" : ""}`}
                onClick={() => setActiveTab("participated")}
            >
                참가한 동행
            </div>

            <div
                className={`mypage-tab-title-feed ${activeTab === "feed" ? "active" : ""}`}
                onClick={() => setActiveTab("feed")}
            >
                동행 후기
            </div>
        </>
    );
}