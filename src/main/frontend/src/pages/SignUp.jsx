import React, { useState } from 'react';         // React 라이브러리와 상태 관리를 위한 useState 훅을 불러옵니다.
import axios from 'axios';                        // HTTP 요청을 쉽게 보내기 위한 axios 라이브러리를 불러옵니다.

const SignupForm = () => {                       // SignupForm이라는 함수형 컴포넌트를 선언합니다.
    const [form, setForm] = useState({              // form 상태와 상태 변경 함수 setForm을 선언하고 초기값을 설정합니다.
        username: '',                                 // └─ username 필드를 빈 문자열로 초기화
        nickname: '',                                 // └─ nickname 필드를 빈 문자열로 초기화
        password: '',                                 // └─ password 필드를 빈 문자열로 초기화
        phone: '',                                    // └─ phone 필드를 빈 문자열로 초기화
        mail: '',                                     // └─ mail 필드를 빈 문자열로 초기화
        profileImg: '',                               // └─ profileImg 필드를 빈 문자열로 초기화
        gender: 'MALE',                               // └─ gender 필드를 기본값 'MALE'로 초기화
        address: {                                    // └─ address 객체를 중첩 상태로 초기화
            city: '',                                   //    └─ city 필드를 빈 문자열로 초기화
            street: '',                                 //    └─ street 필드를 빈 문자열로 초기화
            zipcode: ''                                 //    └─ zipcode 필드를 빈 문자열로 초기화
        },
        userTag: {                                    // └─ userTag 객체를 중첩 상태로 초기화
            tag1: '',                                   //    └─ tag1 필드를 빈 문자열로 초기화
            tag2: ''                                    //    └─ tag2 필드를 빈 문자열로 초기화
        }
    });

    const [statusMsg, setStatusMsg] = useState(''); // 서버 응답이나 오류 메시지를 보여줄 statusMsg 상태를 선언합니다.

    const handleChange = (e) => {                   // 입력값이 바뀔 때마다 호출되는 이벤트 핸들러를 선언합니다.
        const { name, value } = e.target;             // 이벤트 대상(input)의 name과 value 속성을 추출합니다.
        if (name.startsWith('address.')) {            // name이 "address."로 시작하면 주소 객체를 업데이트합니다.
            const key = name.split('.')[1];             // "address.city" → ["address", "city"] 중 city를 key로 사용
            setForm(prev => ({                          // 이전 상태를 복사한 뒤 address 하위 키만 변경
                ...prev,
                address: { ...prev.address, [key]: value }
            }));
        }
        else if (name.startsWith('userTag.')) {      // name이 "userTag."로 시작하면 태그 객체를 업데이트합니다.
            const key = name.split('.')[1];             // "userTag.tag1" → tag1을 key로 사용
            setForm(prev => ({
                ...prev,
                userTag: { ...prev.userTag, [key]: value }
            }));
        }
        else {                                      // 그 외 일반 필드는 최상위 필드를 업데이트합니다.
            setForm(prev => ({ ...prev, [name]: value }));
        }
    };

    const handleSubmit = async (e) => {             // 폼 제출 시 실행되는 비동기 함수입니다.
        e.preventDefault();                           // 페이지 리로드를 막기 위해 기본 동작을 취소합니다.
        try {
            const res = await axios.post('/api/signup', form);  // /api/signup으로 form 데이터를 POST 요청합니다.
            setStatusMsg('회원가입이 완료되었습니다!');            // 성공 시 사용자에게 완료 메시지를 보여줍니다.
            console.log('응답 데이터:', res.data);              // 디버깅 용도로 서버 응답을 콘솔에 출력합니다.
        } catch (err) {
            console.error(err);                                      // 오류를 콘솔에 출력합니다.
            setStatusMsg(err.response?.data?.message                   // 서버에서 받은 오류 메시지를 statusMsg에 설정하거나
                || '회원가입 중 오류가 발생했습니다.');                // 기본 오류 문구를 보여줍니다.
        }
    };

    return (                                       // 화면에 렌더링할 JSX를 반환합니다.
        <div style={{ maxWidth: 500, margin: '2rem auto' }}>  {/* 가운데 정렬된 컨테이너 */}
            <h2>회원가입</h2>                           {/* 제목 */}
            <form onSubmit={handleSubmit}>              {/* 제출 시 handleSubmit 호출 */}
                <label>아이디
                    <input
                        name="username"                      // 상태 키 이름과 일치해야 handleChange에서 처리 가능
                        value={form.username}                // form.username 값을 입력 요소에 바인딩
                        onChange={handleChange}              // 입력 값이 바뀔 때 handleChange 호출
                        required
                    />
                </label><br/>
                <label>닉네임
                    <input
                        name="nickname"
                        value={form.nickname}
                        onChange={handleChange}
                        required
                    />
                </label><br/>
                <label>비밀번호
                    <input
                        type="password"                     // 비밀번호 입력을 가려줍니다.
                        name="password"
                        value={form.password}
                        onChange={handleChange}
                        required
                    />
                </label><br/>
                <label>전화번호
                    <input
                        name="phone"
                        value={form.phone}
                        onChange={handleChange}
                    />
                </label><br/>
                <label>이메일
                    <input
                        name="mail"
                        value={form.mail}
                        onChange={handleChange}
                    />
                </label><br/>
                <label>프로필 이미지 URL
                    <input
                        name="profileImg"
                        value={form.profileImg}
                        onChange={handleChange}
                    />
                </label><br/>
                <label>성별
                    <select
                        name="gender"
                        value={form.gender}
                        onChange={handleChange}
                    >
                        <option value="MALE">남성</option>
                        <option value="FEMALE">여성</option>
                    </select>
                </label>
                <fieldset>                                 {/* 주소 입력을 그룹화 */}
                    <legend>주소</legend>
                    <label>도시
                        <input
                            name="address.city"                // address.city라는 name으로 nested 처리
                            value={form.address.city}
                            onChange={handleChange}
                        />
                    </label><br/>
                    <label>도로명
                        <input
                            name="address.street"
                            value={form.address.street}
                            onChange={handleChange}
                        />
                    </label><br/>
                    <label>우편번호
                        <input
                            name="address.zipcode"
                            value={form.address.zipcode}
                            onChange={handleChange}
                        />
                    </label>
                </fieldset>
                <fieldset>                                 {/* 태그 입력을 그룹화 */}
                    <legend>태그</legend>
                    <label>태그1
                        <input
                            name="userTag.tag1"                // userTag.tag1이라는 name으로 nested 처리
                            value={form.userTag.tag1}
                            onChange={handleChange}
                        />
                    </label><br/>
                    <label>태그2
                        <input
                            name="userTag.tag2"
                            value={form.userTag.tag2}
                            onChange={handleChange}
                        />
                    </label><br/>
                </fieldset>
                <button type="submit">가입하기</button>
                {/* 폼 제출 버튼 */}
            </form>

            {statusMsg && <p>{statusMsg}</p>}          {/* statusMsg가 있으면 메시지 출력 */}
        </div>
    );
};

export default SignupForm;                       // 이 컴포넌트를 외부에서 import할 수 있게 내보냅니다.