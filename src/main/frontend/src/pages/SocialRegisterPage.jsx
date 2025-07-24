import React, { useState, useEffect } from "react";
import "../styles/SocialRegisterPage.css";
import { useNavigate } from "react-router-dom";

// === Figma 에셋들 ===
import EllipseGreen from "../assets/ellipse-1.png";
import LogoText from "../assets/group-text.png";
import LogoIcon from "../assets/group-icon.png";
import DividerLineTop from "../assets/vector-2.svg";
import DividerLineBottom from "../assets/vector-3.svg";
import RuleCheckIcon from "../assets/vector-4.svg";

const SocialRegisterPage = () => {
  const [name, setName] = useState("");
  const [phone, setPhone] = useState("");
  const [birthYear, setBirthYear] = useState("");
  const [birthMonth, setBirthMonth] = useState("");
  const [birthDay, setBirthDay] = useState("");
  const [addressCity, setAddressCity] = useState("");
  const [addressTown, setAddressTown] = useState("");
  const [addressStreet, setAddressStreet] = useState("");
  const [socialEmail, setSocialEmail] = useState("");
  const [submittedData, setSubmittedData] = useState(null);
  const navigate = useNavigate();
  const [gender, setGender] = useState("MALE"); // or "FEMALE"

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!gender) {
      alert("성별을 선택해주세요.");
      return;
    }

    const token = localStorage.getItem("accessToken");

    if (!token) {
      alert("로그인이 만료되었거나 토큰이 없습니다. 다시 로그인해주세요.");
      return;
    }

    const userData = {
      nickname: name,
      gender,
      phone,
      birthdate: `${birthYear}-${birthMonth}-${birthDay}`,
      address: {
        city: addressCity,
        town: addressTown,
        street: addressStreet
      },
      email: socialEmail
    };

    try {
      const response = await fetch("/api/auth/social-register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(userData),
      });

      if (!response.ok) {
        // 👇 여기서 응답 본문이 JSON이 아닐 수도 있으니 text()로 받아
        const errorText = await response.text();
        console.error("❌ 서버 응답 오류:", errorText);
        alert("회원 등록 중 문제가 발생했습니다.");
        return;
      }

      // 정상 응답일 경우
      const data = await response.json();
      navigate("/information");

    } catch (err) {
      console.error("❌ 통신 오류:", err);
      alert("서버와 통신 중 문제가 발생했습니다.");
    }
  };

  useEffect(() => {
    const url = new URL(window.location.href);
    const accessToken = url.searchParams.get("accessToken");
    const refreshToken = url.searchParams.get("refreshToken");
    const email = url.searchParams.get("email");

    if (accessToken && refreshToken) {
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);
      console.log("✅ accessToken 저장 완료");
    }

    if (email) {
      setSocialEmail(decodeURIComponent(email));
    }
  }, []);

  return (
      <div className="element">
        <div className="div">
          {/* 로고 영역 */}
          <div className="group-11">
            <div className="overlap-group-wrapper">
              <div className="overlap-group-2">
                <img className="vector-6" alt="로고" src={LogoIcon} />
              </div>
            </div>
            <div className="group-12">
              <img src={LogoText} alt="로고텍스트" />
            </div>
          </div>

          {/* 진행바 */}
          <div className="overlap">
            <div className="rectangle"></div>
            <div className="ellipse"></div>
          </div>

          {/* 상태 성공 점 */}
          <img className="ellipse-2" src={EllipseGreen} alt="성공표시점" />

          {/* 섹션 타이틀 + 필수입력 표시 */}
          <div className="text-wrapper-14">회원정보</div>
          <div className="text-wrapper-13">*</div>
          <div className="text-wrapper-12">필수입력사항</div>

          {/* 이메일 */}
          <div className="group-14">
            <div className="text-wrapper-2">이메일</div>
            <div className="text-wrapper-18">*</div>
            <input className="input-field" value={socialEmail} readOnly />
          </div>

          {/* OTP */}
          <div className="overlap-6">
            <input className="otp-input" placeholder="인증번호 6자리" disabled />
            <div className="text-wrapper-20">00:00</div>
            <div className="overlap-7">
              <div className="text-wrapper-21">인증번호 확인</div>
            </div>
          </div>
          <div className="overlap-8 disabled">
            <div className="text-wrapper-22">인증번호 받기</div>
          </div>

          {/* 비밀번호 */}
          <div className="group">
            <div className="text-wrapper-2">비밀번호</div>
            <div className="text-wrapper-3">*</div>
            <input className="input-field" type="password" value="********" readOnly />
          </div>

          {/* 비밀번호 규칙 */}
          <div className="group-8">
            <div className="icon-box">
              <img src={RuleCheckIcon} className="icon" alt="규칙체크1" />
            </div>
            <p className="p">영문/숫자/특수문자 중 2가지 이상 포함</p>
          </div>
          <div className="group-9">
            <div className="icon-box">
              <img src={RuleCheckIcon} className="icon" alt="규칙체크2" />
            </div>
            <p className="p">8자 이상 20자 이하 입력</p>
          </div>
          <div className="group-10">
            <div className="icon-box">
              <img src={RuleCheckIcon} className="icon" alt="규첵체크3" />
            </div>
            <p className="p">연속 3자 동일 문자/숫자 제외</p>
          </div>

          {/* 비밀번호 재확인 */}
          <div className="group-7">
            <div className="text-wrapper-2">비밀번호 재확인</div>
            <input className="input-field" type="password" value="********" readOnly />
          </div>

          {/* 구분선 이미지 */}
          <img className="vector-2" src={DividerLineTop} alt="구분선위" />
          <img className="vector-3" src={DividerLineBottom} alt="구분선아래" />

          {/* 이름 */}
          <div className="group-2">
            <div className="text-wrapper-2">이름</div>
            <div className="text-wrapper-4">*</div>
            <input className="input-field" placeholder="이름을 입력해주세요" value={name} onChange={(e) => setName(e.target.value)} />
          </div>

          {/* 성별 */}
          <div className="group-3">
            <div className="text-wrapper-2">성별</div>
            <div className="text-wrapper-4">*</div>
            <button
                type="button"
                className={`gender-btn ${gender === "MALE" ? "selected" : ""}`}
                onClick={() => setGender("MALE")}
            >
              남자
            </button>
            <button
                type="button"
                className={`gender-btn ${gender === "FEMALE" ? "selected" : ""}`}
                onClick={() => setGender("FEMALE")}
            >
              여자
            </button>
          </div>

          {/* 휴대폰 */}
          <div className="group-4">
            <div className="text-wrapper-2">휴대폰</div>
            <div className="text-wrapper-8">*</div>
            <input className="input-field" placeholder="숫자만 입력해주세요" value={phone} onChange={(e) => setPhone(e.target.value)} />
          </div>

          {/* 생년월일 */}
          <div className="overlap-3 group-6">
            <div className="text-wrapper-2">생년월일</div>
            <div className="birthdate-wrapper">
              <input className="birthdate-field" placeholder="YYYY" value={birthYear} onChange={(e) => setBirthYear(e.target.value)} />
              <input className="birthdate-field" placeholder="MM" value={birthMonth} onChange={(e) => setBirthMonth(e.target.value)} />
              <input className="birthdate-field" placeholder="DD" value={birthDay} onChange={(e) => setBirthDay(e.target.value)} />
            </div>
          </div>

          {/* 주소 */}
          <div className="group-5">
            <div className="text-wrapper-2">주소</div>
            <div className="text-wrapper-4">*</div>
            <input className="input-field" placeholder="시/도" value={addressCity} onChange={(e) => setAddressCity(e.target.value)} />
            <input className="input-field" placeholder="구/군" value={addressTown} onChange={(e) => setAddressTown(e.target.value)} />
            <input className="input-field" placeholder="도로명" value={addressStreet} onChange={(e) => setAddressStreet(e.target.value)} />
          </div>

          <div className="overlap-4">
            <div className="text-wrapper-15">주소 찾기</div>
          </div>
        </div>

          <div className="overlap-wrapper">
            <div className="overlap-5" onClick={handleSubmit}>
              <div className="text-wrapper-16">다음으로</div>
            </div>

            {submittedData && (
                <h2 className="submit-summary">
                  가입 완료: {submittedData.name}님, "Meetny에 가입하신걸 환영합니다!"
                </h2>
            )}

        </div>
      </div>
  );
}
export default SocialRegisterPage;