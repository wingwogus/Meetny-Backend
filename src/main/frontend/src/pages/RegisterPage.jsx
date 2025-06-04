// src/pages/RegisterPage.jsx
import React, { useState, useRef, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axiosClient from "../api/axiosClient";
import "../styles/Register.css"; // 변경된 CSS

// === Figma 에셋들 ===
import EllipseGreen         from "../assets/ellipse-1.png";
import LogoText             from "../assets/group-text.png";
import LogoIcon             from "../assets/group-icon.png";
import DividerLineTop       from "../assets/vector-2.svg";
import DividerLineBottom    from "../assets/vector-3.svg";
import RuleCheckIcon        from "../assets/vector-4.svg";
import RuleXIcon            from "../assets/vector-5.svg";


const RegisterPage = () => {
  const navigate = useNavigate();

  // ---- State (폼 입력용) ----
  const [email, setEmail] = useState("");
  const [otpCode, setOtpCode] = useState("");
  const [otpRequested, setOtpRequested] = useState(false);
  const [otpVerified, setOtpVerified] = useState(false);

  const [username, setUsername] = useState("");
  const [nickname, setNickname] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");

  const [birthYear, setBirthYear] = useState("");
  const [birthMonth, setBirthMonth] = useState("");
  const [birthDay, setBirthDay] = useState("");

  const [phone, setPhone] = useState("");
  const [address, setAddress] = useState("");
  const [gender, setGender] = useState("");

  // ---- Password Rule States ----
  const [ruleComplexityOK, setRuleComplexityOK] = useState(false);
  const [ruleLengthOK, setRuleLengthOK] = useState(false);
  const [ruleRepeatOK, setRuleRepeatOK] = useState(false);

  // ---- Refs for birthdate auto-focus ----
  const monthRef = useRef(null);
  const dayRef = useRef(null);

  // ---- OTP 타이머 관련 State ----
  const [timer, setTimer] = useState(0);     // 남은 초(초 단위)
  const timerRef = useRef(null);             // setInterval ID를 담을 ref

  // ---- 함수 정의부: handleFindAddress 반드시 여기 선언! ----
  const handleFindAddress = () => {
    // TODO: 실제 우편번호/주소 검색 API 호출
    console.log("주소 찾기 클릭됨");
  };

  // 1) 비밀번호 규칙 검사
  const validatePasswordRules = (pwd) => {
    const hasLetter = /[A-Za-z]/.test(pwd);
    const hasNumber = /[0-9]/.test(pwd);
    const hasSymbol = /[^A-Za-z0-9]/.test(pwd);
    setRuleComplexityOK(
        (hasLetter && hasNumber) ||
        (hasLetter && hasSymbol) ||
        (hasNumber && hasSymbol)
    );
    setRuleLengthOK(pwd.length >= 8 && pwd.length <= 20);
    if (pwd.length <= 2) {
      setRuleRepeatOK(false);
    } else {
      setRuleRepeatOK(!/(.)\1\1/.test(pwd));
    }
  };

  // 2) 생년월일 입력: YYYY 입력 시 MM으로 포커스
  const handleYearChange = (e) => {
    const val = e.target.value.replace(/\D/g, "");
    if (val.length <= 4) {
      setBirthYear(val);
      if (val.length === 4) {
        monthRef.current.focus();
      }
    }
  };
  const handleMonthChange = (e) => {
    const val = e.target.value.replace(/\D/g, "");
    if (val.length <= 2) {
      setBirthMonth(val);
      if (val.length === 2) {
        dayRef.current.focus();
      }
    }
  };
  const handleDayChange = (e) => {
    const val = e.target.value.replace(/\D/g, "");
    if (val.length <= 2) {
      setBirthDay(val);
    }
  };

  // 3) “인증번호 받기” 클릭 시
  const handleRequestOtp = () => {
    if (!email.trim()) {
      alert("이메일을 먼저 입력해주세요.");
      return;
    }

    console.log("OTP 요청:", email);
    setOtpRequested(true);
    setOtpVerified(false);
    setOtpCode("");

    // 타이머 초기화 → 180초(3분)
    setTimer(180);

    // 이미 setInterval이 돌아가고 있으면 중지
    if (timerRef.current) {
      clearInterval(timerRef.current);
    }
    // 1초마다 timer 감소
    timerRef.current = setInterval(() => {
      setTimer(prev => {
        if (prev <= 1) {
          clearInterval(timerRef.current);
          timerRef.current = null;
          // 타이머가 0이 되면 otpRequested를 false로 돌려서
          // OTP 입력창과 확인 버튼을 비활성화 시킴
          setOtpRequested(false);
          return 0;
        }
        return prev - 1;
      });
    }, 1000);
  };

  // 4) “인증번호 확인” 클릭 시
  const handleVerifyOtp = () => {
    if (!otpCode.trim()) {
      alert("OTP 코드를 입력해주세요.");
      return;
    }

    console.log("OTP 확인:", otpCode);

    // 실제 API 호출 → 검증 성공 시 otpVerified를 true로
    setOtpVerified(true);

    // 타이머 중지 & 초기화
    if (timerRef.current) {
      clearInterval(timerRef.current);
      timerRef.current = null;
    }
    setTimer(0);
    setOtpRequested(false); // 다시 비활성화
  };

  // 5) 회원가입 폼 제출
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!otpVerified) {
      alert("이메일 인증(OTP)이 완료되어야 회원가입이 가능합니다.");
      return;
    }
    if (password !== passwordConfirm) {
      alert("비밀번호와 비밀번호 재확인이 일치하지 않습니다.");
      return;
    }
    if (!ruleComplexityOK || !ruleLengthOK || !ruleRepeatOK) {
      alert("비밀번호가 모든 규칙을 만족해야 합니다.");
      return;
    }
    if (
        birthYear.length !== 4 ||
        birthMonth.length !== 2 ||
        birthDay.length !== 2
    ) {
      alert("생년월일을 YYYY/MM/DD 형식으로 모두 입력해주세요.");
      return;
    }

    const finalUsername = username.trim();
    const formData = {
      username: finalUsername,
      nickname: nickname.trim(),
      password,
      phone: phone.trim(),
      mail: email.trim(),
      gender,
      addressDetail: address.trim(),
      memberTag: null,
      birthYear,
      birthMonth,
      birthDay,
    };

    try {
      await axiosClient.post("/api/auth/signup", formData);
      alert("회원가입이 완료되었습니다.");
      navigate("/login");
    } catch (error) {
      console.error(
          "회원가입 오류:",
          error.response ? error.response.data : error.message
      );
      alert("회원가입 중 오류가 발생했습니다.");
    }
  };

  // 컴포넌트 언마운트 시, 혹시 남아있는 타이머가 있으면 정리
  useEffect(() => {
    return () => {
      if (timerRef.current) {
        clearInterval(timerRef.current);
      }
    };
  }, []);

  // 남은 초 → "MM:SS" 포맷으로 변환
  const formatTime = (sec) => {
    const m = String(Math.floor(sec / 60)).padStart(2, "0");
    const s = String(sec % 60).padStart(2, "0");
    return `${m}:${s}`;
  };

  // “인증번호 확인” 버튼 활성 여부
  const isVerifyEnabled = otpRequested && timer > 0 && !otpVerified;
  // “인증번호 받기” 버튼 활성 여부 (타이머 동작 중이거나, 이미 인증 완료된 뒤에는 비활성)
  const isRequestEnabled = !otpRequested && !otpVerified;

  return (
      <div className="register-container">
        <div className="div">
          {/* 1. Progress Bar */}
          <div className="overlap">
            <div className="rectangle" />
            <div className="ellipse" />
          </div>

          {/* 2. Password Input Group */}
          <div className="group">
            <input
                type="password"
                value={password}
                onChange={(e) => {
                  setPassword(e.target.value);
                  validatePasswordRules(e.target.value);
                }}
                placeholder="비밀번호를 입력해주세요"
                className="input-field"
            />
            <div className="text-wrapper-2">비밀번호</div>
            <div className="text-wrapper-3">*</div>
          </div>

          {/* 3. Name Input Group */}
          <div className="group-2">
            <input
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                placeholder="이름을 입력해주세요"
                className="input-field"
            />
            <div className="text-wrapper-2">이름</div>
            <div className="text-wrapper-4">*</div>
          </div>

          {/* 4. Gender Selection Group */}
          <div className="group-3">
            <input
                type="button"
                value="남자"
                onClick={() => setGender("MALE")}
                className={`gender-btn ${gender === "MALE" ? "selected" : ""}`}
            />
            <div className="text-wrapper-2">성별</div>
            <input
                type="button"
                value="여자"
                onClick={() => setGender("FEMALE")}
                className={`gender-btn ${gender === "FEMALE" ? "selected" : ""}`}
            />
            <div className="text-wrapper-4">*</div>
          </div>

          {/* 5. Phone Input Group */}
          <div className="group-4">
            <input
                type="text"
                value={phone}
                onChange={(e) => setPhone(e.target.value.replace(/\D/g, ""))}
                placeholder="숫자만 입력해주세요"
                className="input-field"
            />
            <div className="text-wrapper-2">휴대폰</div>
            <div className="text-wrapper-8">*</div>
          </div>

          {/* 6. Address Input Group */}
          <div className="group-5">
            <input
                type="text"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
                placeholder="주소를 입력해주세요"
                className="input-field"
            />
            <div className="text-wrapper-2">주소</div>
            <div className="text-wrapper-4">*</div>
          </div>

          {/* 7. Birthdate Input Group */}
          <div className="overlap-3">
            <div className="birthdate-wrapper">
              <input
                  type="text"
                  value={birthYear}
                  onChange={handleYearChange}
                  placeholder="YYYY"
                  maxLength={4}
                  className="birthdate-field"
              />
              <input
                  ref={monthRef}
                  type="text"
                  value={birthMonth}
                  onChange={handleMonthChange}
                  placeholder="MM"
                  maxLength={2}
                  className="birthdate-field"
              />
              <input
                  ref={dayRef}
                  type="text"
                  value={birthDay}
                  onChange={handleDayChange}
                  placeholder="DD"
                  maxLength={2}
                  className="birthdate-field"
              />
            </div>
            <div className="text-wrapper-2">생년월일</div>
          </div>

          {/* 8. Password Confirm Group */}
          <div className="group-7">
            <input
                type="password"
                value={passwordConfirm}
                onChange={(e) => setPasswordConfirm(e.target.value)}
                placeholder="비밀번호를 다시한번 입력해주세요"
                className="input-field"
            />
            {passwordConfirm.length > 0 && (
                <img
                    className="rule-icon"
                    src={
                      passwordConfirm === password ? RuleCheckIcon : RuleXIcon
                    }
                    alt={passwordConfirm === password ? "✔" : "✖"}
                />
            )}
            <div className="text-wrapper-2">비밀번호 재확인</div>
          </div>

          {/* 9. Section Header */}
          <div className="text-wrapper-12">필수입력사항</div>
          <div className="text-wrapper-13">*</div>
          <div className="text-wrapper-14">회원정보</div>

          {/* 10. Horizontal Dividers */}
          <img className="vector-2" src={DividerLineTop} alt="구분선" />
          <img className="vector-3" src={DividerLineBottom} alt="구분선" />

          {/* 11. Password Rules */}
          <div className="group-8">
            <p className="p">영문/숫자/특수문자 중, 2가지 이상 포함</p>
            {/* ruleComplexityOK가 true면 클래스가 "icon-box", false면 "icon-box disabled" */}
            <div className={ ruleComplexityOK ? "icon-box" : "icon-box disabled" }>
              <img
                  className="icon"
                  src={ ruleComplexityOK ? RuleCheckIcon : RuleXIcon }
                  alt={ ruleComplexityOK ? "✔" : "✖" }
              />
            </div>
          </div>

          <div className="group-9">
            <p className="p">8자 이상 20자 이하 입력 (공백 제외)</p>
            {/* ruleLengthOK가 true면 "icon-box", false면 "icon-box disabled" */}
            <div className={ ruleLengthOK ? "icon-box" : "icon-box disabled" }>
              <img
                  className="icon"
                  src={ ruleLengthOK ? RuleCheckIcon : RuleXIcon }
                  alt={ ruleLengthOK ? "✔" : "✖" }
              />
            </div>
          </div>

          <div className="group-10">
            <p className="p">연속 3자 이상 동일한 문자/숫자 제외</p>
            {/* ruleRepeatOK가 true면 "icon-box", false면 "icon-box disabled" */}
            <div className={ ruleRepeatOK ? "icon-box" : "icon-box disabled" }>
              <img
                  className="icon"
                  src={ ruleRepeatOK ? RuleCheckIcon : RuleXIcon }
                  alt={ ruleRepeatOK ? "✔" : "✖" }
              />
            </div>
          </div>

          {/* 12. Address Find Button */}
          <div className="overlap-4" onClick={handleFindAddress}>
            <div className="text-wrapper-15">주소 찾기</div>
          </div>

          {/* 13. Status Icon */}
          <img className="ellipse-2" src={EllipseGreen} alt="성공 아이콘" />

          {/* 14. Logo Section */}
          <div className="group-11">
            <img className="group-12" src={LogoText} alt="MEETNY" />
            <div className="overlap-group-wrapper">
              <div className="overlap-group-2">
                <img className="vector-6" src={LogoIcon} alt="로고 아이콘" />
              </div>
            </div>
          </div>

          {/* 15. Submit Button */}
          <div className="overlap-wrapper" onClick={handleSubmit}>
            <div className="overlap-5">
              <div className="text-wrapper-16">다음으로</div>
            </div>
          </div>

          {/* 16. Email & OTP Group */}
          <div className="group-14">
            <input
                type="email"
                value={email}
                onChange={(e) => {
                  setEmail(e.target.value);
                  setOtpRequested(false);
                  setOtpVerified(false);
                  setOtpCode("");
                  // 타이머 초기화
                  if (timerRef.current) {
                    clearInterval(timerRef.current);
                    timerRef.current = null;
                  }
                  setTimer(0);
                }}
                placeholder="이메일을 입력해주세요"
                className="input-field"
            />
            <div className="text-wrapper-2">이메일</div>
            <div className="text-wrapper-18">*</div>
          </div>

          {/* OTP 입력창 + 버튼들 */}
          <div className="overlap-6">
            <input
                type="text"
                value={otpCode}
                onChange={(e) => setOtpCode(e.target.value)}
                placeholder="인증번호 6자리"
                maxLength={6}
                className="otp-input"
                disabled={!otpRequested || timer <= 0 || otpVerified}
            />
            <div className="text-wrapper-20">
              {timer > 0 ? formatTime(timer) : "00:00"}
            </div>
            <div
                className={`overlap-7 ${isVerifyEnabled ? "active" : ""}`}
                onClick={isVerifyEnabled ? handleVerifyOtp : undefined}
            >
              <div
                  className={`text-wrapper-21 ${
                      isVerifyEnabled ? "active-text" : ""
                  }`}
              >
                인증번호 확인
              </div>
            </div>
          </div>
          <div
              className={`overlap-8 ${otpRequested && timer > 0 ? "same-btn" : ""}`}
              onClick={isRequestEnabled ? handleRequestOtp : undefined}
          >
            <div className="text-wrapper-22">인증번호 받기</div>
          </div>
        </div>
      </div>
  );
};

export default RegisterPage;