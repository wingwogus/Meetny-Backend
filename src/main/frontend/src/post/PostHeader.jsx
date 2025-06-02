import React from "react";
import group2 from "../assets/group-2.png";
import vector from "../assets/vector.svg";

const Header = () => {
    return (
        <div className="header">
            <img src={group2} alt="Logo" />
            <div className="nav-menu">
                <span>로그인</span>
                <span>고객센터</span>
                <button className="join-button">회원가입</button>
            </div>
        </div>
    );
};

export default Header;