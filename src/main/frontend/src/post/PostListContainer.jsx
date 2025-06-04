import React, { useEffect, useState } from 'react';
import axios from 'axios';
import PostGroupSection from './PostGroupSection';
import PostCard from './PostGroupCard';

const PostListContainer = () => {
    const [recommendedPosts, setRecommendedPosts] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const res1 = await axios.get('/api/posts/');
            setRecommendedPosts(res1.data.data);
        };
        fetchData();
    }, []);

    return (
        <div>
            <PostGroupSection title="모임 둘러보기" onMoreClick={() => {}}>
                {recommendedPosts.map(post => (
                    <PostCard key={post.id} post={post} />
                ))}
            </PostGroupSection>
        </div>
    );
};

export default PostListContainer;