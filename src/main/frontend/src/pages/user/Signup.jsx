import React, { Component } from 'react';
import '../../css/signup.css';
import { Link, Redirect } from 'react-router-dom'
import { NAVER_AUTH_URL ,KAKAO_AUTH_URL } from '../../constants';
import { signup } from '../../util/APIUtils';

import kakaoLogo from '../../img/Kakao-Login.png';
import naverLogo from '../../img/Naver-Login.png';


const regions = [
    { id: 'seoul', value: '서울' },
    { id: 'gyeonggi', value: '경기' },
    { id: 'incheon', value: '인천' },
    { id: 'gangwon', value: '강원' },
    { id: 'chungcheong', value: '충청' },
    { id: 'daejeon', value: '대전' },
    { id: 'daegu', value: '대구' },
    { id: 'gwangju', value: '광주' },
    { id: 'busan', value: '부산' },
    { id: 'ulsan', value: '울산' },
    { id: 'jeonla', value: '전라' },
    { id: 'gyeongsang', value: '경상' },
    { id: 'jeju', value: '제주' }
  ];


  const ages = [
    { id: '10s', value: '10대' },
    { id: '20s', value: '20대' },
    { id: '30s', value: '30대' },
    { id: '40s', value: '40대' },
    { id: '50s', value: '50대' },
    { id: '60s', value: '60대' },
    { id: 'none', value: '체크 안 함' }
  ];


class Signup extends Component {
    render() {
        if(this.props.authenticated) {
            return <Redirect
                to={{
                pathname: "/",
                state: { from: this.props.location }
            }}/>;            
        }

        return (
            <div className="signup-container">
                <div className="signup-content">
                    <SignupForm {...this.props} />
                </div>
            </div>
        );
    }
}


class SignupForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            email: '',
            password: '',
            region: '',
            age: ''
        }
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

        const signUpRequest = Object.assign({}, this.state);

        signup(signUpRequest)
        .then(response => {
            alert("회원가입에 성공하셨습니다.");
            this.props.history.push("/login");
        }).catch(error => {
            alert((error && error.message) || '예기치 않은 문제가 발생하였습니다.');            
        });
    }

    render() {
        return (
        <div>
        <div className='loginContainer'>
            <div className='loginwrap'>
                <h1>회원가입</h1>
                <div className="loginline"></div>
                <div className="signUpbox">
                    <form onSubmit={this.handleSubmit}>
                        <input type="text" name='email' placeholder='이메일을 입력해주세요' className="form-input" 
                                value={this.state.email}  onChange={this.handleInputChange} required/>
                        <div className='err-box'>
                        {/* <button onClick={handleEmailCheck} className='check-btn'>중복확인</button> */}
                        {/* <span className="err-msg" style={{marginBottom:0, marginLeft:10}}>{emailMes}</span> */}
                        </div>
                        <input type="password" name='password' placeholder='비밀번호를 입력해주세요.' className="form-input" 
                                value={this.state.password}  onChange={this.handleInputChange} required/>
                        {/* <span className="err-msg">{pwMes}</span> */}
                        <input type="password" name='checkpw' placeholder='비밀번호 확인' className="form-input" 
                                value={this.state.checkpw}  onChange={this.handleInputChange} required/>
                        {/* <span className="err-msg">{confirmPasswordMes}</span> */}
                        <input type="text" name='name' placeholder='닉네임을 입력해주세요' className="form-input" 
                                value={this.state.name} onChange={this.handleInputChange} required/>
                        <div className='err-box'>
                        {/* <button onClick={handleNameCheck} className='check-btn'>중복확인</button> */}
                        {/* <span className="err-msg" style={{marginBottom:0, marginLeft:10}}>{nameMes}</span> */}
                        </div>
                        <div className="form-item">
                        <label htmlFor="region" className="form-title">지역 </label>
                        {regions.map((region) => (
                            <div key={region.id} className="radio-btn">
                            <input
                                type="radio"
                                id={region.id}
                                name="region"
                                value={region.value}
                                checked={region === region.value}
                                onChange={this.handleInputChange}
                            />
                            <label htmlFor={region.id}>{region.value}</label>
                            </div>
                        ))}
                        </div>
                        <div className="form-item">
                        <label htmlFor="age" className="form-title">연령</label>
                        {ages.map((age) => (
                            <div key={age.id} className="radio-btn">
                            <input
                                type="radio"
                                id={age.id}
                                name="age"
                                value={age.value}
                                checked={age === age.value}
                                onChange={this.handleInputChange}
                            />
                            <label htmlFor={age.id}>{age.value}</label>
                            </div>
                        ))}
                        </div>
                    <button type="submit" className='submit-btn'>회원가입 하기</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
        );
    }
}

export default Signup