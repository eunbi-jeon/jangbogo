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
                    <form onSubmit="#">
                        <input type="text" name='email' placeholder='이메일을 입력해주세요' className="form-input" required />
                        <input type="password" name='password' placeholder='이메일을 입력해주세요' className="form-input" required />
                        <div className="form-item">
                            <label for="region" className="form-title">지역 </label>
                            <div className="radio-btn">
                                <input type="radio" id="seoul" name="region" value="seoul" required />
                                <label for="seoul">서울</label>
                            </div>
                            <div className="radio-btn" >
                                <input type="radio" id="busan" name="region" value="busan" required />
                                <label for="busan">부산</label>
                            </div>
                            <div className="radio-btn">
                                <input type="radio" id="incheon" name="region" value="incheon" required />
                                <label for="incheon">인천</label>
                            </div>
                            <div className="radio-btn">
                                <input type="radio" id="daegu" name="region" value="daegu" required />
                                <label for="daegu">대구</label>
                            </div>
                            <div className="radio-btn">
                                <input type="radio" id="gwangju" name="region" value="gwangju" required />
                                <label for="gwangju">광주</label>
                            </div>
                            <div className="radio-btn">
                                <input type="radio" id="daejeon" name="region" value="daejeon" required />
                                <label for="daejeon">대전</label>
                            </div>
                            <div className="radio-btn">
                                <input type="radio" id="ulsan" name="region" value="ulsan" required />
                                <label for="ulsan">울산</label>
                            </div>
                            <div className="radio-btn">
                                <input type="radio" id="gyeonggi" name="region" value="gyeonggi" required />
                                <label for="gyeonggi">경기</label>
                            </div>
                            <div className="radio-btn">
                                <input type="radio" id="gangwon" name="region" value="gangwon" required />
                                <label for="gangwon">강원</label>
                            </div>
                        </div>
                        <div className="form-item">
                            <label for="age" className="form-title">연령 </label>
                            <div className="radio-btn">
                                <input type="radio" name="age" value="10s" required />
                                <label for="10s">10대</label>
                            </div>
                            <div className="radio-btn">
                                <input type="radio" name="age" value="20s" required />
                                <label for="20s">20대</label>
                            </div>
                            <div className="radio-btn">
                                <input type="radio" name="age" value="30s" required />
                                <label for="30s">30대</label>
                            </div>
                            <div className="radio-btn">
                                <input type="radio" name="age" value="40s" required />
                                <label for="40s">40대</label>
                            </div>
                            <div className="radio-btn">
                                <input type="radio" name="age" value="50s" required />
                                <label for="50s">50대</label>
                            </div>
                            <div className="radio-btn">
                                <input type="radio" name="age" value="60s" required />
                                <label for="60s">60대</label>
                            </div>
                            <div className="radio-btn">
                                <input type="radio" name="age" value="none" required />
                                <label for="none">해당 없음</label>
                            </div>
                        </div>
                    <button type="submit">회원가입 하기</button>
                    </form>
                </div>
            </div>
        </div>
        </div>
    )
}