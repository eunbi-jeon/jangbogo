import React, { useState, useCallback } from 'react';
import { Link } from 'react-router-dom';
import "../css/login.css"
import "../css/root.css"
import axios from "axios";

  
export default function Login() {


    const [email, setEmail] = useState("");
    const [password, setPass] = useState("");
    const [isRemember, setIsRemember] = useState(false);
    // const [cookies, setCookie, removeCookie] = useCookies(['rememberId']);
    const navi = useNavigate();

    // 로그인 함수
  const login = async (id, pw) => {
    try {
        // 로그인 정보 보내는 axios
        const response = await axios.post("http://localhost:8080/auth/login", {
            email: email,
            password: password
        })
        if (response.data === "") {
            alert(' 유효하지 않거나 없는 아이디,비밀번호 입니다.')
            console.log(response.data)
        } else {
            // console.log(response.data);
            // alert(id + "님 환영합니다.")
            sessionStorage.setItem("token", JSON.stringify(response.data));
            sessionStorage.setItem("loginInfo", moment.now().toString())
            window.location.href = "/";
            if (isRemember) {
                setCookie('email', email, {maxAge: 2000});
            } else {
                removeCookie('email');
            }
        }

    } catch (err) {
        console.log(err)
    }
}

// 로그인 이벤트
const loginUser = () => {
    if (email === "") {
        alert('아이디를 입력해 주세요!')
    } else if (password === "") {
        alert('비밀번호를 입력해 주세요!')
    } else {
        login(email, password);
    }
}

const onChangeEmail = (e) => {
    setEmail(e.target.value);
}

const onChangePw = (e) => {
    setPass(e.target.value);
}

    return (
        <div className='loginContainer'>
            <div className='loginwrap'>
                <h1>로그인</h1>
                <div className="loginline"></div>
                <div className="loginbox">
                    <form onSubmit={loginUser}>
                    <input type="text" name='email' value={email} placeholder='이메일을 입력해주세요' className="loginid"
                             onChange={onChangeEmail} />
                    <input type="password" name='password' value={password} placeholder='비밀번호 입력해주세요' className="loginid" 
                             onChange={onChangePw}/>
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