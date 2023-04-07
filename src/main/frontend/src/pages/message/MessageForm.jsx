import React, { useState } from 'react';
import axios from 'axios';

function MessageForm() {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [receiverNickname, setReceiver] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();

    axios.post('/api/messages', { headers: {
      'Authorization': `Bearer ${localStorage.getItem('accessToken')}`
    }, title, content, receiverNickname })
      .then(() => {
        // 쪽지 전송 성공
        setTitle('');
        setContent('');
        setReceiver('');
        alert('Message saved successfully');
      })
      .catch((error) => {
        // 쪽지 전송 실패
        console.error(error);
      });
  };

  return (
    
    <form onSubmit={handleSubmit}>
      <div>
        <h1>쪽지보내기</h1>
        <hr/>
        <label htmlFor="title">Title:</label>
        <input type="text" id="title" value={title} onChange={(e) => setTitle(e.target.value)} />
      </div>
      <div>
        <label htmlFor="content">Content:</label>
        <textarea id="content" value={content} onChange={(e) => setContent(e.target.value)} />
      </div>
      <div>
        <label htmlFor="receiverNickname">Receiver:</label>
        <input type="text" id="receiverNickname" value={receiverNickname} onChange={(e) => setReceiver(e.target.value)} />
      </div>
      <button type="submit">Send Message</button>
    </form>
  );
}

export default MessageForm;