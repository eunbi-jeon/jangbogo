import React, { useState } from 'react';
import axios from 'axios';

const BoardCreate = () => {
const [subject, setSubject] = useState('');
const [content, setContent] = useState('');

const handleTitleChange = (e) => {
setSubject(e.target.value);
}

const handleContentChange = (e) => {
setContent(e.target.value);
}

const handleSubmit = () => {
// 글 쓰기 API 호출
const token = localStorage.getItem("accessToken");
axios.post('http://localhost:8080/board/create', { subject, content }, {
headers: {
Authorization: "Bearer " + token,
}
}) // 입력된 데이터를 요청 바디에 포함하여 전송
.then(response => {
alert("글 등록 완료");
console.log(response.data);
})
.catch(error => {
// 에러 처리 로직
alert("오류");
console.error(error);
});
}

return (
<div>
<form>
<input type="text" value={subject} onChange={handleTitleChange} />
<textarea value={content} onChange={handleContentChange} />
<button onClick={handleSubmit}>글 쓰기</button>
</form>
</div>
);
}

export default BoardCreate;