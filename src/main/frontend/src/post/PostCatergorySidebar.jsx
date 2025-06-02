import React, { useState } from "react";
import "../styles/Post.css";

const PostCategorySidebar = () => {
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
    };

    return (
        <div className="category-sidebar">
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
                                    {tag}
                                </label>
                            ))}
                        </div>
                    )}
                </div>
            ))}
        </div>
    );
};

export default PostCategorySidebar;