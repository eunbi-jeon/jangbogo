import React from 'react';
import { Link } from 'react-router-dom';
import "../css/login.css"
import "../css/root.css"

export default function Login() {
    return (
        <div className='loginContainer'>
            <div className='loginwrap'>
                <h1>로그인</h1>
                <div className="loginline"></div>
                <div className="loginbox">
                    <form>
                    <input type="text" name='email' placeholder='이메일을 입력해주세요' className="loginid" />
                    <input type="password" name='password' placeholder='비밀번호 입력해주세요' className="loginid" />
                    <button type="submit">로그인</button>
                    </form>
                </div>
                <div style={{fontSize:14}}>아직 회원이 아니신가요?</div>
                <button className='signup-btn'><Link to='/signup'>간편 회원가입하기</Link></button>
                <span style={{fontSize:16, marginRight:10}}>이메일 찾기</span>
                <span style={{fontSize:16, marginLeft:10}}>비밀번호 찾기</span>
            </div>
         
        </div>
    )
}