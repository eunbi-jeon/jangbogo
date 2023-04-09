import React, { Component } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import '../../css/message.css';

class MessageList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      messagesSender:[],
      messagesReceiver: []
    };
  }

  componentDidMount() {
    const accessToken = localStorage.getItem("accessToken");
  
    axios.all([
      axios.get("/api/messages/sender", {
        headers: {
          Authorization: `Bearer ${accessToken}`
        }
      }),
      axios.get("/api/messages/receiver", {
        headers: {
          Authorization: `Bearer ${accessToken}`
        }
      })
    ]).then(axios.spread((resSender, resReceiver) => {
      console.log("보낸 쪽지:", resSender.data);
      console.log("받은 쪽지:", resReceiver.data);
  
      this.setState({
        messagesSender: resSender.data.result.data,
        messagesReceiver: resReceiver.data.result.data
      });
    })).catch((error) => {
      console.log(error);
    });
  }
  
  handlePostClick = (id) => {
    console.log("메세지 ID : ", id);
  };

  render() {
    return (
      <div className="message-wrap">
      <div id="wrap">
        <h1>받은 쪽지</h1>
        <hr />
        <ul>
          {this.state.messagesReceiver.map((message) => (
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
      <div id="wrap">
        <h1>보낸 쪽지</h1>
        <button type="button" onClick={() => { window.location.href = '/messages'; }}>새 쪽지</button>
        <hr />
        <ul>
          {this.state.messagesSender.map((message) => (
            <li key={message.id}
            >
              <Link to={`/messages/postbox/sender/${message.id}`}>
                <h3 className="listName">받은 사람: {message.receiverName}</h3>
                <h2 className="listTitle">{message.title}</h2>
                <h5>{message.createAt}</h5>
              </Link>
            </li>
          ))}
        </ul>
      </div>
      </div>
    );
  }
}

export default MessageList;