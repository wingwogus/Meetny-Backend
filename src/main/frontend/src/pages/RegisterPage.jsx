import React, { useState } from "react";
import "../styles/Register.css";
import axios from "axios";
import { useNavigate } from 'react-router-dom';

const InputField = ({ id, label, type = "text", placeholder, required = false, value, onChange }) => (
  <label htmlFor={id} className="form-group">
    <span className="form-label">
      {label}{required && <span className="required">*</span>}
    </span>
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
  </label>
);

const RegisterPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [addressDetail, setAddressDetail] = useState("");
  const [phone, setPhone] = useState("");
  const [nickname, setNickname] = useState("");
  const [mail, setMail] = useState("");
  const [profileImg, setProfileImg] = useState("");
  const [gender, setGender] = useState("");
  const navigate = useNavigate();

  const handleFindAddress = () => {
    // TODO: 우편번호/주소 검색 API 호출
    console.log('Find address');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = {
      username,
      nickname,
      password,
      phone,
      mail,
      profileImg,
      gender,
      addressDetail,
      memberTag: null
    };

    try {
      const response = await axios.post("http://localhost:8080/api/auth/signup", formData);
      console.log("회원가입 성공:", response.data);
      alert("회원가입이 완료되었습니다.");
      // TODO: 페이지 이동 처리 (예: navigate("/login"))
         navigate("/login");
    } catch (error) {
      console.error("회원가입 오류:", error.response ? error.response.data : error.message);
      alert("회원가입 중 오류가 발생했습니다.");
    }
  };

  return (
    <div className="register-page">
      <h1>회원가입</h1>
      <form onSubmit={handleSubmit} className="register-form" noValidate>
        <InputField
          id="username"
          label="아이디"
          placeholder="아이디를 입력해주세요"
          required
          value={username}
          onChange={e => setUsername(e.target.value)}
        />
        <InputField
          id="nickname"
          label="닉네임"
          placeholder="닉네임을 입력해주세요"
          required
          value={nickname}
          onChange={e => setNickname(e.target.value)}
        />
        <InputField
          id="password"
          label="비밀번호"
          type="password"
          placeholder="비밀번호를 입력해주세요"
          required
          value={password}
          onChange={e => setPassword(e.target.value)}
        />
        <InputField
          id="phone"
          label="휴대폰"
          placeholder="숫자만 입력해주세요"
          required
          value={phone}
          onChange={e => setPhone(e.target.value)}
        />
        <InputField
          id="mail"
          label="이메일"
          type="email"
          placeholder="이메일을 입력해주세요"
          required
          value={mail}
          onChange={e => setMail(e.target.value)}
        />
        <InputField
          id="profileImg"
          label="프로필 이미지 URL"
          type="url"
          placeholder="이미지 URL을 입력해주세요"
          value={profileImg}
          onChange={e => setProfileImg(e.target.value)}
        />
        <label htmlFor="gender" className="form-group">
          <span className="form-label">성별<span className="required">*</span></span>
          <select
            id="gender"
            name="gender"
            required
            value={gender}
            onChange={e => setGender(e.target.value)}
            className="form-input"
          >
            <option value="" disabled>성별을 선택해주세요</option>
            <option value="MALE">남성</option>
            <option value="FEMALE">여성</option>
          </select>
        </label>
        <div className="form-group address-group">
          <InputField
            id="addressDetail"
            label="주소"
            placeholder="주소를 입력해주세요"
            required
            value={addressDetail}
            onChange={e => setAddressDetail(e.target.value)}
          />
          <button type="button" className="btn address-btn" onClick={handleFindAddress}>
            주소 찾기
          </button>
        </div>
        <button type="submit" className="btn submit-btn">
          가입하기
        </button>
      </form>
    </div>
  );
};

export default RegisterPage;