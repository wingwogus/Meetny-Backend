import React from "react";

export default function ReceivedReviewSection({ receivedReviews, formatDateSlash }) {
    return (
        <div className="mypage-review-list-wrapper">
            <div className="mypage-review-list-heading">받은 동행 후기</div>

            {receivedReviews.length > 0 ? (
                <div className="mypage-review-list">
                    {receivedReviews.map((rev, idx) => (
                        <div key={idx} className="mypage-received-review-card">
                            {/* 썸네일 */}
                            <div className="mypage-review-thumb-wrapper">
                                {rev.photo ? (
                                    <img
                                        className="mypage-review-thumb-img"
                                        alt="Review Thumbnail"
                                        src={rev.photo}
                                    />
                                ) : (
                                    <div className="mypage-review-thumb-placeholder" />
                                )}
                                <div className="mypage-review-thumb-overlay" />
                                <div className="mypage-thumb-overlay-text">
                                    종료된<br />동행
                                </div>
                            </div>

                            {/* 제목 */}
                            <div className="mypage-review-title">{rev.postTitle}</div>

                            {/* 댓글 */}
                            <p className="mypage-review-snippet">“{rev.comment}”</p>

                            {/* 날짜 */}
                            <div className="mypage-review-date">{formatDateSlash(rev.meetingTime)}</div>

                            {/* 작성자 닉네임 */}
                            <div className="mypage-reviewer-nickname">{rev.reviewerNickname}</div>
                        </div>
                    ))}
                </div>
            ) : (
                <div className="mypage-received-review-card-empty">
                    동행 후기 없음
                </div>
            )}
        </div>
    );
}