import React from "react";

const SearchBar = () => {
    return (
        <div className="search-bar">
            <div className="location-select">📍 합정동</div>
            <input className="search-input" placeholder="찾고 싶은 모임을 검색해보세요!" />
            <div className="popular-keywords">
                <span>독서모임</span>
                <span>J-POP</span>
                <span>러닝크루</span>
                {/* ...etc */}
            </div>
        </div>
    );
};

export default SearchBar;