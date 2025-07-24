import React, { useEffect, useState } from 'react';
import axios from 'axios';
import PostGroupSection from './PostGroupSection';
import PostCard from './PostGroupCard';

const PostListContainer = ({ selectedTag }) => {
    const [recommendedPosts, setRecommendedPosts] = useState([]);
    const [filteredPosts, setFilteredPosts] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const res1 = await axios.get('/api/posts');
            setRecommendedPosts(res1.data.data);
        };
        fetchData();
    }, []);

    // 태그 선택 시 필터링
    useEffect(() => {
        if (!selectedTag) {
            setFilteredPosts(recommendedPosts);
        } else {
            const filtered = recommendedPosts.filter(
                (post) => post.postTag?.tagName === selectedTag.tagName
            );
            setFilteredPosts(filtered);
        }
    }, [selectedTag, recommendedPosts]);

    return (
        <div>
            <PostGroupSection title="모임 둘러보기" onMoreClick={() => {}}>
                {filteredPosts.map(post => (
                    <PostCard key={post.id} post={post} selectedTag={selectedTag} />
                ))}
            </PostGroupSection>
        </div>
    );
};

export default PostListContainer;