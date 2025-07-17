import React from "react";

export default function InterestSection({ likedPosts, formatDate }) {
    if (!likedPosts || likedPosts.length === 0) {
        return (
            <div className="mypage-interest-card">
                <div className="mypage-interest-empty">관심 동행이 없습니다.</div>
            </div>
        );
    }

    return (
        <>

            {likedPosts.map((post) => (
                <div key={post.id} className="mypage-interest-card">
                    {/* 썸네일 */}
                    <div className="mypage-review-thumb-wrapper-2">
                        <img
                            className="mypage-review-thumb-img-2"
                            alt={post.title}
                            src={post.photo || "https://via.placeholder.com/114x110?text=No+Photo"}
                        />
                        <div className="mypage-review-thumb-overlay-2" />
                        <div className="mypage-thumb-overlay-text-2">
                            종료된<br />동행
                        </div>
                    </div>

                    {/* 제목 */}
                    <div className="mypage-review-title-2">{post.title}</div>

                    {/* 내용 */}
                    <p className="mypage-review-snippet-2">{post.content}</p>

                    {/* 날짜 */}
                    <div className="mypage-review-date-2">{formatDate(post.createdAt)}</div>
                </div>
            ))}
        </>
    );
}