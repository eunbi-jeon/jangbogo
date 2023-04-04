import React, { useState } from 'react';
import axios from 'axios';
import "../../css/messageform.css";

function MessageForm({ user }) {
  const [content, setContent] = useState('');
  const [receiver, setReceiver] = useState('');
  const [modalIsOpen, setModalIsOpen] = useState(false);


  const handleSubmit = (e) => {
    e.preventDefault();
    if (!receiver || !content) {
      window.alert('Please enter a receiver and message content.');
      return;
    }
    //받는사람 member name 컬럼에서 조회
    axios.get('/api/members', {
      params: {
        name: receiver
      }
    })

    // 입력값과 같은 사용자 찾으면 id값 가져오기
      .then(res => {
        const member = res.data[0];
        if (member) {
          axios.post('/api/messages', {
            sender: user,
            receiver: member.id,
            content: content
          })

          // 해당하는 사용자 입력창에 가져오기
            .then(res => {
              console.log(res);
              setContent('');
              setReceiver(member.name);
              setModalIsOpen(false);
            })
            .catch(err => {
              console.log(err);
              window.alert('Failed to find the receiver.');
            });
          }
        });
  };
// 모달창 오픈
    const modal = document.querySelector('.modal');
const openBtn = document.querySelector('.open-btn');
const closeBtn = document.querySelector('.close-btn');

openBtn.addEventListener('click', () => {
  modal.classList.add('show');
});

closeBtn.addEventListener('click', () => {
  modal.classList.remove('show');
});


  return (
    <div>
    <div class="modal" isOpen={modalIsOpen}>
        <h2>쪽지 보내기</h2>
        <form onSubmit={handleSubmit}>
            <label for="content"></label>
            <input type="text" placeholder="받는 사용자" id="name" value={receiver} onChange={e => setReceiver(e.target.value)}/>
            <textarea id="content" placeholder="쪽지 내용" value={content} onChange={e => setContent(e.target.value)}/>
            <button type="submit">보내기</button>
        </form>
        <button class="close-btn"> 닫기</button>
    </div>

    {/* 쪽지 폼여는 버튼 */}
    <button class="open-btn">쪽지</button>
     
    </div>
  );
}

export default MessageForm;
