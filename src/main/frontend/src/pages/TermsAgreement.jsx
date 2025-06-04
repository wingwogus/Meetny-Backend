import React from 'react';
import '../styles/TermsAgreement.css';

    export default function TermsAgreement() {
        return (
            <div className="terms-container">
                {/* ───────────────────────────────
         1. 상단 타이틀 + 별표
      ─────────────────────────────── */}
                <div className="asterisk">*</div>
                <div className="main-title">이용약관 동의</div>

                {/* ───────────────────────────────
         2. 구분선 (Vector 10)
      ─────────────────────────────── */}
                <div className="divider-line">
                    <svg
                        width="1128"
                        height="3"
                        viewBox="0 0 1128 3"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path d="M0 1.86768H1128" stroke="#EEE5DD" strokeWidth="2" />
                    </svg>
                </div>

                {/* ───────────────────────────────
         3. “전체 동의” 텍스트 + 체크박스 (Rectangle 18 + Vector 3)
      ─────────────────────────────── */}
                <div className="agree-all-text">
                    필수 및 선택 약관에 모두 동의합니다.
                </div>
                <div className="agree-all-rect">
                    <svg
                        width="26"
                        height="26"
                        viewBox="0 0 26 26"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <rect
                            x="0.118408"
                            y="0.524902"
                            width="25.4053"
                            height="25.4053"
                            rx="3"
                            fill="#F4964A"
                        />
                    </svg>
                </div>
                <div className="agree-all-icon">
                    <svg
                        width="17"
                        height="16"
                        viewBox="0 0 17 16"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M1.8313 5.50102L7.99512 14.2536L15.3915 2.04932"
                            stroke="white"
                            strokeWidth="2.5"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                        />
                    </svg>
                </div>

                {/* ───────────────────────────────
         4. 개별 약관 영역(총 4개)
         Rectangle 19 / Vector 4  →  이용약관 동의 (필수)
         Rectangle 20 / Vector 5  →  개인정보 수집·이용 동의 (필수)
         Rectangle 20 / Vector 5  →  개인정보 수집·이용 동의 (선택)
         Rectangle 20 / Vector 5  →  본인은 만 14세 이상입니다. (필수)
      ─────────────────────────────── */}

                {/* 4-1) 이용약관 동의 (필수) */}
                <div className="clause-rect clause1-rect">
                    <svg
                        width="31"
                        height="31"
                        viewBox="0 0 31 31"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <rect
                            x="1.4813"
                            y="1.36485"
                            width="28.3666"
                            height="28.3666"
                            rx="2.90578"
                            fill="#F3F3F3"
                            stroke="#E9E9E9"
                            strokeWidth="1.16231"
                        />
                    </svg>
                </div>
                <div className="clause-icon clause1-icon">
                    <svg
                        width="19"
                        height="18"
                        viewBox="0 0 19 18"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M1.54028 6.37915L8.70457 16.5524L17.3015 2.36719"
                            stroke="#D2D2D2"
                            strokeWidth="2.90578"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                        />
                    </svg>
                </div>
                <div className="clause-text clause1-text">이용약관 동의</div>
                <div className="clause-required clause1-required">(필수)</div>
                <div className="view-button view1">보기</div>

                {/* 4-2) 개인정보 수집·이용 동의 (필수) */}
                <div className="clause-rect clause2-rect">
                    <svg
                        width="31"
                        height="30"
                        viewBox="0 0 31 30"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <rect
                            x="1.4813"
                            y="0.588481"
                            width="28.3666"
                            height="28.3666"
                            rx="2.90578"
                            fill="#F3F3F3"
                            stroke="#E9E9E9"
                            strokeWidth="1.16231"
                        />
                    </svg>
                </div>
                <div className="clause-icon clause2-icon">
                    <svg
                        width="19"
                        height="18"
                        viewBox="0 0 19 18"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M1.54028 5.60278L8.70457 15.776L17.3015 1.59082"
                            stroke="#D2D2D2"
                            strokeWidth="2.90578"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                        />
                    </svg>
                </div>
                <div className="clause-text clause2-text">
                    개인정보 수집·이용 동의
                </div>
                <div className="clause-required clause2-required">(필수)</div>
                <div className="view-button view2">보기</div>

                {/* 4-3) 개인정보 수집·이용 동의 (선택) */}
                <div className="clause-rect clause3-rect">
                    <svg
                        width="31"
                        height="30"
                        viewBox="0 0 31 30"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <rect
                            x="1.4813"
                            y="0.812114"
                            width="28.3666"
                            height="28.3666"
                            rx="2.90578"
                            fill="#F3F3F3"
                            stroke="#E9E9E9"
                            strokeWidth="1.16231"
                        />
                    </svg>
                </div>
                <div className="clause-icon clause3-icon">
                    <svg
                        width="19"
                        height="18"
                        viewBox="0 0 19 18"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M1.54028 5.82642L8.70457 15.9996L17.3015 1.81445"
                            stroke="#D2D2D2"
                            strokeWidth="2.90578"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                        />
                    </svg>
                </div>
                <div className="clause-text clause3-text">
                    개인정보 수집·이용 동의
                </div>
                <div className="clause-required clause3-required">(선택)</div>
                <div className="view-button view3">보기</div>

                {/* 4-4) 본인은 만 14세 이상입니다. (필수) */}
                <div className="clause-rect clause4-rect">
                    <svg
                        width="31"
                        height="30"
                        viewBox="0 0 31 30"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <rect
                            x="1.4813"
                            y="1.03575"
                            width="28.3666"
                            height="28.3666"
                            rx="2.90578"
                            fill="#F3F3F3"
                            stroke="#E9E9E9"
                            strokeWidth="1.16231"
                        />
                    </svg>
                </div>
                <div className="clause-icon clause4-icon">
                    <svg
                        width="19"
                        height="18"
                        viewBox="0 0 19 18"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M1.54028 6.05005L8.70457 16.2233L17.3015 2.03809"
                            stroke="#D2D2D2"
                            strokeWidth="2.90578"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                        />
                    </svg>
                </div>
                <div className="clause-text clause4-text">
                    본인은 만 14세 이상입니다.
                </div>
                <div className="clause-required clause4-required">(필수)</div>
                <div className="view-button view4">보기</div>

                {/* ───────────────────────────────
         5. 데코레이션 SVG 아이콘들 (원본 위치 그대로)
         Ellipse 2, Vector11, Vector12, Vector13, Vector14
      ─────────────────────────────── */}
                <div className="decor-ellipse2">
                    <svg
                        width="22"
                        height="23"
                        viewBox="0 0 22 23"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <circle cx="11.0508" cy="11.5886" r="10.8205" fill="#F4964A" />
                    </svg>
                </div>

                <div className="decor-vector11">
                    <svg
                        width="10"
                        height="15"
                        viewBox="0 0 10 15"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M1.32739 1.08936L8.48169 7.82263L1.32739 14.1353"
                            stroke="#C5C5C5"
                            strokeWidth="1.7"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                        />
                    </svg>
                </div>
                <div className="decor-vector12">
                    <svg
                        width="10"
                        height="15"
                        viewBox="0 0 10 15"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M1.32739 0.874512L8.48169 7.60779L1.32739 13.9205"
                            stroke="#C5C5C5"
                            strokeWidth="1.7"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                        />
                    </svg>
                </div>
                <div className="decor-vector13">
                    <svg
                        width="10"
                        height="16"
                        viewBox="0 0 10 16"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M1.32739 1.33154L8.48169 8.06482L1.32739 14.3775"
                            stroke="#C5C5C5"
                            strokeWidth="1.7"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                        />
                    </svg>
                </div>
                <div className="decor-vector14">
                    <svg
                        width="10"
                        height="16"
                        viewBox="0 0 10 16"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M1.32739 1.31104L8.48169 8.04431L1.32739 14.357"
                            stroke="#C5C5C5"
                            strokeWidth="1.7"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                        />
                    </svg>
                </div>

                {/* ───────────────────────────────
         6. 진행 상태 표시(Progress Bar)
         Rectangle 24 / Rectangle 25 / Ellipse 3
      ─────────────────────────────── */}
                <div className="progress-bg" />
                <div className="progress-filled" />
                <div className="progress-circle" />

                {/* ───────────────────────────────
         7. “다음으로” 버튼
      ─────────────────────────────── */}
                <button className="next-button">다음으로</button>

                {/* ───────────────────────────────
         8. 상단 좌측 로고 아이콘(예시: Vector들)
         위치는 기존 인라인 좌표 그대로 배치
      ─────────────────────────────── */}
                <div className="logo-vector1">
                    <svg
                        width="28"
                        height="26"
                        viewBox="0 0 28 26"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M2.07233 0.272461H4.6097C5.06552 0.272461 5.49094 0.500324 5.73405 0.882545L13.1335 12.4962C13.65 13.3047 14.8655 13.3047 15.3821 12.4962L22.7815 0.882545C23.0247 0.507674 23.4501 0.272461 23.9059 0.272461H26.4433C27.1726 0.272461 27.7651 0.845793 27.7651 1.55143V23.9923C27.7651 24.6979 27.1726 25.2712 26.4433 25.2712H23.3209C22.5916 25.2712 21.9991 24.6979 21.9991 23.9923V16.1567C21.9991 14.863 20.2366 14.3853 19.5453 15.4952L16.3925 20.5449C16.1494 20.9271 15.724 21.1623 15.2606 21.1623H13.2626C12.7992 21.1623 12.3738 20.9271 12.1307 20.5449L8.97794 15.4952C8.28662 14.3853 6.52413 14.863 6.52413 16.1567V23.9996C6.52413 24.7052 5.93157 25.2786 5.20226 25.2786H2.07993C1.35062 25.2786 0.758057 24.7052 0.758057 23.9996V1.55878C0.758057 0.853144 1.35062 0.279812 2.07993 0.279812L2.07233 0.272461Z"
                            fill="#544439"
                        />
                    </svg>
                </div>
                <div className="logo-vector2">
                    <svg
                        width="21"
                        height="26"
                        viewBox="0 0 21 26"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M2.02011 0.272487H18.589C19.3183 0.272487 19.9109 0.845819 19.9109 1.55146V3.83744C19.9109 4.54308 19.3183 5.11641 18.589 5.11641H7.79378C7.06448 5.11641 6.47191 5.68974 6.47191 6.39538V8.93128C6.47191 9.63692 7.06448 10.2102 7.79378 10.2102H17.4799C18.2092 10.2102 18.8017 10.7836 18.8017 11.4892V13.7752C18.8017 14.4808 18.2092 15.0542 17.4799 15.0542H7.79378C7.06448 15.0542 6.47191 15.6275 6.47191 16.3331V19.141C6.47191 19.8466 7.06448 20.42 7.79378 20.42H18.8777C19.607 20.42 20.1996 20.9933 20.1996 21.6989V23.9849C20.1996 24.6906 19.607 25.2639 18.8777 25.2639H2.02011C1.29081 25.2639 0.698242 24.6906 0.698242 23.9849V1.54411C0.698242 0.83847 1.29081 0.265137 2.02011 0.265137V0.272487Z"
                            fill="#544439"
                        />
                    </svg>
                </div>
                <div className="logo-vector3">
                    <svg
                        width="20"
                        height="26"
                        viewBox="0 0 20 26"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M1.74814 0.272487H18.317C19.0463 0.272487 19.6389 0.845819 19.6389 1.55146V3.83744C19.6389 4.54308 19.0463 5.11641 18.317 5.11641H7.5218C6.79249 5.11641 6.19994 5.68974 6.19994 6.39538V8.93128C6.19994 9.63692 6.79249 10.2102 7.5218 10.2102H17.2079C17.9372 10.2102 18.5297 10.7836 18.5297 11.4892V13.7752C18.5297 14.4808 17.9372 15.0542 17.2079 15.0542H7.5218C6.79249 15.0542 6.19994 15.6275 6.19994 16.3331V19.141C6.19994 19.8466 6.79249 20.42 7.5218 20.42H18.6057C19.335 20.42 19.9276 20.9933 19.9276 21.6989V23.9849C19.9276 24.6906 19.335 25.2639 18.6057 25.2639H1.74814C1.01883 25.2639 0.42627 24.6906 0.42627 23.9849V1.54411C0.42627 0.83847 1.01883 0.265137 1.74814 0.265137V0.272487Z"
                            fill="#544439"
                        />
                    </svg>
                </div>
                <div className="logo-vector4">
                    <svg
                        width="24"
                        height="26"
                        viewBox="0 0 24 26"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M21.952 5.21194H16.1935C15.4642 5.21194 14.8716 5.78527 14.8716 6.49091V23.9923C14.8716 24.6979 14.2791 25.2712 13.5498 25.2712H10.4274C9.69813 25.2712 9.10557 24.6979 9.10557 23.9923V6.49091C9.10557 5.78527 8.51302 5.21194 7.78371 5.21194H2.02524C1.29593 5.21194 0.703369 4.63861 0.703369 3.93297V1.55143C0.703369 0.845793 1.29593 0.272461 2.02524 0.272461H21.952C22.6813 0.272461 23.2738 0.845793 23.2738 1.55143V3.93297C23.2738 4.63861 22.6813 5.21194 21.952 5.21194Z"
                            fill="#544439"
                        />
                    </svg>
                </div>
                <div className="logo-vector5">
                    <svg
                        width="25"
                        height="27"
                        viewBox="0 0 25 27"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M2.06269 1.27258H4.94191C5.33695 1.27258 5.70919 1.44164 5.95989 1.73566L16.1246 13.5772C16.9147 14.496 18.4645 13.9594 18.4645 12.7613V2.24284C18.4645 1.5372 19.057 0.963867 19.7863 0.963867H22.9162C23.6455 0.963867 24.2381 1.5372 24.2381 2.24284V24.6837C24.2381 25.3893 23.6455 25.9626 22.9162 25.9626H20.037C19.642 25.9626 19.2697 25.7936 19.019 25.4996L8.85432 13.658C8.06424 12.7392 6.51448 13.2758 6.51448 14.4739V24.9924C6.51448 25.698 5.92192 26.2713 5.19262 26.2713H2.07027C1.34097 26.2713 0.748413 25.698 0.748413 24.9924V2.55156C0.748413 1.84592 1.34097 1.27258 2.07027 1.27258H2.06269Z"
                            fill="#544439"
                        />
                    </svg>
                </div>
                <div className="logo-vector6">
                    <svg
                        width="24"
                        height="26"
                        viewBox="0 0 24 26"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M14.7318 17.7444V23.9923C14.7318 24.6979 14.1393 25.2712 13.41 25.2712H10.2876C9.55834 25.2712 8.96576 24.6979 8.96576 23.9923V17.7444C8.96576 17.5312 8.91259 17.3181 8.80623 17.1343L0.366033 2.16152C-0.112573 1.30887 0.525591 0.272461 1.52839 0.272461H5.08374C5.57754 0.272461 6.03335 0.544427 6.26126 0.970751L10.9258 9.83535C11.4196 10.7689 12.7946 10.7689 13.2808 9.83535L17.9225 0.970751C18.1505 0.537076 18.6063 0.272461 19.1001 0.272461H22.1768C23.1796 0.272461 23.8178 1.30887 23.3392 2.16152L14.899 17.1343C14.7926 17.3254 14.7394 17.5312 14.7394 17.7444H14.7318Z"
                            fill="#544439"
                        />
                    </svg>
                </div>
                <div className="logo-vector7">
                    <svg
                        width="13"
                        height="22"
                        viewBox="0 0 13 22"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M6.18646 0.621582H6.17886C2.82233 0.621582 0.101318 3.2543 0.101318 6.50191V16.0795C0.101318 19.3271 2.82233 21.9598 6.17886 21.9598H6.18646C9.54299 21.9598 12.264 19.3271 12.264 16.0795V6.50191C12.264 3.2543 9.54299 0.621582 6.18646 0.621582Z"
                            fill="#F4964A"
                        />
                    </svg>
                </div>
                <div className="logo-vector8">
                    <svg
                        width="13"
                        height="22"
                        viewBox="0 0 13 22"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M6.20159 0.621582H6.194C2.83746 0.621582 0.116455 3.2543 0.116455 6.50191V16.0795C0.116455 19.3271 2.83746 21.9598 6.194 21.9598H6.20159C9.55812 21.9598 12.2791 19.3271 12.2791 16.0795V6.50191C12.2791 3.2543 9.55812 0.621582 6.20159 0.621582Z"
                            fill="#FFCC4D"
                        />
                    </svg>
                </div>
                <div className="logo-vector9">
                    <svg
                        width="37"
                        height="51"
                        viewBox="0 0 37 51"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M15.4031 0.102051C9.45471 0.102051 4.0685 2.48359 0.20166 6.31315C3.94694 10.0251 6.26401 15.0895 6.26401 20.6759V34.3329C6.26401 37.5745 3.5519 40.2059 0.20166 40.2133H0.216854C3.5747 40.2133 6.2944 37.5818 6.2944 34.3329V20.6759C6.2944 15.8172 10.3815 11.8701 15.3955 11.8701C20.4095 11.8701 24.4966 15.8246 24.4966 20.6759V44.8514C24.4966 48.1003 27.2163 50.7317 30.5742 50.7317C33.932 50.7317 36.6517 48.1003 36.6517 44.8514V20.6759C36.6517 9.33417 27.1176 0.109401 15.3955 0.109401L15.4031 0.102051Z"
                            fill="#FFCC4D"
                        />
                    </svg>
                </div>
                <div className="logo-vector10">
                    <svg
                        width="38"
                        height="51"
                        viewBox="0 0 38 51"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M31.1392 34.3256V20.6685C31.1392 15.0822 33.4563 10.0178 37.2015 6.30583C33.3423 2.47626 27.9561 0.0947266 22.0001 0.0947266C10.278 0.0947266 0.743896 9.3195 0.743896 20.6612V44.8367C0.743896 48.0856 3.4636 50.717 6.82144 50.717C10.1793 50.717 12.899 48.0856 12.899 44.8367V20.6612C12.899 15.8026 16.9861 11.8554 22.0001 11.8554C27.0141 11.8554 31.1012 15.8099 31.1012 20.6612V34.3183C31.1012 37.5671 33.8209 40.1986 37.1788 40.1986H37.194C33.8437 40.1912 31.1316 37.5598 31.1316 34.3183L31.1392 34.3256Z"
                            fill="#F4964A"
                        />
                    </svg>
                </div>
                <div className="logo-vector11">
                    <svg
                        width="13"
                        height="35"
                        viewBox="0 0 13 35"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M12.2639 28.3254V14.6684C12.2639 9.08206 9.94679 4.01762 6.20151 0.305664C2.45622 4.01762 0.13916 9.08206 0.13916 14.6684V28.3254C0.13916 31.567 2.85126 34.1984 6.20151 34.2058C9.55175 34.1984 12.2639 31.567 12.2639 28.3254Z"
                            fill="#DB6A1F"
                        />
                    </svg>
                </div>
                <div className="logo-vector12">
                    <svg
                        width="24"
                        height="23"
                        viewBox="0 0 24 23"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                            d="M11.7648 22.1475C17.987 22.1475 23.031 17.2671 23.031 11.2469C23.031 5.22659 17.987 0.346191 11.7648 0.346191C5.5426 0.346191 0.498535 5.22659 0.498535 11.2469C0.498535 17.2671 5.5426 22.1475 11.7648 22.1475Z"
                            fill="#72D3AA"
                        />
                    </svg>
                </div>
            </div>
        );
}