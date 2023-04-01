import React, { useState, useCallback } from 'react';
import "../css/signup.css"
import "../css/root.css"
import axios from "axios";


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

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [checkpw, setConfirmPassword] = useState("");
    const [nickname, setNickName] = useState("");
    const [selectedRegion, setSelectedRegion] = useState('');
    const [selectedAge, setSelectedAge] = useState('');

    // 오류 메세지
    const [emailMes, setEmailMes] = useState("")
    const [nameMes, setNameMes] = useState("")
    const [pwMes, setPwMes] = useState("")
    const [confirmPasswordMes, setConfirmPasswordMes] = useState("")

    //유효성 검사
    const [isName, setIsName] = useState(false)
    const [isEmail, setIsEmail] = useState(false)
    const [isPassword, setIsPassword] = useState(false)
    const [isConfirmPassword, setIsConfirmPassword] = useState(false)

    const data = {
        email: email,
        password: password,
        nickname: nickname,
        region: selectedRegion,
        age: selectedAge
    };


    const onNameHandler = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
        const nameRegex =  /^(?=.*[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|a-z|A-Z]).{2,10}$/
        const nameCurrent = e.target.value
        setNickName(nameCurrent)

        if (!nameRegex.test(nameCurrent)) {
            setNameMes('올바른 이름을 입력해주세요')
            setIsName(false)
        } else {
            setNameMes('')
            setIsName(true)
        }
    }, [])

    const onPasswordHandler = useCallback((e: React.ChangeEvent<HTMLInputElement>) =>{
        const passwordRegex = /^(?=.*[a-zA-Z])(?=.*[0-9]).{6,25}$/
        const passwordCurrent = e.target.value
        setPassword(passwordCurrent)

    if (!passwordRegex.test(passwordCurrent)) {
        setPwMes('숫자+영문자 조합으로 6자리 이상 입력하세요')
        setIsPassword(false)
    } else {
        setPwMes('')
        setIsPassword(true)
        }
    },[])

    const onConfirmPasswordHandler = useCallback((e: React.ChangeEvent<HTMLInputElement>)  => {
            const passwordConfirmCurrent = e.target.value
            setConfirmPassword(passwordConfirmCurrent)

            if (password === passwordConfirmCurrent) {
                setConfirmPasswordMes('')
                setIsConfirmPassword(true)
            } else {
                setConfirmPasswordMes('비밀번호가 일치하지 않습니다.')
                setIsConfirmPassword(false)
            }
        },
        [password]
    )


    const onChangeEmail = (e) => {
        const currentEmail = e.target.value;
        setEmail(currentEmail);
        const emailRegExp =
            /([\w-.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/

        if (!emailRegExp.test(currentEmail)) {
            setEmailMes("이메일 형식으로 작성해주세요.");
            setIsEmail(false);
        } else {
            setEmailMes("");
            setIsEmail(true);
        }
    };

    const onSubmitHandler = (event) => {
        event.preventDefault(); //리프레시 방지-> 방지해야 이 아래 라인의 코드들 실행 가능

        // 비밀번호와 비밀번호 확인 같을때 회원가입 되게 함
        if (password !== checkpw) {
            return alert('비밀번호와 비밀번호 확인은 같아야 합니다.')
        }   //여기서 걸리면 아래로 못감

        axios.post("http://localhost:8080/auth/create", data)
            .then((req) => {
                alert('회원가입이 완료되었습니다.');
                window.location.href = "/login";
            }).catch(err => {
                alert('회원가입에 실패하였습니다. 관리자에게 문의해주세요.');
                window.location.href = "/";
        })

    }

    // 닉네임 중복체크
    const handleNameCheck = (e) => {
        e.preventDefault();
        console.log(nickname);
        if (!nickname) {
            setNameMes("닉네임은 필수입력 항목입니다.");
        }else {
            axios.get("http://localhost:8080/auth/nameCheck",
                {
                    params: {nickname: nickname}
                })
                .then((req) => {
                    console.log("데이터 전송 성공")

                    if (req.data === 1) alert('중복된 닉네임입니다.');
                    else if (req.data === 0) {
                        alert('사용가능한 닉네임 입니다.');
                        setNameMes("")
                    }

                }).catch(err => {
                console.log(`데이터 전송 실패 ${err}`)
            })

        }


    }

    //이메일 중복체크
        const handleEmailCheck = (e) => {
            e.preventDefault();
            console.log(email);
            if (!email) {
                setEmailMes("이메일은 필수입력 항목입니다.");
            }else {
                axios.get("http://localhost:8080/auth/emailCheck",
                    {
                        params: {email: email}
                    })
                    .then((req) => {
                        console.log("데이터 전송 성공")
                        if (req.data === 1) alert('중복된 이메일입니다.');
                        else if (req.data === 0){
                            alert('사용가능한 이메일입니다.');
                            setEmailMes("")
                        }
                    }).catch(err => {
                    console.log(`데이터 전송 실패 ${err}`)
                })
            }
        }


    return (
        <div>
        <div className='loginContainer'>
            <div className='loginwrap'>
                <h1>회원가입</h1>
                <div className="loginline"></div>
                <div className="signUpbox">
                    <form onSubmit={onSubmitHandler}>
                        <input type="text" name='email' placeholder='이메일을 입력해주세요' className="form-input" 
                                value={email}  onChange={onChangeEmail} required/>
                        <div className='err-box'>
                        <button onClick={handleEmailCheck} className='check-btn'>중복확인</button>
                        <span className="err-msg" style={{marginBottom:0, marginLeft:10}}>{emailMes}</span>
                        </div>
                        <input type="password" name='password' placeholder='비밀번호를 입력해주세요.' className="form-input" 
                                value={password}  onChange={onPasswordHandler} required/>
                        <span className="err-msg">{pwMes}</span>
                        <input type="password" name='checkpw' placeholder='비밀번호 확인' className="form-input" 
                                value={checkpw}  onChange={onConfirmPasswordHandler} required/>
                        <span className="err-msg">{confirmPasswordMes}</span>
                        <input type="text" name='nickname' placeholder='닉네임을 입력해주세요' className="form-input" 
                                value={nickname} onChange={onNameHandler} required/>
                        <div className='err-box'>
                        <button onClick={handleNameCheck} className='check-btn'>중복확인</button>
                        <span className="err-msg" style={{marginBottom:0, marginLeft:10}}>{nameMes}</span>
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
                    <button type="submit" className='submit-btn' disabled={!(isEmail && isName && isPassword && isConfirmPassword)}>회원가입 하기</button>
                    </form>
                </div>
            </div>
        </div>
        </div>
    )
};

export default SignUpForm;