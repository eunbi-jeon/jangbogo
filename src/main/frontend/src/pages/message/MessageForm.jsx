import React, { useState } from 'react';
import axios from 'axios';
import '../../css/messageForm.css';

function MessageForm() {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [receiverNickname, setReceiver] = useState('');
  const [receiverInfo,setReceiverInfo] = useState(null);


  const searchName = (e) => {
    const nickname = e.target.value;
    axios
    .get(`/api/messages/findName/${nickname}`)
    .then((response) => {
      const receiverId = response.data.id;
      axios
      .get(`/api/messages/receiver/${receiverId}`)
      .then((response) => {
        setReceiverInfo(response.data);
      })
      .catch((error) => {
        console.error(error);
        setReceiverInfo(null);
      });
    })
    .catch((error) => {
      console.error(error);
      setReceiverInfo(null);
    });
    setReceiver(nickname);
  };
  
  

  const handleSubmit = (e) => {
    e.preventDefault();

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
        window.location.href = '/messages/postbox';
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className='wrap'>
        <div>
          <h1>쪽지보내기</h1>
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
            <input placeholder='쪽지 제목을 입력하세요' type='text' id='title'
              value={title} onChange={(e) => setTitle(e.target.value)}
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
      </div>
    </form>
  );
}

export default MessageForm;
