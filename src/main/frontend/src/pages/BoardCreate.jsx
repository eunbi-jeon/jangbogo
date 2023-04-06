import React, { useState } from 'react';
import axios from 'axios';

import '../css/boardcreate.css'

const BoardCreate = () => {
const [subject, setSubject] = useState('');
const [content, setContent] = useState('');

const handleTitleChange = (e) => {
setSubject(e.target.value);
}

const handleContentChange = (e) => {
setContent(e.target.value);
}

const handleSubmit = (e) => {
    e.preventDefault(); //새로고침 방지
    // 글 쓰기 API 호출
    const token = localStorage.getItem("accessToken");
    axios.post('http://localhost:8080/board/create', { subject, content }, {
    headers: {
    Authorization: "Bearer " + token,
    }
    }) // 입력된 데이터를 요청 바디에 포함하여 전송
    .then(response => {
    alert("글 등록 완료");
    //글 작성 완료 후 리스트페이지로 이동 추가
    window.location.href="/board/list";
    })
    .catch(error => {
    // 에러 처리 로직
    alert("오류");
    console.error(error);
    });
}

return (
<div className='createWrap'>
<form className='board-create-form'>
    <input type="text" value={subject} onChange={handleTitleChange} placeholder='제목을 입력해주세요' />
    <textarea value={content} onChange={handleContentChange} />
    <button className='board-btn' onClick={handleSubmit}>글 쓰기</button>
    </form>
</div>
);
}

export default BoardCreate;