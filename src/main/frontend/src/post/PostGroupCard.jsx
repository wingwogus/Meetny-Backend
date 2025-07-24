import React from 'react';
import '../styles/PostGroupCard.css';
import location from '../assets/location.svg';
import time from '../assets/time.svg';
import view from '../assets/postview.svg';

const PostGroupCard = ({ post,selectedTag }) => {
    return (
        <div className="post-card">
            <img
                src={'https://picsum.photos/200/300'}
                alt="모임 썸네일"
                className="thumbnail"
                onClick={() => window.location.href = `/posts/${post.id}`}
            />

            <div className="post-content">
                <h4 className="title"
                    onClick={() => window.location.href = `/posts/${post.id}`}>{post.title}</h4>
                <p className="description">{post.content}</p>

                <div className="meta">
                    <span className="tag-pill">{post.postTag?.tagName}</span>
                    <span> <img src={location} alt="location" className="location" />
                        {post.address?.city}  {post.address?.town}  {post.address?.street}
                    </span>
                    <span><img src={time} alt="time" className="location" /> {new Date(post.meetingTime).toLocaleString()}</span>
                    <span> <img src={view} alt="view" className="location" /> 조회수: {post.viewCount}</span>
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