import React, { useState } from 'react';
import axios from 'axios';
import '../../css/messageForm.css';

function MessageForm() {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [receiverNickname, setReceiver] = useState('');

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
        console.log('Message saved successfully');
        window.location.href = '/messages/postbox/receiver';
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className='wrap'>
        <div>
          <h1>새 쪽지</h1>
          <hr />
        </div>
        <div id='message_form'>
          <div>
            <label htmlFor='receiverNickname'></label>
            <input
              type='text'
              placeholder='받는 사람의 닉네임을 입력해주세요'
              id='receiverNickname'
              value={receiverNickname}
              onChange={searchName}
            />
          </div>
          <hr />
          <div>
            <label htmlFor='title'></label>
            <input
              placeholder='쪽지 제목을 입력하세요'
              type='text'
              id='title'
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
          </div>
          <div>
            <label htmlFor='content'></label>
            <textarea
              placeholder='쪽지 내용을 입력하세요'
              id='content'
              value={content}
              onChange={(e) => setContent(e.target.value)}
            />
          </div>
        </div>
        <button type='submit'>Send Message</button>
        <button type="button" onClick={() => { window.location.href = '/messages/postbox/receiver'; }}>받은 쪽지 목록</button>
        <button type="button" onClick={() => { window.location.href = '/messages/postbox/sender'; }}>보낸 쪽지 목록</button>
      </div>
    </form>
  );
}

export default MessageForm;