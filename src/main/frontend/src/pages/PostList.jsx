import { useEffect, useState } from 'react';
import axiosClient from "../api/axiosClient";
import { useNavigate } from 'react-router-dom';

function PostList() {
    const [posts, setPosts] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        axiosClient().get('/api/posts') // proxy 설정 되어 있다면 이대로
            .then((res) => {
                console.log("받은 게시글:", res.data.data);
                setPosts(res.data.data)
            })
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