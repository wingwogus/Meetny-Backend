// src/pages/MyPage.jsx
import React, { useState, useEffect } from "react";
import axiosClient from '../api/axiosClient'
import "../styles/Mypage.css";

export default function MyPage() {
    const [user, setUser] = useState(null);
    const [errorMsg, setErrorMsg] = useState("");

    useEffect(() => {
        const fetchMyPage = async () => {
            try {
                const token = localStorage.getItem("accessToken");
                const res = await axiosClient.get("/api/my-page/information", {
                    headers: { Authorization: `Bearer ${token}` },
                });
                setUser(res.data.data);
            } catch (err) {
                console.error(err);
                setErrorMsg("회원 정보를 불러오는 중 오류가 발생했습니다.");
            }
        };
        fetchMyPage();
    }, []);

    if (errorMsg) {
        return (
            <div className="min-h-screen flex items-center justify-center">
                <p className="text-red-500">{errorMsg}</p>
            </div>
        );
    }
    if (!user) {
        return (
            <div className="min-h-screen flex items-center justify-center">
                <p>로딩 중...</p>
            </div>
        );
    }

    return (
        <div className="mypage-screen">
            <div className="mypage" data-model-id="722:240">
            <div className="div">
                <div className="overlap">
                    <img
                        className="rectangle"
                        alt="Rectangle"
                        src="https://c.animaapp.com/3LplbCFc/img/rectangle-73.png"
                    />

                    <div className="rectangle-2" />

                    <div className="rectangle-3" />

                    <div className="rectangle-4" />

                    <div className="text-wrapper">콘서트</div>

                    <div className="text-wrapper-2">역동적/활동적</div>

                    <div className="rectangle-5" />

                    <div className="rectangle-6" />

                    <div className="text-wrapper-3">음악</div>

                    <div className="text-wrapper-4">맛집투어</div>

                    <div className="rectangle-7" />

                    <div className="rectangle-8" />

                    <img
                        className="mask-group"
                        alt="Mask group"
                        src="https://c.animaapp.com/3LplbCFc/img/mask-group@2x.png"
                    />

                    <p className="element">
                        <span className="span">동행 </span>

                        <span className="text-wrapper-5">{user.posts.length}회 참여</span>

                        <span className="span"> 인증</span>
                    </p>

                    <div className="text-wrapper-6">(최근 5개월 기준)</div>

                    <div className="text-wrapper-7">{user.nickname}</div>

                    <div className="text-wrapper-8"></div>

                    <div className="text-wrapper-9">동행 신뢰도</div>

                    <div className="rectangle-9" />

                    <div className="rectangle-10" />

                    <div className="rectangle-11" />

                    <div className="rectangle-12" />

                    <div className="rectangle-13" />

                    <div className="text-wrapper-10">47.3</div>

                    <p className="p">
                        “지속 가능한 삶과 자기개발을 중요하게 생각해요 :) ”
                    </p>

                    <div className="text-wrapper-11">{user.tags[0]}</div>

                    <div className="text-wrapper-12">{user.tags[1]}</div>

                    <div className="text-wrapper-13">{user.tags[2]}</div>

                    <div className="text-wrapper-14">{user.tags[3]}</div>

                    <div className="text-wrapper-15">{user.nickname} 님의 동행 키워드</div>

                    <div className="text-wrapper-16">게시물</div>

                    <div className="text-wrapper-17">{user.posts.length}</div>

                    <div className="text-wrapper-18">{user.followerCount}</div>

                    <div className="text-wrapper-19">{user.followingCount}</div>

                    <div className="text-wrapper-20">팔로워</div>

                    <div className="text-wrapper-21">팔로잉</div>

                    <div className="ellipse" />

                    <div className="rectangle-14" />

                    <div className="rectangle-15" />

                    <div className="rectangle-16" />

                    <div className="rectangle-17" />

                    <div className="rectangle-18" />

                    <div className="text-wrapper-22">우수 동행자</div>

                    <img
                        className="vector"
                        alt="Vector"
                        src="https://c.animaapp.com/3LplbCFc/img/vector-25.svg"
                    />

                    <img
                        className="img"
                        alt="Vector"
                        src="https://c.animaapp.com/3LplbCFc/img/vector-26.svg"
                    />

                    <img
                        className="vector-2"
                        alt="Vector"
                        src="https://c.animaapp.com/3LplbCFc/img/vector-27.svg"
                    />

                    <img
                        className="vector-3"
                        alt="Vector"
                        src="https://c.animaapp.com/3LplbCFc/img/vector-28.svg"
                    />

                    <img
                        className="element-2"
                        alt="Element"
                        src="https://c.animaapp.com/3LplbCFc/img/-----1-12.svg"
                    />
            </div>
        </div>

                <div className="svg" />

                <img
                    className="mask-group-2"
                    alt="Mask group"
                    src="https://c.animaapp.com/3LplbCFc/img/mask-group-1@2x.png"
                />

                <div className="text-wrapper-23">동행 후기</div>

                <div className="text-wrapper-24">동행 게시글</div>

                <div className="text-wrapper-25">참가한 동행</div>

                <div className="text-wrapper-26">피드</div>

                <div className="logo">
                    <img
                        className="group"
                        alt="Group"
                        src="https://c.animaapp.com/3LplbCFc/img/group@2x.png"
                    />

                    <div className="overlap-group-wrapper">
                        <div className="overlap-group">
                            <img
                                className="vector-4"
                                alt="Vector"
                                src="https://c.animaapp.com/3LplbCFc/img/vector.svg"
                            />

                            <img
                                className="vector-4"
                                alt="Vector"
                                src="https://c.animaapp.com/3LplbCFc/img/vector-1.svg"
                            />

                            <img
                                className="group-2"
                                alt="Group"
                                src="https://c.animaapp.com/3LplbCFc/img/group-1@2x.png"
                            />

                            <img
                                className="vector-5"
                                alt="Vector"
                                src="https://c.animaapp.com/3LplbCFc/img/vector-2.svg"
                            />
                        </div>
                    </div>
                </div>

                <img
                    className="vector-6"
                    alt="Vector"
                    src="https://c.animaapp.com/3LplbCFc/img/vector-16.svg"
                />

                <div className="overlap-2">
                    <p className="text-wrapper-27">
                        좋은 날씨에 친절하고 마음
                        <br />
                        통하는 분들과 함께 시간...
                    </p>

                    <div className="text-wrapper-28">
                        한강 피크닉 수다
                        <br />
                        도시락 모임
                    </div>

                    <div className="overlap-3">
                        <img
                            className="mask-group-3"
                            alt="Mask group"
                            src="https://c.animaapp.com/3LplbCFc/img/mask-group-6@2x.png"
                        />

                        <div className="rectangle-19" />

                        <div className="text-wrapper-29">
                            종료된
                            <br />
                            동행
                        </div>
                    </div>

                    <div className="text-wrapper-30">6/8</div>

                    <div className="text-wrapper-31">25/03/12</div>

                    <img
                        className="element-3"
                        alt="Element"
                        src="https://c.animaapp.com/3LplbCFc/img/-----1-6.svg"
                    />

                    <img
                        className="element-4"
                        alt="Element"
                        src="https://c.animaapp.com/3LplbCFc/img/-----1-9.svg"
                    />
                </div>

                <div className="overlap-4">
                    <p className="text-wrapper-32">
                        좋은 날씨에 친절하고 마음
                        <br />
                        통하는 분들과 함께 시간...
                    </p>

                    <div className="text-wrapper-33">차박 캠핑 모임과 불멍</div>

                    <div className="overlap-5">
                        <img
                            className="mask-group-4"
                            alt="Mask group"
                            src="https://c.animaapp.com/3LplbCFc/img/mask-group-7@2x.png"
                        />

                        <div className="rectangle-19" />

                        <div className="text-wrapper-34">
                            종료된
                            <br />
                            동행
                        </div>
                    </div>

                    <div className="text-wrapper-35">6/8</div>

                    <div className="text-wrapper-36">25/03/12</div>

                    <img
                        className="element-5"
                        alt="Element"
                        src="https://c.animaapp.com/3LplbCFc/img/-----1-7.svg"
                    />

                    <img
                        className="element-6"
                        alt="Element"
                        src="https://c.animaapp.com/3LplbCFc/img/-----1-10.svg"
                    />
                </div>

                <div className="overlap-6">
                    <p className="text-wrapper-37">
                        좋은 날씨에 친절하고 마음
                        <br />
                        통하는 분들과 함께 시간...
                    </p>

                    <div className="text-wrapper-38">
                        한강 - [채식주의자]
                        <br />
                        독서 모임
                    </div>

                    <div className="overlap-3">
                        <img
                            className="mask-group-3"
                            alt="Mask group"
                            src="https://c.animaapp.com/3LplbCFc/img/mask-group-8@2x.png"
                        />

                        <div className="rectangle-19" />

                        <div className="text-wrapper-39">
                            종료된
                            <br />
                            동행
                        </div>
                    </div>

                    <div className="text-wrapper-40">6/8</div>

                    <div className="text-wrapper-41">25/03/12</div>

                    <img
                        className="element-5"
                        alt="Element"
                        src="https://c.animaapp.com/3LplbCFc/img/-----1-8.svg"
                    />

                    <img
                        className="element-7"
                        alt="Element"
                        src="https://c.animaapp.com/3LplbCFc/img/-----1-11.svg"
                    />
                </div>

                <div className="overlap-7">
                    <div className="text-wrapper-42">동행 관리</div>

                    <img
                        className="mask-group-5"
                        alt="Mask group"
                        src="https://c.animaapp.com/3LplbCFc/img/mask-group-2@2x.png"
                    />

                    <img
                        className="mask-group-6"
                        alt="Mask group"
                        src="https://c.animaapp.com/3LplbCFc/img/mask-group-3@2x.png"
                    />

                    <img
                        className="mask-group-7"
                        alt="Mask group"
                        src="https://c.animaapp.com/3LplbCFc/img/mask-group-4@2x.png"
                    />

                    <img
                        className="mask-group-8"
                        alt="Mask group"
                        src="https://c.animaapp.com/3LplbCFc/img/mask-group-5@2x.png"
                    />

                    <p className="text-wrapper-43">아티스트 DPR LIVE 콘서트 동행</p>

                    <p className="text-wrapper-44">기억에 가장 남는 도서와 힐링</p>

                    <p className="text-wrapper-45">아티스트 박유훈 기타 콘서트 동행</p>

                    <p className="text-wrapper-46">감성 음악과 함께하는 한강 요가</p>

                    <div className="text-wrapper-47">연남동</div>

                    <div className="text-wrapper-48">연남동</div>

                    <div className="text-wrapper-49">연남동</div>

                    <div className="text-wrapper-50">연남동</div>

                    <div className="text-wrapper-51">25.05.18</div>

                    <div className="text-wrapper-52">25.05.18</div>

                    <div className="text-wrapper-53">25.03.18</div>

                    <div className="text-wrapper-54">25.03.18</div>

                    <div className="text-wrapper-55">11/15</div>

                    <div className="text-wrapper-56">11/15</div>

                    <div className="text-wrapper-57">05/05</div>

                    <div className="text-wrapper-58">05/05</div>

                    <img
                        className="element-8"
                        alt="Element"
                        src="https://c.animaapp.com/3LplbCFc/img/-----1.svg"
                    />

                    <img
                        className="element-9"
                        alt="Element"
                        src="https://c.animaapp.com/3LplbCFc/img/-----1-1.svg"
                    />

                    <img
                        className="element-10"
                        alt="Element"
                        src="https://c.animaapp.com/3LplbCFc/img/-----1-2.svg"
                    />

                    <img
                        className="element-11"
                        alt="Element"
                        src="https://c.animaapp.com/3LplbCFc/img/-----1-3.svg"
                    />

                    <img
                        className="element-12"
                        alt="Element"
                        src="https://c.animaapp.com/3LplbCFc/img/-----1-4.svg"
                    />

                    <img
                        className="element-13"
                        alt="Element"
                        src="https://c.animaapp.com/3LplbCFc/img/-----1-5.svg"
                    />

                    <img
                        className="vector-7"
                        alt="Vector"
                        src="https://c.animaapp.com/3LplbCFc/img/vector-29.svg"
                    />

                    <img
                        className="vector-8"
                        alt="Vector"
                        src="https://c.animaapp.com/3LplbCFc/img/vector-20.svg"
                    />
                </div>

                <img className="element-14" alt="Element" src="/img/1.svg" />

                <img className="element-15" alt="Element" src="/img/image.svg" />

                <img className="element-16" alt="Element" src="/img/1-2.svg" />

                <img className="element-17" alt="Element" src="/img/1-3.svg" />

                <img className="element-18" alt="Element" src="/img/1-4.svg" />

                <img className="element-19" alt="Element" src="/img/1-5.svg" />

                <div className="text-wrapper-59">마이페이지</div>

                <div className="text-wrapper-60">동행 관리</div>

                <div className="text-wrapper-61">내 게시글</div>

                <div className="text-wrapper-62">마이페이지</div>

                <div className="text-wrapper-63">신뢰도 관리</div>

                <div className="text-wrapper-64">팔로워/팔로잉</div>

                <div className="text-wrapper-65">찜 게시물</div>

                <div className="text-wrapper-66">동행 후기</div>

                <div className="text-wrapper-67">필터 관리</div>

                <div className="text-wrapper-68">설정</div>

                <img
                    className="vector-9"
                    alt="Vector"
                    src="https://c.animaapp.com/3LplbCFc/img/vector-24.svg"
                />

                <div className="rectangle-20" />

                <div className="rectangle-21" />

                <div className="rectangle-22" />

                <div className="div-wrapper">
                    <div className="text-wrapper-69">프로필 관리</div>
                </div>

                <div className="frame">
                    <img
                        className="notification"
                        alt="Notification"
                        src="https://c.animaapp.com/3LplbCFc/img/notification@2x.png"
                    />
                </div>

                <img
                    className="frame-2"
                    alt="Frame"
                    src="https://c.animaapp.com/3LplbCFc/img/frame.svg"
                />

                <img className="vector-10" alt="Vector" src="/img/vector-30.svg" />

                <div className="overlap-8">
                    <div className="svg-2" />

                    <div className="ellipse-2" />
                </div>
            </div>
        </div>    );
}