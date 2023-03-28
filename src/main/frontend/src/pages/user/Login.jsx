import React, { Component } from 'react';
import '../../css/login.css';
import { NAVER_AUTH_URL ,KAKAO_AUTH_URL, ACCESS_TOKEN, REFRESH_TOKEN } from '../../constants';
import { login } from '../../util/APIUtils';
import { Link, Redirect } from 'react-router-dom'

import kakaoLogo from '../../img/Kakao-Login.png';
import naverLogo from '../../img/Naver-Login.png';

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
                    <h1 className="login-title">Sign In</h1>
                    <SocialLogin />
                    <div className="or-separator">
                        <span className="or-text">OR</span>
                    </div>
                    <LoginForm {...this.props} />
                    <span className="signup-link">New user? <Link to="/signup">Sign up!</Link></span>
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
                    <img src={kakaoLogo} alt="Kakao" /> Sign in with Kakao</a>
                <a className="btn btn-block social-btn kakao" href={NAVER_AUTH_URL}>
                    <img src={naverLogo} alt="Naver" /> Sign in with Naver</a>

            </div>
        );
    }
}

class LoginForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password: ''
        };
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const inputName = target.name;        
        const inputValue = target.value;

        this.setState({
            [inputName] : inputValue
        });        
    }

    handleSubmit(event) {
        event.preventDefault();   

        const loginRequest = Object.assign({}, this.state);

        login(loginRequest)
        .then(response => {
            localStorage.setItem(ACCESS_TOKEN, response.accessToken);
            localStorage.setItem(REFRESH_TOKEN, response.refreshToken);
            alert("로그인에 성공하였습니다.");
            this.props.history.push("/");
        }).catch(error => {
            alert((error && error.message) || '로그인에 실패하였습니다.');
        });
    }
    
    render() {
        return (
        <div className='loginContainer'>
            <div className='loginwrap'>
                <h1>로그인</h1>
                <div className="loginline"></div>
                <div className="loginbox">
                    <form onSubmit={this.handleSubmit}>
                    <input type="text" name='email' placeholder='이메일을 입력해주세요' className="loginid"
                            value={this.state.email} onChange={this.handleInputChange} required />
                    <input type="password" name='password' placeholder='비밀번호 입력해주세요' className="loginid" 
                                value={this.state.password} onChange={this.handleInputChange} required />
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
}

export default Login;