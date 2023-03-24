import React from 'react';
import "../css/signup.css"
import "../css/root.css"

export default function Signup() {
    return (
        <div>
        <div className='loginContainer'>
            <div className='loginwrap'>
                <h1>회원가입</h1>
                <div className="loginline"></div>
                <div className="loginbox">
                    <form>
                    <input type="text" name='email' placeholder='이메일을 입력해주세요' className="loginid" />
                    <input type="password" name='password' placeholder='비밀번호 입력해주세요' className="loginid" />
                    <input type="password" name='password' placeholder='비밀번호 입력해주세요' className="loginid" />
                    <input type="password" name='password' placeholder='비밀번호 입력해주세요' className="loginid" />
                    <button type="submit">회원가입 하기</button>
                    </form>
                </div>
            </div>
        </div>
        </div>
    )
}