import React, { useState } from 'react';
import axios from 'axios';
import '../../css/messageForm.css';

function MessageForm() {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [receiverNickname, setReceiver] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);

  const searchName = (e) => {
    console.log(e.target.value);
    setReceiver(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    
    if (title === '' || content === '' || receiverNickname === '') {
    alert('빈칸을 모두 채워주세요');
    return;
    }
    
    axios
      .post(
        '/api/messages',
        { title, content, receiverNickname },
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
          },
        }
      )
      .then(() => {
        setTitle('');
        setContent('');
        setReceiver('');
        alert('Message saved successfully');
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="modal" style={{ display: isModalOpen ? 'block' : 'none' }}>
        <h2>새 쪽지</h2>
        <hr/>
        <div id='wrap'>
        <label htmlFor='receiverNickname'></label>
        <input
              type='text'
              placeholder='받는 사람의 닉네임을 입력해주세요'
              id='receiverNickname'
              value={receiverNickname}
              onChange={searchName}/>
        <label htmlFor='title'></label>
        <input
              placeholder='쪽지 제목을 입력하세요'
              type='text'
              id='title'
              value={title}
              onChange={(e) => setTitle(e.target.value)} />
        <label htmlFor='content'></label>
        <textarea
              placeholder='쪽지 내용을 입력하세요'
              id='content'
              value={content}
              onChange={(e) => setContent(e.target.value)}/>
          <button type="submit">보내기</button>
        </div>
        <button className="close-btn" onClick={() => setIsModalOpen(false)}>닫기</button>
      </div>
      <div className="wrap">
        <div>
          <h1>쪽지 기능</h1>
          <hr />
        </div>
        <div id="message_form">
          <div>
            <p className="open-btn" onClick={() => setIsModalOpen(true)}>쪽지 보내기</p>
          </div>
          <div>
            <form onSubmit={handleSubmit}>
              {/* Form input elements */}
            </form>
          </div>
        </div>
      </div>
    </form>
  );
}

export default MessageForm;
