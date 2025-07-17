import React from "react";

export default function ProfileSection({
                                           user,
                                           tagList,
                                           avatarUrl,
                                           trustScore,
                                           filledBarWidth,
                                           barColor,
                                           participationCount,
                                           followerCount,
                                           followingCount,
                                           setActiveFollowTab,
                                       }) {
    return (
        <div className="mypage-profile-section">
            {/* (1-1) 태그 배경 이미지 */}
            <div className="mypage-tag-wrapper">
                <img
                    className="mypage-tag-background"
                    alt="Tag Background"
                    src="https://c.animaapp.com/3LplbCFc/img/rectangle-73.png"
                />

                {/* (1-2) 동적 Tags */}
                <div className="mypage-tag-list">
                    {tagList.map((tag, idx) => (
                        <div
                            key={idx}
                            className="mypage-tag-pill"
                            style={{ backgroundColor: tag.color }}
                        >
                            {tag.tagName}
                        </div>
                    ))}
                </div>

                {/* (1-3) 프로필 닉네임 */}
                <div className="mypage-profile-tag-label">
                    <span className="mypage-profile-nickname">{user.nickname}</span>
                    <span className="mypage-profile-tag-text"> 님의 동행 키워드</span>
                </div>
            </div>

            {/* (1-4) 카드 배경 */}
            <div className="mypage-profile-card-bg" />
            <div className="mypage-stats-card-bg" />

            {/* (1-5) 아바타 */}
            <img className="mypage-avatar" alt="Avatar" src={avatarUrl} />
            <div className="mypage-profile-name">{user.nickname}</div>

            {/* (1-6) 참여 인증 */}
            <p className="mypage-participation-cert">
                <span className="mypage-participation-text">동행 </span>
                <span className="mypage-participation-highlight">{participationCount}회 참여</span>
                <span className="mypage-participation-text"> 인증</span>
            </p>

            {/* (1-7) 신뢰도 바 */}
            <div className="mypage-trust-label">동행 신뢰도</div>
            <div
                className="mypage-trust-bar-bg"
                style={{
                    left: `${313 + filledBarWidth - 35}px`,
                    top: "218px",
                }}
            />
            <div className="mypage-trust-bar-full" />
            <div
                className="mypage-trust-bar-filled"
                style={{
                    width: `${filledBarWidth}px`,
                    backgroundColor: barColor,
                }}
            />
            <div
                className="mypage-trust-indicator"
                style={{ left: `${313 + filledBarWidth - 7.5}px` }}
            />
            <div
                className="mypage-trust-score"
                style={{
                    left: `${313 + filledBarWidth - 14}px`,
                    top: "220px",
                }}
            >
                {trustScore.toFixed(1)}
            </div>

            {/* (1-8) 통계 */}
            <div className="mypage-stat-label-posts">게시물</div>
            <div className="mypage-stat-value-posts">{participationCount}</div>
            <div className="mypage-stat-value-followers">{followerCount}</div>
            <div className="mypage-stat-value-following">{followingCount}</div>
            <div
                className="mypage-stat-label-followers"
                onClick={() => setActiveFollowTab("follower")}
            >
                팔로워
            </div>
            <div
                className="mypage-stat-label-following"
                onClick={() => setActiveFollowTab("following")}
            >
                팔로잉
            </div>

            {/* (1-9) 구분선 */}
            <div className="mypage-divider-vertical-1" />
            <div className="mypage-divider-vertical-2" />
        </div>
    );
}