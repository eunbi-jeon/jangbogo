import React, {useState} from 'react';
import { Link } from 'react-router-dom';
import "../css/login.css"
import "../css/root.css"

import { login } from '../util/APIUtils';
import { NAVER_AUTH_URL ,KAKAO_AUTH_URL, ACCESS_TOKEN, REFRESH_TOKEN } from '../constants';


function Login(props) {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const onChangeEmail = (e) => {
        setEmail(e.target.value);
    }

    const onChangePassword = (e) => {
        setPassword(e.target.value);
    }

    const data = {
        email: email,
        password: password
    }


    const handleSubmit = (event) => {
        event.preventDefault();   

        const loginRequest = Object.assign({}, data);

        login(loginRequest)
        .then(response => {
            localStorage.setItem(ACCESS_TOKEN, response.accessToken);
            localStorage.setItem(REFRESH_TOKEN, response.refreshToken);
            alert("로그인에 성공하였습니다.");
            window.location.href = "/";
        }).catch(error => {
            alert((error && error.message) || '로그인에 실패하였습니다.');
        });
    }


    return (
        <div className='loginContainer'>
            <div className='loginwrap'>
                <h1>로그인</h1>
                <div className="loginline"></div>
                <div className="loginbox">
                    <form onSubmit={handleSubmit}>
                    <input type="text" name='email' placeholder='이메일을 입력해주세요' className="loginid"
                            value={email} onChange={onChangeEmail} required />
                    <input type="password" name='password' placeholder='비밀번호 입력해주세요' className="loginid" 
                                value={password} onChange={onChangePassword} required />
                    <button type="submit">로그인</button>
                    </form>
                </div>
                <div style={{fontSize:14}}>아직 회원이 아니신가요?</div>
                <button className='signup-btn'><Link to='/signup'>간편 회원가입하기</Link></button>
                <span style={{fontSize:14, marginRight:10}}><Link to='/email/find'>이메일 찾기</Link></span>
                <span style={{fontSize:14}}>ㅣ</span>
                <span style={{fontSize:14, marginLeft:10}}><Link to='/password/find'>비밀번호 재설정</Link></span>
            </div>
        </div>
    )
}

export default Login;