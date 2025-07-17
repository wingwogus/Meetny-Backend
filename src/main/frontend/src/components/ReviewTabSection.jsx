import React from "react";

export default function ReviewTabSection({ writtenReviews, formatDate }) {
    const sliced = writtenReviews.slice(0, 4);

    return (
        <div className="mypage-travel-management-section">
            <div className="mypage-travel-management-title">동행 후기 관리</div>

            {sliced.map((rev, i) => (
                <React.Fragment key={i}>
                    {/* 썸네일 */}
                    <img
                        className={`mypage-card-thumb-${i + 1}`}
                        alt={`Review Thumb ${i + 1}`}
                        src={
                            rev.photo
                                ? rev.photo
                                : "https://via.placeholder.com/308x246?text=No+Photo"
                        }
                    />

                    {/* 제목 */}
                    <p className={`mypage-card-title-${i + 1}`}>
                        {rev.postTitle}
                    </p>

                    {/* 날짜 */}
                    <div className={`mypage-card-date-${i + 1}`}>
                        {formatDate(rev.meetingTime)}
                    </div>

                    {/* 위치 */}
                    <div
                        className={`mypage-card-location-${i + 1}`}
                        style={{
                            top: "calc( /*mypage-card-date 의 top + 24px*/ )"
                        }}
                    >
                        {rev.address?.town || ""}
                    </div>

                    {/* 상태 아이콘 */}
                    <img
                        className={`mypage-card-status-icon-${i + 1}`}
                        alt="Status Icon"
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

            {/* 구분선 및 화살표 */}
            {writtenReviews.length > 0 && (
                <>
                    <div className="mypage-card-divider-horizontal" />
                    <img
                        className="mypage-card-arrow"
                        alt="Arrow"
                        src="https://c.animaapp.com/3LplbCFc/img/vector-29.svg"
                    />
                </>
            )}

            {writtenReviews.length === 0 && (
                <div className="mypage-feed-placeholder">
                    동행 후기 데이터가 없습니다.
                </div>
            )}
        </div>
    );
}