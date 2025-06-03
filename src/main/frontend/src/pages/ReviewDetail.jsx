import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import axiosClient from "../api/axiosClient";

function ReviewDetail() {
    const { id } = useParams(); // 리뷰 ID
    const [review, setReview] = useState(null);

    useEffect(() => {
        axiosClient.get(`/api/reviews/${id}`)
            .then((res) => {
                console.log('받은 리뷰:', res.data.data);
                setReview(res.data.data);
            })
            .catch((err) => {
                console.error('리뷰 불러오기 실패:', err);
            });
    }, [id]);

    if (!review) return <p>로딩 중...</p>;

    return (
        <div style={{ padding: "2rem" }}>
            <h2>리뷰 상세</h2>
            <p><strong>리뷰어 ID:</strong> {review.reviewerId}</p>
            <p><strong>리뷰어 닉네임:</strong> {review.reviewerNickname}</p>
            <p><strong>리뷰 코멘트:</strong> {review.comment}</p>
            <p><strong>게시글 제목:</strong> {review.postTitle}</p>
            <p><strong>게시글 사진:</strong> {review.photo ? <img src={review.photo} alt="photo" style={{ maxWidth: "300px" }} /> : '없음'}</p>
            <p><strong>만남 시간:</strong> {new Date(review.meetingTime).toLocaleString()}</p>
            <p><strong>매너 태그:</strong></p>
            <ul>
                {review.mannerTag.map((tag, idx) => (
                    <li key={idx}>{tag}</li>
                ))}
            </ul>
        </div>
    );
}

export default ReviewDetail;