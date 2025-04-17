import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import axios from 'axios';

function PostDetail() {
    const { id } = useParams();
    const [post, setPost] = useState(null);

    useEffect(() => {
        axios.get(`/api/posts/${id}`)
            .then((res) => {
                console.log("받은 게시글:", res.data);
                setPost(res.data)
            })
            .catch((err) => console.error(err));
    }, [id]);

    if (!post) return <p>로딩 중...</p>;

    return (
        <div>
            <h2>{post.title}</h2>
            <p><strong>작성자:</strong> {post.author}</p>
            <p><strong>내용:</strong> {post.content}</p>
            <p><strong>작성일:</strong> {post.createdAt}</p>
        </div>
    );
}

export default PostDetail;