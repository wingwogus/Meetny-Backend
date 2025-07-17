import React from "react";
import { useNavigate } from "react-router-dom";

export default function ParticipatedTabSection({ participatedPosts, formatDate }) {
    const navigate = useNavigate();
    const sliced = participatedPosts.slice(0, 4);

    return (
        <div className="mypage-participated-section">
            {sliced.length === 0 ? (
                <div className="mypage-participated-placeholder">
                    참여한 동행 데이터가 없습니다.
                </div>
            ) : (
                <>
                    {sliced.map((post, i) => (
                        <React.Fragment key={post.id}>
                            {/* 썸네일 */}
                            <img
                                className={`mypage-card-thumb-${i + 1}`}
                                alt={`참여 동행 ${i + 1}`}
                                src={
                                    post.photo ||
                                    "https://via.placeholder.com/308x246?text=No+Photo"
                                }
                                onClick={() => navigate(`/posts/${post.id}`)}
                            />

                            {/* 제목 */}
                            <p
                                className={`mypage-card-title-${i + 1}`}
                                onClick={() => navigate(`/posts/${post.id}`)}
                            >
                                {post.title}
                            </p>

                            {/* 위치 */}
                            <div className={`mypage-card-location-${i + 1}`}>
                                {post.address?.town || ""}
                            </div>

                            {/* 날짜 */}
                            <div className={`mypage-card-date-${i + 1}`}>
                                {formatDate(post.createdAt)}
                            </div>

                            {/* 상태 아이콘 */}
                            <img
                                className={`mypage-card-status-icon-${i + 1}`}
                                alt="Status"
                                src="https://c.animaapp.com/3LplbCFc/img/-----1.svg"
                            />

                            {/* 태그 아이콘 */}
                            <img
                                className={`mypage-card-icon-${i + 1}`}
                                alt="Icon"
                                src="https://c.animaapp.com/3LplbCFc/img/-----1-4.svg"
                            />
                        </React.Fragment>
                    ))}

                    {/* 구분선 */}
                    <div className="mypage-card-divider-horizontal" />

                    {/* 우측 상단 화살표 */}
                    <img
                        className="mypage-card-arrow"
                        alt="Arrow"
                        src="https://c.animaapp.com/3LplbCFc/img/vector-29.svg"
                    />
                </>
            )}
        </div>
    );
}