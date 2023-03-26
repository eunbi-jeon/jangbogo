import React, { useState} from 'react';
import "../css/signup.css"
import "../css/root.css"

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


const SignUpForm = () => {

    const [selectedRegion, setSelectedRegion] = useState('');
    const [selectedAge, setSelectedAge] = useState('');

    return (
        <div>
        <div className='loginContainer'>
            <div className='loginwrap'>
                <h1>회원가입</h1>
                <div className="loginline"></div>
                <div className="loginbox">
                    <form onSubmit='#'>
                        <input type="text" name='email' placeholder='이메일을 입력해주세요' className="form-input" required />
                        <input type="password" name='password' placeholder='비밀번호를 입력해주세요' className="form-input" />
                        <input type="text" name='nickname' placeholder='닉네임을 입력해주세요' className="form-input" />
                        <div className="form-item">
                        <label htmlFor="region" className="form-title">지역 </label>
                        {regions.map((region) => (
                            <div key={region.id} className="radio-btn">
                            <input
                                type="radio"
                                id={region.id}
                                name="region"
                                value={region.value}
                                checked={selectedRegion === region.value}
                                onChange={(e) => setSelectedRegion(e.target.value)}
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
                                checked={selectedAge === age.value}
                                onChange={(e) => setSelectedAge(e.target.value)}
                            />
                            <label htmlFor={age.id}>{age.value}</label>
                            </div>
                        ))}
                        </div>
                    <button type="submit">회원가입 하기</button>
                    </form>
                </div>
            </div>
        </div>
        </div>
    )
};

export default SignUpForm;