import React from 'react';
import "../css/header.css"
import "../css/root.css"
import logoImg from "../img/logo.png"
import { Link } from 'react-router-dom';

export default function Header() {
    return (
        <div>
            <div className="header-container">
                <div className="header-wrap">
                    <div className="header-left">
                        <div>
                            <Link to='/'>
                                <img src={logoImg} alt='로고' className='logo-img' />
                            </Link>
                        </div>
                        <div className="search-form">
                            <input type="text" placeholder="검색어를 입력해주세요" />
                        </div>
                    </div>
                    <div className="button">
                        <button id="sign-up"><Link to='/mypage'>마이페이지</Link></button>
                        <button id="login"><Link to='/login'>로그인</Link></button>
                    </div>
                </div>
            </div>
            <div className="line"></div>
            <div className="header-menu">
                <div className="menu-wrap">
                    <span className="category">전체 카테고리</span>
                    <div className="menu-right">
                        <span>우리지역</span>
                        <span>문의 게시판</span>
                    </div>
                </div>
            </div>
        </div>
    )
}