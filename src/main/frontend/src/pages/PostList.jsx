import { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function PostList() {
    const [posts, setPosts] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get('/api/posts') // proxy 설정 되어 있다면 이대로
            .then((res) => setPosts(res.data))
            .catch((err) => console.error(err));
    }, []);

    const handleClick = (id) => {
        navigate(`/posts/${id}`);
    };

    return (
        <div>
            <h2>게시글 목록</h2>
            <ul>
                {posts.map(post => (
                    <li key={post.id} onClick={() => handleClick(post.id)} style={{ cursor: 'pointer' }}>
                        <strong>{post.title}</strong> - {post.author}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default PostList;