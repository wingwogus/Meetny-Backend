import React from 'react';
import '../styles/PostGroupCard.css';

const PostGroupCard = ({ post }) => {
    return (
        <div className="post-card">
            <img src={'https://picsum.photos/200/300'}
                 alt="모임 썸네일"
                 className="thumbnail"
                 onClick={() => window.location.href = '/posts/${postId}'}
            />

            <div className="post-content">
                <h4 className="title">{post.title}</h4>
                <p className="description">{post.content}</p>

                <div className="meta">
                    <span>🏷 태그: {post.postTag?.tagName}</span>
                    <span>📍 {post.address?.city}  {post.address?.town}  {post.address?.street}</span>
                    <span>🕒 {new Date(post.meetingTime).toLocaleString()}</span>
                    <span>🔥 조회수: {post.viewCount}</span>
                </div>
            </div>

            <div className="icons">
                <span>🤍</span>
                <span>🧑‍🤝‍🧑</span>
                <span>🔖</span>
            </div>
        </div>
    );
};

export default PostGroupCard;