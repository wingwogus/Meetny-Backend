import React from "react";

const MyKeywordGroupSection = () => {
    return (
        <section className="my-keyword-section">
            <h2>내 키워드 모임</h2>
            {/* 추후 동적 렌더링 가능 */}
            <div>독서시티: 독서습관 만들기</div>
            <div>달빛한강: 한강 요가 모임</div>
            <div>애니 덕후 집합: 애니메이션 감상회</div>
        </section>
    );
};

export default MyKeywordGroupSection;