import React, { useState, useEffect } from "react";
import axiosClient from "../api/axiosClient";
import {useNavigate} from "react-router-dom";
import "../styles/MyPage.css";
import AppHeader from "../components/AppHeader";
import ProfileSection from "../components/ProfileSection";
import InterestSection from "../components/InterestSection";
import ReceivedReviewSection from "../components/ReceivedReviewSection";
import TabSelector from "../components/TabSelector";
import PostTabSection from "../components/PostTabSection";
import ReviewTabSection from "../components/ReviewTabSection";
import ParticipatedTabSection from "../components/ParticipatedTabSection";
import Sidebar from "../components/Sidebar";
import FollowersPane from "../components/FollowersPane";

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
                    <ProfileSection
                        user={user}
                        tagList={tagList}
                        avatarUrl={avatarUrl}
                        trustScore={trustScore}
                        filledBarWidth={filledBarWidth}
                        barColor={barColor}
                        participationCount={participationCount}
                        followerCount={followerCount}
                        followingCount={followingCount}
                        setActiveFollowTab={setActiveFollowTab}
                    />

                {/* =================================================
             2. 상단 우측 검색 아이콘 & 프로필 배지
        ================================================= */}

                {/* =================================================
             3. 탭 영역 (동행 게시글 / 참가한 동행 / 동행 후기)
             (받은 동행 후기는 탭이 아니라 상시 노출)
        ================================================= */}
                <TabSelector activeTab={activeTab} setActiveTab={setActiveTab} />

                {/* =================================================
             4. 사이트 로고 (상단 중앙)
        ================================================= */}


                {/* =================================================
             5. 관심 동행 섹션 (항상 왼쪽 상단)
        ================================================= */}
                <div className="mypage-interest-section">
                    <div className="mypage-interest-heading">관심 동행</div>
                    <InterestSection likedPosts={likedPosts} formatDate={formatDate} />
                </div>                {/* =================================================
             6. 받은 동행 후기 섹션 (항상 오른쪽 상단)
        ================================================= */}
                <ReceivedReviewSection
                    receivedReviews={receivedReviews}
                    formatDateSlash={formatDateSlash}
                />
                {/* =================================================
             7. “동행 게시글” 탭 눌렀을 때 (activeTab === "posts")
             ─ Travel Management (2×2 레이아웃)
        ================================================= */}
                {activeTab === "posts" && (
                    <PostTabSection posts={user.posts} formatDate={formatDate} />
                )}                {/* =================================================
             8. “참가한 동행” 탭 눌렀을 때 (activeTab === "participated")
             ─ 향후 백엔드 데이터가 내려오면 동일한 레이아웃(map() 안에서 대체)
        ================================================= */}
                {activeTab === "participated" && (
                    <ParticipatedTabSection participatedPosts={[]} />
                )}
                {/* =================================================
             9. “동행 후기” 탭 눌렀을 때 (activeTab === "feed")
             ─ 향후 백엔드 데이터가 내려오면 동일한 레이아웃(map() 안에서 대체)
        ================================================= */}
                {activeTab === "feed" && (
                    <ReviewTabSection
                        writtenReviews={writtenReviews}
                        formatDate={formatDate}
                    />
                )}
                {/* =================================================
             10. 사이드바 (내비게이션 메뉴)
        ================================================= */}
                <Sidebar />
                {/* =================================================
             11. 팔로워/팔로잉 패널 (하단 우측)
        ================================================= */}
                <FollowersPane
                    activeFollowTab={activeFollowTab}
                    followers={followers}
                    following={following}
                    onUnfollow={async (memberId) => {
                        try {
                            const token = localStorage.getItem("accessToken");
                            await axiosClient.delete(`/api/follows/unfollow/${memberId}`, {
                                headers: { Authorization: `Bearer ${token}` },
                            });
                            setFollowing(prev => prev.filter(f => f.memberId !== memberId));
                            setUser(prev => ({
                                ...prev,
                                followingCount: (prev.followingCount ?? 1) - 1,
                            }));
                        } catch (err) {
                            console.error("❌ 언팔로우 실패:", err);
                        }
                    }}
                /></div>
        </div>
    );
}