import React from 'react';
import { Link } from "react-router-dom";

export default function Header() {
    return (
		<>
    <div className='header-container'>
        <div className='header-wrap'>
            <div className='header-left'>
                <div id='logo-img'>
                <Link to="/">      
                    <img src='../src/img/logo.png' alt='로고'/>
                </Link>
                </div>
                <div className='search-form'>
                    <input type='text' placeholder='검색어를 입력해주세요' />
                </div>
            </div>
            <div className='button'>
                <button id='sign-up'>회원가입</button>
                <button id='login'>로그인</button>
            </div>
        </div>
    </div>
    <div className='line'></div>
    <div className='header-menu'>
        <div className='menu-wrap'>
            <span className='category'>전체 카테고리</span>
            <div className='menu-right'>
                <span>우리지역</span>
                <span>문의 게시판</span>
            </div>
        </div>
    </div>
    </>
    );
}