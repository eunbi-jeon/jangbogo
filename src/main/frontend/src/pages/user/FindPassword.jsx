import React, { Component } from 'react';
import '../../css/login.css';
import { NAVER_AUTH_URL ,KAKAO_AUTH_URL } from '../../constants';
import { Link, Redirect } from 'react-router-dom'

import kakaoLogo from '../../img/Kakao-Login.png';
import naverLogo from '../../img/Naver-Login.png';

import axios from 'axios';

class Login extends Component {

    componentDidMount() {
        if(this.props.location.state && this.props.location.state.error) {
            setTimeout(() => {
                alert(this.props.location.state.error, {
                    timeout: 5000
                });
                this.props.history.replace({
                    pathname: this.props.location.pathname,
                    state: {}
                });
            }, 100);
        }
    }
    
    render() {
        if(this.props.authenticated) {
            return <Redirect
                to={{
                pathname: "/",
                state: { from: this.props.location }
            }}/>;            
        }

        return (
            <div className="login-container">
                <div className="login-content">
                    <div className="or-separator">
                    <LoginForm {...this.props} />
                    <SocialLogin />
                    </div>
                </div>
            </div>
        );
    }
}

class SocialLogin extends Component {
    render() {
        return (
            <div className="social-login">
                <a className="btn btn-block social-btn kakao" href={KAKAO_AUTH_URL}>
                    <img src={kakaoLogo} style={{marginRight:3}} alt="Kakao" /></a>
                <a className="btn btn-block social-btn kakao" href={NAVER_AUTH_URL}>
                    <img src={naverLogo} style={{height:45, marginLeft:3}} alt="Naver" /></a>

            </div>
        );
    }
}

class LoginForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
          email: "",
        };
        this.handleEmailCheck = this.handleEmailCheck.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
      }

      handleInputChange(event) {
        const { name, value } = event.target;
        this.setState({
          [name]: value
        })
    }
    

    handleEmailCheck(event) {
        event.preventDefault(); // 기본 동작 방지
        const { email } = this.state;
        if (!email) {
          alert("이메일을 입력해주세요.");
          return;
        }
        axios.get("http://localhost:8080/auth/emailCheck", {
            params: { email }
          })
          .then((req) => {
            console.log("데이터 전송 성공");
            if (req.data === 1) alert('중복된 이메일입니다.');
            else if (req.data === 0) {
              alert('가입된 정보가 없습니다.</br>회원가입페이지로 이동합니다.');
              window.location.href = "/signup";
            }
          })
          .catch(err => {
            console.log(`데이터 전송 실패 ${err}`);
          });
      }

    
    render() {
        return (
        <div className='loginContainer'>
            <div className='loginwrap'>
                <h1>비밀번호 재설정</h1>
                <div className="loginline"></div>
                <div className='pass-des' style={{marginBottom:35}}>
                    <div>가입시 작성한 이메일을 입력해주세요.</div>
                    <div style={{marginTop:8}}>해당 이메일로 임시 패스워드를 전송합니다.</div>
                </div>
                <div className="loginbox">
                    <form onSubmit={this.handleEmailCheck}>
                    <input type="text" name='email' placeholder='이메일을 입력해주세요' className="loginid"
                            value={this.state.email} onChange={this.handleInputChange} required />
                    <button type="submit">전송</button>
                    </form>
                </div>
                <div style={{fontSize:14}}>아직 회원이 아니신가요?</div>
                <button className='signup-btn'><Link to='/signup'>간편 회원가입하기</Link></button>
            </div>
        </div>
    )
    }
}

export default Login;