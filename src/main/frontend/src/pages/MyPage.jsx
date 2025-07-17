import React, { useState, useEffect } from "react";
import axiosClient from "../api/axiosClient";
import {useNavigate} from "react-router-dom";
import "../styles/MyPage.css";
import AppHeader from "../components/AppHeader";

export default function MyPage() {
    const [user, setUser] = useState(null);
    const [errorMsg, setErrorMsg] = useState("");
    // activeTab: "posts" | "participated" | "feed"
    const [activeTab, setActiveTab] = useState("posts");
    const [writtenReviews, setWrittenReviews] = useState([]);
    const [receivedReviews, setReceivedReviews] = useState([]);
    const [likedPosts, setLikedPosts] = useState([]);
    const [followers, setFollowers] = useState([]); // ✅ 초기값 명확히 배열
    const [following, setFollowing] = useState([]); // ✅ 이거 없으면 .length 에러 남
    const [activeFollowTab, setActiveFollowTab] = useState("follower");
    const navigate = useNavigate();

    // ───────────────────────────────────────────────
    // 1) 사용자 정보 가져오기
    // ───────────────────────────────────────────────
    useEffect(() => {
        const fetchMyPage = async () => {
            try {
                const token = localStorage.getItem("accessToken");
                const res = await axiosClient.get("/api/my-page/information", {
                    headers: { Authorization: `Bearer ${token}` },
                });
                setUser(res.data.data);
            } catch (err) {
                console.error(err);
                setErrorMsg("회원 정보를 불러오는 중 오류가 발생했습니다.");
            }
        };
        fetchMyPage();
    }, []);

    // ───────────────────────────────────────────────
    // 2) 받은 동행 후기 데이터 가져오기
    // ───────────────────────────────────────────────
    useEffect(() => {
        const fetchReceivedReviews = async () => {
            if (!user) return;
            try {
                // user.nickname을 username으로 사용한다고 가정
                const res = await axiosClient.get(
                    `/api/reviews/receive/${user.nickname}`
                );
                setReceivedReviews(res.data.data || []);
            } catch (err) {
                console.error(err);
                setReceivedReviews([]);
            }
        };
        fetchReceivedReviews();
    }, [user]);

    // ───────────────────────────────────────────────
    // 4) 내가 좋아요(관심) 누른 동행 게시물(likedPosts) 가져오기
    //    → /api/posts/like 를 호출하여, DTO 내 { posts: [ ... ] } 형태로 받아온다고 가정
    // ───────────────────────────────────────────────
    useEffect(() => {
        const fetchLikedPosts = async () => {
            if (!user) return;
            try {
                const token = localStorage.getItem("accessToken");
                // 만약 토큰이 필요하다면 헤더에 Bearer 토큰을 붙이세요.
                const res = await axiosClient.get("/api/posts/like", {
                    headers: { Authorization: `Bearer ${token}` },
                });
                // 예시 응답: { posts: [ { id, title, content, photo, author, meetingTime, address, … }, … ] }
                setLikedPosts(res.data.data || []);
            } catch (err) {
                console.error(err);
                setLikedPosts([]);
            }
        };
        fetchLikedPosts();
    }, [user]);

    // ───────────────────────────────────────────────
    // 3) 내가 작성한 동행 후기(writtenReviews) 가져오기
    // ───────────────────────────────────────────────
    useEffect(() => {
        const fetchWrittenReviews = async () => {
            if (!user) return;
            try {
                // user.nickname 을 username으로 사용한다고 가정
                const res = await axiosClient.get(
                    `/api/reviews/write/${user.nickname}`
                );
                setWrittenReviews(res.data.data || []);
            } catch (err) {
                console.error(err);
                setWrittenReviews([]);
            }
        };
        fetchWrittenReviews();
    }, [user]);

    // ===============================
    // 팔로워/팔로잉 정보 불러오기 함수
    // ===============================
    const fetchFollowers = async () => {
        try {
            const token = localStorage.getItem("accessToken");
            const res = await axiosClient.get(`/api/follows/${user.nickname}/followers`, {
                headers: { Authorization: `Bearer ${token}` },
            });
            console.log("🔥 API 응답 (followers):", res.data);
            setFollowers(res.data.data);
        } catch (err) {
            console.error("팔로워 불러오기 오류:", err);
            setFollowers([]);
        }
    };

    const fetchFollowing = async () => {
        try {
            const token = localStorage.getItem("accessToken");
            const res = await axiosClient.get(`/api/follows/${user.nickname}/followings`, {
                headers: { Authorization: `Bearer ${token}` },
            });
            console.log("✅ followings 응답", res.data);
            setFollowing(res.data.data);
        } catch (err) {
            console.error("❌ 팔로잉 불러오기 오류:", err);
            setFollowing([]);
        }
    };

    useEffect(() => {
        if (activeFollowTab === "follower") {
            console.log("🔥 [팔로워 모드] followers:", followers);
            fetchFollowers(); // ✅ 이거 호출해줘야 데이터 채워짐
        } else if (activeFollowTab === "following") {
            console.log("🔥 [팔로잉 모드] following:", following);
            fetchFollowing(); // ✅ 팔로잉 탭일 때 자동 호출
        }
    }, [activeFollowTab, user]);


    // ───────────────────────────────────────────────
    // 3) 에러 / 로딩 화면
    // ───────────────────────────────────────────────
    if (errorMsg) {
        return (
            <div className="mypage-loading-container">
                <p className="mypage-error-text">{errorMsg}</p>
            </div>
        );
    }
    if (!user) {
        return (
            <div className="mypage-loading-container">
                <p className="mypage-loading-text">로딩 중...</p>
            </div>
        );
    }

    // ───────────────────────────────────────────────
    // 4) 백엔드에서 받은 데이터를 안전하게 분리
    // ───────────────────────────────────────────────
    const tagList = Array.isArray(user.tags) ? user.tags : [];
    const participationCount = Array.isArray(user.posts)
        ? user.posts.length
        : 0;
    const followerCount = user.followerCount ?? 0;
    const followingCount = user.followingCount ?? 0;

    // “credibility”: { “credibility”: 0 } 스펙 가정
    const rawCred = user.credibility?.credibility ?? 0;
    const trustScore = Math.min(Math.max(rawCred, 0), 100);

    // 프로필 사진 URL (없으면 테스트용 랜덤 사진)
    const avatarUrl =
        user.profilePic && user.profilePic.length > 0
            ? user.profilePic
            : "https://picsum.photos/200/300";

    // ───────────────────────────────────────────────
    // 5) 신뢰도 바: 3단계 색상 및 너비 계산
    // ───────────────────────────────────────────────
    const fullBarWidth = 435; // CSS에 정의된 전체 바 너비
    const filledBarWidth = (trustScore / 100) * fullBarWidth;

    let barColor = "#f7b681"; // 기본: 노란색
    if (trustScore >= 66) {
        barColor = "#72d3aa"; // 초록색
    } else if (trustScore >= 33) {
        barColor = "#f4964a"; // 주황색
    }

    // ───────────────────────────────────────────────
    // 6) 날짜 포맷 함수 (YYYY.MM.DD)
    // ───────────────────────────────────────────────
    const formatDate = (isoString) => {
        if (!isoString) return "";
        const d = new Date(isoString);
        const year = d.getFullYear();
        const month = String(d.getMonth() + 1).padStart(2, "0");
        const day = String(d.getDate()).padStart(2, "0");
        return `${year}.${month}.${day}`;
    };

    // ───────────────────────────────────────────────
    // 7) 리뷰용 날짜 포맷 함수 (YY/MM/DD)
    // ───────────────────────────────────────────────
    const formatDateSlash = (isoString) => {
        if (!isoString) return "";
        const d = new Date(isoString);
        const yy = String(d.getFullYear() % 100).padStart(2, "0");
        const month = String(d.getMonth() + 1).padStart(2, "0");
        const day = String(d.getDate()).padStart(2, "0");
        return `${yy}/${month}/${day}`;
    };

    return (
        <div className="mypage-container">
            <div className="mypage-content">
                <AppHeader/>
                {/* =================================================
             1. 상단 프로필 & 태그 섹션
        ================================================= */}
                <div className="mypage-profile-section">
                    <div className="mypage-tag-wrapper">
                        {/* (1-1) 태그 배경 이미지 */}
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
              <span className="mypage-profile-nickname">
                {user.nickname}
              </span>
                            <span className="mypage-profile-tag-text">
                {" "}
                                님의 동행 키워드
              </span>
                        </div>
                    </div>

                    {/* (1-4) 프로필·통계 카드 배경 */}
                    <div className="mypage-profile-card-bg" />
                    <div className="mypage-stats-card-bg" />

                    {/* (1-5) 프로필 아바타 */}
                    <img
                        className="mypage-avatar"
                        alt="Profile Avatar"
                        src={avatarUrl}
                    />
                    <div className="mypage-profile-name">{user.nickname}</div>

                    {/* (1-6) 참여 인증 */}
                    <p className="mypage-participation-cert">
                        <span className="mypage-participation-text">동행 </span>
                        <span className="mypage-participation-highlight">
              {participationCount}회 참여
            </span>
                        <span className="mypage-participation-text"> 인증</span>
                    </p>

                    {/* (1-7) 신뢰도 프로그레스바 */}
                    <div className="mypage-trust-label">동행 신뢰도</div>
                    {/* 배경 바 */}
                    <div
                        className="mypage-trust-bar-bg"
                        style={{
                            left: `${313 + filledBarWidth - 35}px`,
                            top: "218px",
                        }}
                    />
                    {/* 회색 전체 바 */}
                    <div className="mypage-trust-bar-full" />
                    {/* 채워진 바 (너비·색상 동적) */}
                    <div
                        className="mypage-trust-bar-filled"
                        style={{
                            width: `${filledBarWidth}px`,
                            backgroundColor: barColor,
                        }}
                    />
                    {/* 강조 바 (디자인 참고용) */}

                    {/* 표시 점 */}
                    <div
                        className="mypage-trust-indicator"
                        style={{
                            left: `${313 + filledBarWidth - 7.5}px`,
                        }}
                    />
                    {/* 점수 텍스트 */}
                    <div
                        className="mypage-trust-score"
                        style={{
                            left: `${313 + filledBarWidth - 14}px`,
                            top: "220px",
                        }}
                    >
                        {trustScore.toFixed(1)}
                    </div>

                    {/* (1-8) 게시물/팔로워/팔로잉 통계 */}
                    <div className="mypage-stat-label-posts">게시물</div>
                    <div className="mypage-stat-value-posts">{participationCount}</div>
                    <div className="mypage-stat-value-followers">{followerCount}</div>
                    <div className="mypage-stat-value-following">{followingCount}</div>
                    <div className="mypage-stat-label-followers" onClick={() => setActiveFollowTab("follower")}>
                        팔로워
                    </div>
                    <div className="mypage-stat-label-following" onClick={() => setActiveFollowTab("following")}>
                        팔로잉
                    </div>

                    {/* (1-9) 세로 구분선들 */}
                    <div className="mypage-divider-vertical-1" />
                    <div className="mypage-divider-vertical-2" />
                </div>

                {/* =================================================
             2. 상단 우측 검색 아이콘 & 프로필 배지
        ================================================= */}

                {/* =================================================
             3. 탭 영역 (동행 게시글 / 참가한 동행 / 동행 후기)
             (받은 동행 후기는 탭이 아니라 상시 노출)
        ================================================= */}
                <div
                    className={`mypage-tab-title-posts ${
                        activeTab === "posts" ? "active" : ""
                    }`}
                    onClick={() => setActiveTab("posts")}
                >
                    동행 게시글
                </div>
                <div
                    className={`mypage-tab-title-participated ${
                        activeTab === "participated" ? "active" : ""
                    }`}
                    onClick={() => setActiveTab("participated")}
                >
                    참가한 동행
                </div>
                <div
                    className={`mypage-tab-title-feed ${
                        activeTab === "feed" ? "active" : ""
                    }`}
                    onClick={() => setActiveTab("feed")}
                >
                    동행 후기
                </div>

                {/* =================================================
             4. 사이트 로고 (상단 중앙)
        ================================================= */}


                {/* =================================================
             5. 관심 동행 섹션 (항상 왼쪽 상단)
        ================================================= */}
                <div className="mypage-interest-section">
                    <div className="mypage-interest-heading">관심 동행</div>

                    {likedPosts.length > 0 ? (
                        likedPosts.map((post, idx) => (
                            <div key={post.id} className="mypage-interest-card">
                                {/* 1) 썸네일 */}
                                <div className="mypage-review-thumb-wrapper-2">
                                    <img
                                        className="mypage-review-thumb-img-2"
                                        alt={post.title}
                                        src={
                                            post.photo
                                                ? post.photo
                                                : "https://via.placeholder.com/114x110?text=No+Photo"
                                        }
                                    />
                                    <div className="mypage-review-thumb-overlay-2" />
                                    <div className="mypage-thumb-overlay-text-2">
                                        종료된
                                        <br />
                                        동행
                                    </div>
                                </div>

                                {/* 2) 제목 */}
                                <div className="mypage-review-title-2">
                                    {post.title}
                                </div>

                                {/* 3) content (설명) */}
                                <p className="mypage-review-snippet-2">
                                    {post.content}
                                </p>

                                {/* 4) 날짜 (createdAt) */}
                                <div className="mypage-review-date-2">
                                    {formatDate(post.createdAt)}
                                </div>
                            </div>
                        ))
                    ) : (
                        <div className="mypage-interest-card">
                        <div className="mypage-interest-empty">
                            관심 동행이 없습니다.
                        </div>
                        </div>
                    )}
                </div>
                {/* =================================================
             6. 받은 동행 후기 섹션 (항상 오른쪽 상단)
        ================================================= */}
                <div className="mypage-review-list-wrapper">
                    <div className="mypage-review-list-heading">받은 동행 후기</div>

                    {receivedReviews.length > 0 ? (
                        <div className="mypage-review-list">
                            {receivedReviews.map((rev, idx) => (
                                <div key={idx} className="mypage-received-review-card">
                                    {/* 사진(썸네일) */}
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
                                            종료된
                                            <br />
                                            동행
                                        </div>
                                    </div>

                                    {/* 제목 */}
                                    <div className="mypage-review-title">{rev.postTitle}</div>

                                    {/* 내용(댓글) */}
                                    <p className="mypage-review-snippet">“{rev.comment}”</p>

                                    {/* 만난 날짜 */}
                                    <div className="mypage-review-date">
                                        {formatDateSlash(rev.meetingTime)}
                                    </div>

                                    {/* 작성자 닉네임 */}
                                    <div className="mypage-reviewer-nickname">
                                        {rev.reviewerNickname}
                                    </div>
                                </div>
                            ))}
                        </div>
                    ) : (
                        <div className="mypage-received-review-card-empty">
                            동행 후기 없음
                        </div>
                    )}
                </div>

                {/* =================================================
             7. “동행 게시글” 탭 눌렀을 때 (activeTab === "posts")
             ─ Travel Management (2×2 레이아웃)
        ================================================= */}
                {activeTab === "posts" && (
                    <div className="mypage-travel-management-section">
                        <div className="mypage-travel-management-title">
                            동행 관리
                        </div>

                        {/* posts 첫 4개만 2×2 레이아웃으로 배치 */}
                        {user.posts.slice(0, 4).map((post, i) => (
                            <React.Fragment key={post.id}>
                                {/* 1) 썸네일 */}
                                <img
                                    className={`mypage-card-thumb-${i + 1}`}
                                    alt={`Travel ${i + 1}`}
                                    src="https://fastly.picsum.photos/id/992/300/200.jpg?hmac=w137wSlXMe7QugWkdz2qvxFlif1dwEWqNnv4qFIyWps"
                                    onClick={()=>navigate(`/posts/${post.id}`)}
                                />

                                {/* 2) 제목 */}
                                <p className={`mypage-card-title-${i + 1}`}
                                   onClick={()=>navigate(`/posts/${post.id}`)}>
                                    {post.title}
                                </p>

                                {/* 3) 위치(동) */}
                                <div className={`mypage-card-location-${i + 1}`}>
                                    {post.address?.town || ""}
                                </div>

                                {/* 4) 날짜 */}
                                <div className={`mypage-card-date-${i + 1}`}>
                                    {formatDate(post.createdAt)}
                                </div>

                                {/* 5) 해당 카드의 “상태 아이콘” */}
                                <img
                                    className={`mypage-card-status-icon-${i + 1}`}
                                    alt={`Status ${i + 1}`}
                                    src="https://c.animaapp.com/3LplbCFc/img/-----1.svg"
                                />

                                {/* 6) 해당 카드의 “아이콘(태그 등)” */}
                                <img
                                    className={`mypage-card-icon-${i + 1}`}
                                    alt={`Icon ${i + 1}`}
                                    src="https://c.animaapp.com/3LplbCFc/img/-----1-4.svg"
                                />
                            </React.Fragment>
                        ))}

                        {/* 7) 카드들 전체를 나누는 가로 구분선 */}
                        <div className="mypage-card-divider-horizontal" />

                        {/* 8) 화살표 (전체 section 우측 상단) */}
                        <img
                            className="mypage-card-arrow"
                            alt="Arrow"
                            src="https://c.animaapp.com/3LplbCFc/img/vector-29.svg"
                        />
                    </div>
                )}
                {/* =================================================
             8. “참가한 동행” 탭 눌렀을 때 (activeTab === "participated")
             ─ 향후 백엔드 데이터가 내려오면 동일한 레이아웃(map() 안에서 대체)
        ================================================= */}
                {activeTab === "participated" && (
                    <div className="mypage-participated-section">
                        <div className="mypage-participated-placeholder">
                            참여한 동행 데이터가 없습니다.
                        </div>
                    </div>
                )}

                {/* =================================================
             9. “동행 후기” 탭 눌렀을 때 (activeTab === "feed")
             ─ 향후 백엔드 데이터가 내려오면 동일한 레이아웃(map() 안에서 대체)
        ================================================= */}
                {activeTab === "feed" && (
                    <div className="mypage-travel-management-section">
                        {/* 1) 섹션 타이틀 */}
                        <div className="mypage-travel-management-title">
                            동행 후기 관리
                        </div>

                        {/* 2) writtenReviews 배열의 첫 4개만 2×2 형태로 배치 */}
                        {writtenReviews.slice(0, 4).map((rev, i) => (
                            <React.Fragment key={i}>
                                {/* 2-1) 썸네일 영역 */}
                                <img
                                    className={`mypage-card-thumb-${i + 1}`}
                                    alt={`Review Thumb ${i + 1}`}
                                    src={
                                        rev.photo
                                            ? rev.photo
                                            : "https://via.placeholder.com/308x246?text=No+Photo"
                                    }
                                />

                                {/* 2-2) 후기 제목 (postTitle) */}
                                <p className={`mypage-card-title-${i + 1}`}>
                                    {rev.postTitle}
                                </p>

                                {/* 2-3) 만난 날짜 (YYYY.MM.DD) */}
                                <div className={`mypage-card-date-${i + 1}`}>
                                    {formatDate(rev.meetingTime)}
                                </div>

                                {/* 2-4) 내용(댓글) */}
                                <div
                                    className={`mypage-card-location-${i + 1}`}
                                    style={{
                                        top: "calc( /*mypage-card-date 의 top + 24px*/ )"
                                    }}
                                >
                                    {rev.address?.town || ""}
                                </div>

                                {/* 2-5) 작성자 닉네임 */}

                                {/* 2-6) “상태 아이콘” (card-status-icon) */}
                                <img
                                    className={`mypage-card-status-icon-${i + 1}`}
                                    alt={`Status Icon ${i + 1}`}
                                    src="https://c.animaapp.com/3LplbCFc/img/-----1.svg"
                                />

                                {/* 2-7) “추가 아이콘(태그 등)” (card-icon) */}
                                <img
                                    className={`mypage-card-icon-${i + 1}`}
                                    alt={`Icon ${i + 1}`}
                                    src="https://c.animaapp.com/3LplbCFc/img/-----1-4.svg"
                                />
                            </React.Fragment>
                        ))}

                        {/* 3) 카드들을 나누는 가로 구분선 (후기가 하나라도 있으면 렌더) */}
                        {writtenReviews.length > 0 && (
                            <div className="mypage-card-divider-horizontal" />
                        )}

                        {/* 4) 전체 섹션 우측 상단 화살표 (후기가 하나라도 있으면 렌더) */}
                        {writtenReviews.length > 0 && (
                            <img
                                className="mypage-card-arrow"
                                alt="Arrow"
                                src="https://c.animaapp.com/3LplbCFc/img/vector-29.svg"
                            />
                        )}

                        {/* 5) 후기가 하나도 없으면 중앙 메시지 */}
                        {writtenReviews.length === 0 && (
                            <div className="mypage-feed-placeholder">
                                동행 후기 데이터가 없습니다.
                            </div>
                        )}
                    </div>
                )}

                {/* =================================================
             10. 사이드바 (내비게이션 메뉴)
        ================================================= */}
                <div className="mypage-sidebar">
                    <div className="mypage-nav-travel-management">동행 관리</div>
                    <div className="mypage-nav-home">마이페이지</div>
                    <div className="mypage-nav-my-posts">내 게시글</div>
                    <div className="mypage-nav-home-duplicate">마이페이지</div>
                    <div className="mypage-nav-followers-following">
                        팔로워/팔로잉
                    </div>
                    <div className="mypage-nav-liked-posts">좋아요 한 게시물</div>
                    <div className="mypage-nav-reviews">동행 후기</div>
                    <div className="mypage-nav-settings">설정</div>

                    <div className="mypage-sidebar-divider" />

                    <div className="mypage-profile-manage-btn">
                        <div className="mypage-profile-manage-text">프로필 관리</div>
                    </div>
                    <div
                        className="mypage-chat-btn"
                        onClick={() => navigate("/chat")}>
                        <div className="mypage-chat-icon" />
                        <div className="mypage-chat-badge" />
                    </div>
                </div>

                {/* =================================================
             11. 팔로워/팔로잉 패널 (하단 우측)
        ================================================= */}
                <div className="mypage-followers-pane">
                    {activeFollowTab === "follower" && followers && (
                        <div className="mypage-follow-list">
                            {followers.length === 0 ? (
                                <p>팔로워가 없습니다.</p>
                            ) : (
                                followers.map((f, idx) => (
                                    <div key={f.memberId} className="follower-entry">
                                        <img
                                            src={f.profileImg || 'https://via.placeholder.com/40'}
                                            alt={`${f.nickname} 프로필`}
                                            className="follower-avatar"
                                        />
                                        <div className="follower-info">
                                            <div className="nickname">{f.nickname}</div>
                                            <div className="credibility">{f.credibility?.toFixed(1)}</div>
                                        </div>
                                        <button className="follow-btn">팔로우</button>
                                    </div>
                                ))
                            )}
                        </div>
                    )}

                    {activeFollowTab === "following" && (
                        <div className="mypage-follow-list">
                            {following.length === 0 ? (
                                <p>팔로잉한 사용자가 없습니다.</p>
                            ) : (
                                following.map((f, idx) => (
                                    <div key={f.memberId} className="follower-entry">
                                        <img
                                            src={f.profileImg || "https://via.placeholder.com/40"}
                                            alt={`${f.nickname} 프로필`}
                                            className="follower-avatar"
                                        />
                                        <div className="follower-info">
                                            <div className="nickname">{f.nickname}</div>
                                            <div className="credibility">
                                                {f.credibility != null ? f.credibility.toFixed(1) : "0.0"}
                                            </div>
                                        </div>
                                        <button
                                            className="follow-btn"
                                            onClick={async () => {
                                                try {
                                                    const token = localStorage.getItem("accessToken");
                                                    await axiosClient.delete(`/api/follows/unfollow/${f.memberId}`, {
                                                        headers: { Authorization: `Bearer ${token}` },
                                                    });
                                                    setFollowing(prev => prev.filter(item => item.memberId !== f.memberId));
                                                    // 숫자 동기화 (팔로잉 수 감소)
                                                    setUser(prev => ({
                                                      ...prev,
                                                      followingCount: (prev.followingCount ?? 1) - 1,
                                                    }));
                                                } catch (err) {
                                                    console.error("❌ 언팔로우 실패:", err);
                                                }
                                            }}
                                        >
                                            언팔로우
                                        </button>
                                    </div>
                                ))
                            )}
                        </div>
                    )}
                </div>
</div>
        </div>
    );
}