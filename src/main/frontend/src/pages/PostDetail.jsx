import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axiosClient from "../api/axiosClient";
import "../styles/PostDetail.css";
import AppHeader from "../components/AppHeader";
import point from "../assets/point.svg";
import time from "../assets/time.svg";

export default function PostDetail() {
    const { id } = useParams();
    const [post, setPost] = useState(null);
    const [loading, setLoading] = useState(true);

    const handleJoinClick = async () => {
        try {
            await axiosClient.post(`/api/chat/rooms/${post.id}`);
            window.location.href = "/chat";
        } catch (error) {
            console.error("채팅방 생성 실패:", error);
            alert(error.response?.data?.message || "채팅방 생성 중 오류가 발생했습니다.");
        }
    };

    const handleLike = async () => {
        try {
            await axiosClient.post(`/api/posts/${post.id}/like`);
            window.location.href = "/information";
        } catch (error) {
            console.error("채팅방 생성 실패:", error);
            alert("채팅방 입장 중 오류가 발생했습니다.");
        }
    };




    useEffect(() => {
        const fetchPost = async () => {
            try {
                const response = await axiosClient.get(`/api/posts/${id}`);
                setPost(response.data.data);
            } catch (err) {
                console.error("게시물 불러오기 실패:", err);
            } finally {
                setLoading(false);
            }
        };
        fetchPost();
    }, [id]);

    if (loading) return <div className="post-loading">로딩 중...</div>;
    if (!post) return <div className="post-error">게시물을 불러올 수 없습니다.</div>;

    return (
        <>
            <AppHeader/>
            <div className="post-detail-wrapper">
                <h2 className="post-detail-header">{post.title}</h2>
                <div className="post-main">

                    <div className="post-main-card">
                        <div className="post-left">
                            <img className="post-main-image" src={post.photo} alt="대표 이미지" />
                        </div>
                        <div className="post-right">
                            <div className="post-tags-top">
                                <span className="post-category">{post.postTag.tagName}</span>
                            </div>

                            <h3 className="post-title">{post.title}</h3>

                            <div className="post-meta-info">
                                <p><img src={point} alt="location"/>  {post.address.city} {post.address.town} {post.address.street} </p>
                                <p>
                                    <span><img src={time} alt="time"/> {new Date(post.meetingTime).toLocaleDateString()} {new Date(post.meetingTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                                    </span>
                                    <span className="d-day">D-{post.dDay ?? 7}</span>
                                </p>
                            </div>

                            <div className="like-cta">
                                <button className="cta-button" onClick={handleJoinClick}>동행하기</button>
                                <button className="like-button" onClick={handleLike}> {post.liked === true ? "❤️": "🤍️"} </button>
                            </div>
                        </div>
                    </div>
                    <div className="post-author-box">
                        <h4>작성자</h4>
                        <div className="author-info">
                            <img className="author-image" src={post.author.profileImg} alt="작성자" />
                            <div className="author-text">
                                <div className="author-name">
                                    {post.author.nickname}
                                    <div className="author-score">{post.author.credibility}%</div>
                                </div>
                                <div className="author-tags">
                                    {post.author.tags?.map((tag, idx) => (
                                        <span key={idx} className="author-tag">{tag}</span>
                                    ))}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="post-detail-info">
                    <div className="post-detail-info-header">모임 상세 정보</div>
                    <div className="post-detail-info-body">
                        <h2 className="detail-title">{post.title}</h2>
                        <p className="detail-location">{post.address.town} | {new Date(post.meetingTime).toLocaleString()}</p>
                        <img className="post-detail-image" src={post.photo} alt="상세 이미지" />

                        <div className="post-detail-description">
                            <p>{post.content}</p>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}
