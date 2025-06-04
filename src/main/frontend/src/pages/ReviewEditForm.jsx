import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axiosClient from "../api/axiosClient";
import "../styles/review.css";
import CompanionCard from "../components/CompanionCard";
import MyActivityCard from "../components/MyActivityCard";
import AppHeader from "../components/AppHeader";
import { ReactComponent as CheckedIcon } from "../assets/checkbox_checked.svg";
import { ReactComponent as UncheckedIcon } from "../assets/checkbox_unchecked.svg";

function ReviewEditForm() {
    const { postId } = useParams();
    const numericPostId = parseInt(postId);
    const navigate = useNavigate();

    const [reviewId, setReviewId] = useState(null);
    const [meta, setMeta] = useState(null);
    const [comment, setComment] = useState("");
    const [selectedTags, setSelectedTags] = useState([]);
    const [allMannerTags, setAllMannerTags] = useState([]);

    useEffect(() => {
        const fetchTagsAndReview = async () => {
            try {
                const tagsRes = await axiosClient.get("/api/reviews/tags");
                const tagsFromServer = tagsRes.data.data.map(tag => ({
                    id: tag.id,
                    label: tag.tagName
                }));
                setAllMannerTags(tagsFromServer);

                const reviewRes = await axiosClient.get(`/api/reviews/post/${numericPostId}`);
                const review = reviewRes.data.data;
                setReviewId(review.reviewId);
                setComment(review.comment);

                const resolvedIds = review.mannerTag.map(tagName => {
                    const found = tagsFromServer.find(t => t.label === tagName);
                    return found ? found.id : null;
                }).filter(id => id !== null);

                setSelectedTags(resolvedIds);
            } catch (err) {
                console.error("태그 또는 리뷰 로딩 실패:", err);
            }
        };

        fetchTagsAndReview();
    }, [numericPostId]);

    useEffect(() => {
        axiosClient.get(`/api/reviews/${numericPostId}/meta`)
            .then(res => setMeta(res.data.data))
            .catch(err => console.error("메타 정보 로딩 실패:", err));
    }, [numericPostId]);

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
            await axiosClient.put(`/api/reviews/${reviewId}`, payload);
            alert("리뷰 수정 완료!");
            navigate("/information");
        } catch (err) {
            console.error("리뷰 수정 실패:", err);
            alert("리뷰 수정 실패");
        }
    };

    return (
        <div>
            <AppHeader />
            <form onSubmit={handleSubmit} className="review-form">
                <h2 className="page-title">동행 후기 수정</h2>

                <div className="form-wrapper">
                    <div className="profile-section">
                        <div className="profile-column">
                            <div className="profile-label">동행자</div>
                            {meta && (
                                <CompanionCard
                                    name={meta.companionName}
                                    imageSrc={meta.companionImageUrl}
                                />
                            )}
                        </div>
                        <div className="profile-column">
                            <div className="profile-label">나의 동행</div>
                            {meta && (
                                <MyActivityCard
                                    title={meta.title}
                                    description={meta.description}
                                    location={meta.location}
                                    date={meta.date}
                                    imageSrc={meta.imageUrl}
                                />
                            )}
                        </div>
                    </div>

                    <hr className="divider" />

                    <div className="question-section">
                        <div className="question">어떤 점이 좋으셨나요?</div>
                        <div className="manner-tags-grid">
                            {allMannerTags.slice(0, 4).map(tag => {
                                const isSelected = selectedTags.includes(tag.id);
                                return (
                                    <div
                                        key={tag.id}
                                        className={`manner-tag ${isSelected ? "selected" : ""}`}
                                        onClick={() => toggleTag(tag.id)}
                                        role="button"
                                        tabIndex={0}
                                        onKeyDown={(e) => {
                                            if (e.key === "Enter" || e.key === " ") toggleTag(tag.id);
                                        }}
                                    >
                                        <span className="icon-wrapper">
                                            {isSelected ? <CheckedIcon /> : <UncheckedIcon />}
                                        </span>
                                        <span className="tag-label">{tag.label}</span>
                                    </div>
                                );
                            })}
                        </div>

                        <div className="question">어떤 점이 아쉬우셨나요?</div>
                        <div className="manner-tags-grid">
                            {allMannerTags.slice(4).map(tag => {
                                const isSelected = selectedTags.includes(tag.id);
                                return (
                                    <div
                                        key={tag.id}
                                        className={`manner-tag ${isSelected ? "selected" : ""}`}
                                        onClick={() => toggleTag(tag.id)}
                                        role="button"
                                        tabIndex={0}
                                        onKeyDown={(e) => {
                                            if (e.key === "Enter" || e.key === " ") toggleTag(tag.id);
                                        }}
                                    >
                                        <span className="icon-wrapper">
                                            {isSelected ? <CheckedIcon /> : <UncheckedIcon />}
                                        </span>
                                        <span className="tag-label">{tag.label}</span>
                                    </div>
                                );
                            })}
                        </div>
                    </div>

                    <div className="question">동행 후기</div>
                    <textarea
                        className="review-textarea"
                        placeholder="동행에 대한 후기를 입력해 주세요"
                        value={comment}
                        onChange={e => setComment(e.target.value)}
                        required
                    />
                </div>

                <button className="submit-button" type="submit">후기 수정하기</button>
            </form>
        </div>
    );
}

export default ReviewEditForm;
