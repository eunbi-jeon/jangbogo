import React, { Component } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

class MessageList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      messages: []
    };
  }
  componentDidMount() {
    const accessToken = localStorage.getItem("accessToken");

    axios
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
      <div className="">
        <h1>쪽지 리스트</h1>
        <hr />
        <ul>
          {messages.map((message) => (
            <li
              style={{ border: "1px solid tomato", borderRadius: "5px" }}
              key={message.id}
            >
              <Link to={`/messages/postbox/${message.id}`}>
                <h2 className="listTitle">{message.title}</h2>
                <h3 className="listSender">보낸 사람: {message.senderName}</h3>
                <h5>{message.createat}</h5>
              </Link>
            </li>
          ))}
        </ul>
      </div>
    );
  }
}

export default MessageList;