// src/pages/RegisterPage.jsx
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axiosClient from "../api/axiosClient";
import "../styles/Register.css"; // 위에서 정의한 리팩토링된 CSS
import group from "../assets/group.png";
import image from "../assets/group.png";
import vector from "../assets/vector.svg";
import vector6 from "../assets/vector-6.svg";


const InputField = ({ id, label, type = "text", placeholder, required = false, value, onChange }) => (
    <div className="form-group-container">
      <label htmlFor={id} className="form-label">
        {label}
        {required && <span className="required">*</span>}
      </label>
      <input
          id={id}
          name={id}
          type={type}
          placeholder={placeholder}
          required={required}
          value={value}
          onChange={onChange}
          className="form-input"
      />
    </div>
);

const SelectField = ({ id, label, value, onChange, required = false, options = [] }) => (
    <div className="form-group-container">
      <label htmlFor={id} className="form-label">
        {label}
        {required && <span className="required">*</span>}
      </label>
      <select
          id={id}
          name={id}
          value={value}
          onChange={onChange}
          required={required}
          className="form-input"
      >
        <option value="" disabled>
          선택해주세요
        </option>
        {options.map((opt) => (
            <option key={opt.value} value={opt.value}>
              {opt.label}
            </option>
        ))}
      </select>
    </div>
);

const RegisterPage = () => {
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [nickname, setNickname] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [phone, setPhone] = useState("");
  const [mail, setMail] = useState("");
  const [addressDetail, setAddressDetail] = useState("");
  const [profileImg, setProfileImg] = useState("");
  const [gender, setGender] = useState("");

  const handleFindAddress = () => {
    // TODO: 우편번호/주소 검색 API 호출
    console.log("주소 찾기 클릭됨");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (password !== passwordConfirm) {
      alert("비밀번호와 비밀번호 재확인이 일치하지 않습니다.");
      return;
    }

    const finalUsername = username.trim(); // 또는 nickname.trim()

    const formData = {
      username: finalUsername,
      nickname: nickname.trim(),
      password,
      phone: phone.trim(),
      mail: mail.trim(),
      profileImg,
      gender,
      addressDetail: addressDetail.trim(),
      memberTag: null,
    };

    try {
      await axiosClient.post("/api/auth/signup", formData);
      alert("회원가입이 완료되었습니다.");
      navigate("/login");
    } catch (error) {
      console.error("회원가입 오류:", error.response ? error.response.data : error.message);
      alert("회원가입 중 오류가 발생했습니다.");
    }
  };

  return (
      <div className="element">
        <div className="div">
          <div className="text-wrapper-12">회원정보</div>
          <div className="text-wrapper-11">*</div>
          <div className="text-wrapper-10">필수입력사항</div>

          <form onSubmit={handleSubmit} noValidate>
            <InputField
                id="username"
                label="아이디"
                placeholder="아이디를 입력해주세요"
                required
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />
            <InputField
                id="nickname"
                label="닉네임"
                placeholder="닉네임을 입력해주세요"
                required
                value={nickname}
                onChange={(e) => setNickname(e.target.value)}
            />
            <InputField
                id="password"
                label="비밀번호"
                type="password"
                placeholder="비밀번호를 입력해주세요"
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <InputField
                id="passwordConfirm"
                label="비밀번호 재확인"
                type="password"
                placeholder="비밀번호를 다시 입력해주세요"
                required
                value={passwordConfirm}
                onChange={(e) => setPasswordConfirm(e.target.value)}
            />
            <InputField
                id="phone"
                label="휴대폰"
                placeholder="숫자만 입력해주세요"
                required
                value={phone}
                onChange={(e) => setPhone(e.target.value)}
            />
            <InputField
                id="mail"
                label="이메일"
                type="email"
                placeholder="이메일을 입력해주세요"
                required
                value={mail}
                onChange={(e) => setMail(e.target.value)}
            />
            <InputField
                id="profileImg"
                label="프로필 이미지 URL"
                type="url"
                placeholder="이미지 URL을 입력해주세요"
                value={profileImg}
                onChange={(e) => setProfileImg(e.target.value)}
            />

            <SelectField
                id="gender"
                label="성별"
                required
                value={gender}
                onChange={(e) => setGender(e.target.value)}
                options={[
                  { value: "MALE", label: "남성" },
                  { value: "FEMALE", label: "여성" },
                ]}
            />

            <div className="address-group">
              <div className="form-group-container" style={{ flex: 1 }}>
                <label htmlFor="addressDetail" className="form-label">
                  주소<span className="required">*</span>
                </label>
                <input
                    id="addressDetail"
                    name="addressDetail"
                    type="text"
                    placeholder="주소를 입력해주세요"
                    required
                    value={addressDetail}
                    onChange={(e) => setAddressDetail(e.target.value)}
                    className="form-input"
                />
              </div>
              <button type="button" className="address-btn" onClick={handleFindAddress}>
                주소 찾기
              </button>
            </div>

            <div className="auth-group">
              <input
                  type="text"
                  placeholder="인증번호 6자리"
                  className="auth-input"
              />
              <button type="button" className="auth-btn">
                인증번호 받기
              </button>
              <span className="timer-text">00:00</span>
              <button type="button" className="auth-btn">
                인증번호 확인
              </button>
            </div>

            <button type="submit" className="submit-btn">
              다음으로
            </button>
          </form>

          <div className="icon-group">
            <img className="group-11" alt="Group" src={group} />
            <img className="vector-6" alt="Vector" src={vector} />
            <img className="vector-6" alt="Vector" src={vector6} />
            <img className="group-12" alt="Group" src={image} />
          </div>
        </div>
      </div>
  );
};

export default RegisterPage;
