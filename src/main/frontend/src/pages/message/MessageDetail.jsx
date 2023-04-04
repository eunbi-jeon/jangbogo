import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import "../../css/messageform.css";

function MessageDetail() {
  const { id } = useParams();
  const [message, setMessage] = useState({});

  useEffect(() => {
    axios.get(`/api/messages/receiver/{id}`)
      .then(res => {
        setMessage(res.data);
      })
      .catch(err => {
        console.log(err);
      });
  }, [id]);

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
  <div class="modal">
    <h2>받은 쪽지</h2>
      <form>
          <label for="message"></label>
          <span id="content">{message.content}</span>
          <span id="sender" name="sender">{message.sender}</span>
          <span id="createAt" name="createAt">{message.createBy}</span>
      </form>
    <button class="close-btn">닫기</button>
    <button class="delete-btn">삭제</button>
  </div>

  <p class="open-btn">쪽지</p>
</div>
);
}

export default MessageDetail;
