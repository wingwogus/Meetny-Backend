// src/components/PostForm.jsx
import React, { useState } from 'react';
import axios from 'axios';

const PostForm = () => {
    // DTO 에 맞춘 초기 상태
    const [form, setForm] = useState({
        title: '',
        content: '',
        photo: '',
        meetingTime: '',
        address: { city: '', street: '', zipcode: '' },
        // tag: {
        //     // TODO: Tag 클래스에 정의된 필드명으로 초기값 채우기
        //     // 예: name: '', category: ''
        // }
    });
    const [statusMsg, setStatusMsg] = useState('');

    // 입력 값 변경 핸들러
    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name.startsWith('address.')) {
            const key = name.split('.')[1];
            setForm(prev => ({
                ...prev,
                address: { ...prev.address, [key]: value }
            }));
        }
        // else if (name.startsWith('tag.')) {
        //     const key = name.split('.')[1];
        //     setForm(prev => ({
        //         ...prev,
        //         tag: { ...prev.tag, [key]: value }
        //     }));
        // }
        else {
            setForm(prev => ({ ...prev, [name]: value }));
        }
    };

    // 폼 제출 핸들러
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const res = await axios.post(
                '/api/posts',
                form,
                { withCredentials: true }       // JSESSIONID 쿠키 자동 포함
            );
            console.log('생성된 포스트:', res.data);
            setStatusMsg('포스트가 성공적으로 작성되었습니다.');
        } catch (err) {
            console.error(err);
            setStatusMsg(
                err.response?.data?.message
                || '포스트 작성 중 오류가 발생했습니다.'
            );
        }
    };

    return (
        <div style={{ maxWidth: 600, margin: '2rem auto' }}>
            <h2>포스트 작성</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    제목
                    <input
                        name="title"
                        value={form.title}
                        onChange={handleChange}
                        required
                    />
                </label>

                <label>
                    내용
                    <textarea
                        name="content"
                        value={form.content}
                        onChange={handleChange}
                        required
                    />
                </label>

                <label>
                    사진 URL
                    <input
                        name="photo"
                        value={form.photo}
                        onChange={handleChange}
                    />
                </label>

                <label>
                    모임 시간
                    <input
                        type="datetime-local"
                        name="meetingTime"
                        value={form.meetingTime}
                        onChange={handleChange}
                        required
                    />
                </label>

                <fieldset>
                    <legend>주소</legend>
                    <label>
                        도시
                        <input
                            name="address.city"
                            value={form.address.city}
                            onChange={handleChange}
                        />
                    </label>
                    <label>
                        도로명
                        <input
                            name="address.street"
                            value={form.address.street}
                            onChange={handleChange}
                        />
                    </label>
                    <label>
                        우편번호
                        <input
                            name="address.zipcode"
                            value={form.address.zipcode}
                            onChange={handleChange}
                        />
                    </label>
                </fieldset>

                {/*<fieldset>*/}
                {/*    <legend>태그</legend>*/}
                {/*    /!* Tag 클래스에 정의된 필드명에 맞춰서 아래 input을 추가/수정하세요 *!/*/}
                {/*    <label>*/}
                {/*        Tag 이름*/}
                {/*        <input*/}
                {/*            name="tag.name"*/}
                {/*            value={form.tag.name || ''}*/}
                {/*            onChange={handleChange}*/}
                {/*        />*/}
                {/*    </label>*/}
                {/*</fieldset>*/}

                <button type="submit">작성하기</button>
            </form>

            {statusMsg && <p>{statusMsg}</p>}
        </div>
    );
};

export default PostForm;