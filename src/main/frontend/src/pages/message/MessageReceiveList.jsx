import React, { Component } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import '../../css/message.css';

class MessageList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      messages: []
    };
  }
  componentDidMount() {
    const accessToken = localStorage.getItem("accessToken");

    axios//받은 쪽지 전부 확인
      .get("/api/messages/receiver", {
        headers: {
          Authorization: `Bearer ${accessToken}`
        }
      })
      .then((res) => {
        console.log("콘솔 data 출력" + res.data);
        this.setState({ messages: res.data.result.data });
      })
      .catch((error) => {
        console.log(error);
      });
  }

  handlePostClick = (id) => {
    console.log("메세지 ID : ", id);
  };

  render() {
    const { messages } = this.state;
    return (
      <div id="wrap">
        <h1>받은 쪽지</h1>
        <button type="button" onClick={() => { window.location.href = '/messages'; }}>새 쪽지</button>
        <button type="button" onClick={() => { window.location.href = '/messages/postbox/sender'; }}>보낸 쪽지 목록</button>
        <hr />
        <ul>
          {messages.map((message) => (
            <li key={message.id}
            >
              <Link to={`/messages/postbox/receiver/${message.id}`}>
                <h2 className="listTitle">{message.title}</h2>
                <h3 className="listName">보낸 사람: {message.senderName}</h3>
              </Link>
            </li>
          ))}
        </ul>
      </div>
    );
  }
}

export default MessageList;