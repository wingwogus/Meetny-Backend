// src/components/Login.jsx
import React, { useState } from 'react'
import axiosClient from '../api/axiosClient'
import { useNavigate } from 'react-router-dom'
import {jwtDecode} from 'jwt-decode'
import '../styles/Login.css';


const Login = () => {
    const [username, setUsername] = useState('')
    const [pw, setPw] = useState('')
    const navigate = useNavigate()

    const handleSubmit = async e => {
        e.preventDefault()
        try {
            const response = await axiosClient.post('/api/auth/login', { username, password: pw });
            const { accessToken, refreshToken } = response.data.data;
            localStorage.setItem('accessToken', accessToken);
            localStorage.setItem('refreshToken', refreshToken);
            const decoded = jwtDecode(accessToken);
            localStorage.setItem('nickname', decoded.nickname);
            localStorage.setItem('profileImg', decoded.profileImg);
            navigate('/information')
        } catch (err) {
            console.error(err)
            alert(err.response?.data?.message || '로그인 실패')
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            {/* Anima에서 내보낸 JSX + CSS를 여기에 사용 */}

            <div className="login-screen">
              <div className="mypage" data-model-id="721:2">
                <div className="div">
                    <button type="submit" className="overlap login-button">
                        <div className="text-wrapper">로그인</div>
                    </button>

                    <div className="overlap-group"
                    onClick={() => navigate('/register')}>
                        <div className="text-wrapper-2">회원가입</div>
                    </div>

                    <div className="group">
                        <div className="div-wrapper">
                            <input
                                type="text"
                                name="username"
                                placeholder="아이디를 입력해주세요"
                                value={username}
                                onChange={e => setUsername(e.target.value)}
                                className="input-field"
                                required
                            />
                        </div>
                        <div className="text-wrapper-4">아이디</div>
                    </div>

                    <div className="text-wrapper-5">로그인</div>

                    <div className="group-2">
                        <div className="div-wrapper">
                            <input
                                type="password"
                                name="password"
                                placeholder="비밀번호를 입력해주세요"
                                value={pw}
                                onChange={e => setPw(e.target.value)}
                                className="input-field"
                                required
                            />
                        </div>
                        <div className="text-wrapper-4">비밀번호</div>
                    </div>

                    <div className="text-wrapper-6">아이디 찾기</div>

                    <img
                        className="vector"
                        alt="Vector"
                        src="https://c.animaapp.com/6EfA1y0Z/img/vector-1.svg"
                    />

                    <div className="text-wrapper-7">비밀번호 찾기</div>

                    <div className="register-container">
                        <a href="http://localhost:8080/oauth2/authorization/kakao">
                            <img
                                className="img"
                                alt="Kakao Login"
                                src="https://c.animaapp.com/6EfA1y0Z/img/group@2x.png"
                            />
                        </a>

                        <a href="http://localhost:8080/oauth2/authorization/naver">
                          <img
                            className="group-3"
                            alt="Naver Login"
                            src="https://c.animaapp.com/6EfA1y0Z/img/group-1@2x.png"
                          />
                        </a>

                        <img
                            className="group-4"
                            alt="Group"
                            src="https://c.animaapp.com/6EfA1y0Z/img/group-2@2x.png"
                        />

                        <div className="overlap-group-wrapper">
                          <div className="clip-path-group-wrapper">
                            <a href="http://localhost:8080/oauth2/authorization/google">
                              <img
                                className="clip-path-group"
                                alt="Google Login"
                                src="https://c.animaapp.com/6EfA1y0Z/img/clip-path-group@2x.png"
                              />
                            </a>
                          </div>
                        </div>
                    </div>

                    <div className="logo">
                        <img
                            className="group-5"
                            alt="Group"
                            src="https://c.animaapp.com/6EfA1y0Z/img/group-3@2x.png"
                        />

                        <div className="group-6">
                            <div className="overlap-group-2">
                                <img
                                    className="vector-2"
                                    alt="Vector"
                                    src="https://c.animaapp.com/6EfA1y0Z/img/vector-2.svg"
                                />

                                <img
                                    className="vector-2"
                                    alt="Vector"
                                    src="https://c.animaapp.com/6EfA1y0Z/img/vector-3.svg"
                                />

                                <img
                                    className="group-7"
                                    alt="Group"
                                    src="https://c.animaapp.com/6EfA1y0Z/img/group-4@2x.png"
                                />

                                <img
                                    className="vector-3"
                                    alt="Vector"
                                    src="https://c.animaapp.com/6EfA1y0Z/img/vector-4.svg"
                                />
                            </div>
                        </div>
                    </div>

                    <img
                        className="vector-4"
                        alt="Vector"
                        src="https://c.animaapp.com/6EfA1y0Z/img/vector-16.svg"
                    />
                </div>
              </div>  {/* closing for .screen */}
            </div>  {/* closing for .login-screen */}
        </form>
    )
}
export default Login;