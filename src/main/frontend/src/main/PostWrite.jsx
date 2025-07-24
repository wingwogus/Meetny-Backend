import React, { useState , useEffect } from "react";
import axios from "../api/axiosClient";
import "../styles/PostWrite.css";
import Header from "../components/AppHeader";

export const PostWrite = () => {
    const [title, setTitle] = useState("");
    const [date, setDate] = useState("");
    const [details, setDetails] = useState("");
    const [images, setImages] = useState([]);
    const [previewImages, setPreviewImages] = useState([]);
    const [address, setAddress] = useState({
        city: "",    // 예: 서울특별시
        town: "",    // 예: 은평구
        street: ""   // 예: 응암동
    });

    const [selectedCategory, setSelectedCategory] = useState("");
    const [filteredTags, setFilteredTags] = useState([]);
    const [selectedTagId, setSelectedTagId] = useState(null);
    const [allTags, setAllTags] = useState([]);

    const categories = ["Concert", "Sports", "Movie", "Culture"];

    useEffect(() => {
        axios.get("/api/tags")
            .then(res => {
                setAllTags(res.data.data);  // 전체 태그 목록 저장
            })
            .catch(err => {
                console.error("전체 태그 로딩 실패", err);
            });
    }, []);

    useEffect(() => {
        if (selectedCategory) {
            axios.get(`/api/tags/${selectedCategory}`)
                .then(res => setFilteredTags(res.data.data))
                .catch(err => {
                    console.error("태그 로딩 실패", err);
                    setFilteredTags([]);
                });
        }
    }, [selectedCategory]);

    const handleCategoryChange = async (e) => {
        const category = e.target.value;
        setSelectedCategory(category);
        setSelectedTagId(null);

        try {
            const res = await axios.get(`/api/tags/${category}`);
            setFilteredTags(res.data.data);
        } catch (err) {
            console.error("태그 불러오기 실패", err);
            setFilteredTags([]);
        }
    };


    const handleImageChange = (e) => {
        const files = Array.from(e.target.files);
        setImages(files);
        setPreviewImages(files.map(file => URL.createObjectURL(file)));
        e.target.value = null;
    };

    const handleFindAddress = () => {
        new window.daum.Postcode({
            oncomplete: function (data) {
                const city = data.sido;              // 시
                const street = data.sigungu;         // 구
                const town = data.bname || data.roadname || ""; // 동 (또는 도로명)

                setAddress({ city, street, town });
            },
        }).open();
    };

    const handleRemoveImage = (index) => {
        const newImages = [...images];
        const newPreviews = [...previewImages];
        newImages.splice(index, 1);
        newPreviews.splice(index, 1);
        setImages(newImages);
        setPreviewImages(newPreviews);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!selectedTagId) {
            alert("태그를 선택해 주세요.");
            return;
        }

        try {
            const fullMeetingTime = `${date}T00:00:00`;
            const postData = {
                title,
                content: details,
                photo: 'https://picsum.photos/200/300', // S3 업로드된 이미지 URL 또는 임시값
                meetingTime: fullMeetingTime,
                address: {
                    city: address.city,
                    town: address.town,
                    street: address.street
                },
                    tagId: selectedTagId

            };

            const res = await axios.post("/api/posts", postData);
            alert("게시글이 성공적으로 등록되었습니다!");

            window.location.href = "/posts"
        } catch (err) {
            console.error("게시글 등록 실패", err);
            alert("등록에 실패했습니다.");
        }
    };

    return (
        <>
            <Header />
        <div className="post-write-container">
            {/* 동행 만들기 섹션 */}
                <div className="section-wrapper">
                <h2 className="section-title">
                    <span className="dot green" /> 동행 만들기
                </h2>

                {/* 제목 */}
                    <div className="section-box">
                <div className="form-group">
                    <label>동행 제목 <span>*</span></label>
                    <input
                        type="text"
                        maxLength={50}
                        placeholder="제목을 입력해 주세요 (50자 이내)"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                    />
                    <div className="char-count">{title.length}/50</div>
                </div>

                {/* 장소 */}
                <div className="form-group">
                    <label>동행 장소</label>
                    <input
                        type="text"
                        placeholder="장소를 입력해 주세요"
                        value={`${address.city} ${address.street} ${address.town}`}
                        onClick={handleFindAddress}
                        readOnly // 사용자가 직접 입력 못하게 함
                    />
                </div>

                {/* 날짜 */}
                <div className="form-group">
                    <label>동행 기간 설정</label>
                    <input
                        type="date"
                        value={date}
                        onChange={(e) => setDate(e.target.value)}
                    />
                </div>

                {/* 상세 정보 */}
                <div className="form-group">
                    <label>동행 상세 정보 <span>*</span></label>
                    <textarea
                        placeholder="동행에 대한 상세 정보를 입력해 주세요"
                        value={details}
                        onChange={(e) => setDetails(e.target.value)}
                    />
                </div>

                {/* 이미지 등록 */}
                <div className="form-group">
                    <label>동행 이미지 등록</label>
                    <div className="image-upload-area">
                        <label htmlFor="image-upload" className="upload-box">+</label>
                        <input
                            id="image-upload"
                            type="file"
                            multiple
                            accept="image/*"
                            onChange={handleImageChange}
                        />
                        <div className="preview-images">
                            {previewImages.map((src, idx) => (
                                <div key={idx} className="preview-container">
                                    <img src={src} alt={`preview-${idx}`} />
                                    <button onClick={() => handleRemoveImage(idx)}>×</button>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            </div>
            </div>


            {/* 카테고리 선택 영역 */}
            <div className="section-wrapper">
                <h2 className="section-title">
                    <span className="dot orange" /> 동행 카테고리
                </h2>

                <div className="section-box-category">
                    <div className="category-group">
                        <label className="category-label">카테고리 <span>*</span></label>
                        <div className="category-buttons">
                            {categories.map((cat, idx) => (
                                <button
                                    key={idx}
                                    className={`category-btn ${selectedCategory === cat ? 'selected' : ''}`}
                                    onClick={() => handleCategoryChange({ target: { value: cat } })}
                                >
                                    {cat}
                                </button>
                            ))}
                        </div>
                    </div>

                    {filteredTags.length > 0 && (
                        <div className="tag-group">
                            <label className="category-label">태그 선택 <span>*</span></label>
                            <div className="tag-buttons">
                                {filteredTags.map(tag => (
                                    <button
                                        key={tag.id}
                                        className={`tag-btn ${selectedTagId === tag.id ? 'selected' : ''}`}
                                        onClick={() => setSelectedTagId(tag.id)}
                                    >
                                        {tag.tagName}
                                    </button>
                                ))}
                            </div>
                        </div>
                    )}
                </div>
            </div>

            {/* 버튼 */}
            <div className="button-area">
                <button className="submit" onClick={handleSubmit}>동행 만들기</button>
                <button className="save">임시저장</button>
            </div>
        </div>
        </>
    );
};
export default PostWrite;