import {useEffect, useState} from "react";
import { useParams } from "react-router-dom";
import axiosClient from "../api/axiosClient";

function ReviewForm() {
    const { postId } = useParams();
    const numericPostId = parseInt(postId);

    const [comment, setComment] = useState("");
    const [selectedTags, setSelectedTags] = useState([]);
    const [allMannerTags, setAllMannerTags] = useState([]);

    useEffect(() => {
        axiosClient.get("/api/reviews/tags")
            .then((res) => {
                const tagsFromServer = res.data.data.map((tag) => ({
                    id: tag.id,            // ✅ 서버에서 받은 실제 ID 사용
                    label: tag.tagName     // ✅ 이름 그대로 사용
                }));
                setAllMannerTags(tagsFromServer);
            })
            .catch((err) => {
                console.error("매너태그 로딩 실패:", err);
            });
    }, []);

    const toggleTag = (tagId) => {
        setSelectedTags(prev =>
            prev.includes(tagId)
                ? prev.filter(id => id !== tagId)
                : [...prev, tagId]
        );
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const payload = {
            comment,
            mannerTags: selectedTags
        };

        try {
            await axiosClient.post(`/api/reviews/${numericPostId}`, payload);
            alert("리뷰 작성 완료!");
            setComment("");
            setSelectedTags([]);
        } catch (err) {
            console.error("리뷰 작성 실패:", err);
            alert("리뷰 작성 실패");
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>리뷰 코멘트</label><br />
                <input
                    type="text"
                    value={comment}
                    onChange={e => setComment(e.target.value)}
                    required
                />
            </div>

            <div>
                <label>매너 태그 선택</label>
                <ul>
                    {allMannerTags.map(tag => (
                        <li key={tag.id}>
                            <label>
                                <input
                                    type="checkbox"
                                    value={tag.id}
                                    checked={selectedTags.includes(tag.id)}
                                    onChange={() => toggleTag(tag.id)}
                                />
                                {tag.label}
                            </label>
                        </li>
                    ))}
                </ul>
            </div>

            <button type="submit">리뷰 작성</button>
        </form>
    );
}

export default ReviewForm;
