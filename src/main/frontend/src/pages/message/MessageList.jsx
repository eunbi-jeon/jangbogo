//list
import React, { useState, useEffect } from "react";
import axios from "axios";

function MessageList(props) {
  const [messages, setMessages] = useState([]);

  const accessToken = localStorage.getItem("accessToken");
  const config = {
    headers: { Authorization: `Bearer ${accessToken}` }
  };  

  useEffect(() => {
    // H2 database에서 쪽지 내용을 가져오는 API endpoint
    axios
      .get("/api/messages/receiver", config)
      .then((res) => {
        // 가져온 쪽지 내용을 state에 저장
        console.log(res.data.result.data);
        setMessages(res.data.result.data);
        console.log(messages);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);
  

  return (
    <div>
      <h1>쪽지 리스트</h1>
      
      <ul>
        {messages.map((message) => (
          <li key={message.id}>
            <h2>{message.title}</h2>
            <p>보낸 사람: {message.sender}</p>
            <p>{message.content}</p>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default MessageList;