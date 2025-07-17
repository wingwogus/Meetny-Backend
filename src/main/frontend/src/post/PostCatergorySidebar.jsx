import React, { useState } from "react";
import "../styles/PostCategorySidebar.css";
import "../components/AppHeader";

const PostCategorySidebar = ({onTagSelect}) => {
    const categoryData = {
        콘서트: ["J-POP", "락", "발라드", "힙합", "K-POP", "EDM", "POP", "트로트", "JAZZ", "코미디 쇼"],
        문화: ["박물관", "미술관", "팝업", "박람회", "콘테스트", "전시회", "뮤지컬", "연극"],
        스포츠: ["축구", "야구", "농구", "배구"],
        영화: ["액션", "로맨스", "코미디", "스릴러/호러", "판타지/SF", "다큐멘터리", "애니메이션", "뮤지컬", "드라마"]
    };

    const [openCategory, setOpenCategory] = useState(null);
    const [selectedTags, setSelectedTags] = useState({});

    const toggleCategory = (category) => {
        setOpenCategory((prev) => (prev === category ? null : category));
    };

    const selectTag = (category, tag) => {
        setSelectedTags((prev) => ({
            ...prev,
            [category]: tag
        }));
        onTagSelect({category, tagName:tag});
    };

    return (
        <>

        <div className="category-sidebar">
            {/* 전체보기 버튼 */}
            <div className="accordion-section">
                <button className="radio-reset-btn" onClick={() => onTagSelect(null)}>
                    전체 모임 보기
                </button>
            </div>

            <div className="sidebar-header">
                <div className="category-title">카테고리</div>
                <hr className="category-divider" />

            </div>


            {Object.entries(categoryData).map(([category, tags]) => (
                <div key={category} className="accordion-section">
                    <div className="accordion-title" onClick={() => toggleCategory(category)}>
                        {category}
                    </div>

                    {openCategory === category && (
                        <div className="accordion-content">
                            {tags.map((tag) => (
                                <label key={tag} className="radio-label">
                                    <input
                                        type="radio"
                                        name={category}
                                        checked={selectedTags[category] === tag}
                                        onChange={() => selectTag(category, tag)}
                                    />
                                    <span>{tag}</span>
                                </label>
                            ))}
                        </div>
                    )}
                </div>
            ))}


        </div>
        </>
    );
};

export default PostCategorySidebar;