import React, { useState } from "react";
import Header from "../components/AppHeader";
import PostCategorySidebar from "../post/PostCatergorySidebar";
import PostSearch from "../post/PostSearchBox";
import PostListContainer from "../post/PostListContainer";
import "../styles/PostMain.css"


const PostMain = () => {
    // 카테고리 및 태그 선택 상태
    const [selectedCategory, setSelectedCategory] = useState(null);
    const [selectedTag, setSelectedTag] = useState(null);

    return (
        <div className="post-main-container">
            {/* 헤더 */}
            <Header />
            <div className="post-searchbox">
                <PostSearch />
            </div>

            <div className="post-body">
                {/* 사이드바 */}
                <PostCategorySidebar
                    selectedCategory={selectedCategory}
                    setSelectedCategory={setSelectedCategory}
                    selectedTag={selectedTag}
                    setSelectedTag={setSelectedTag}
                    onTagSelect={setSelectedTag}
                />
                <div className="post-content-sections">
                    <PostListContainer
                        selectedCategory={selectedCategory}
                        selectedTag={selectedTag}
                    />
                </div>

            </div>
        </div>
    );
};

export default PostMain;