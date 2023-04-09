import React, { Component } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

import '../../css/messageList.css';
import '../../css/messageForm.css';


class MessageList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      messagesSender:[],
      messagesReceiver: [],
      title:'',
      content:'',
      receiverName:'',
      isModalOpen:false,
      selectedValue: '',
      findName:[],
      timerId: null
    };
    this.searchName = this.searchName.bind(this);
  }

  //쪽지 정보 불러오기
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
  
      this.setState({
        messagesSender: resSender.data.result.data,
        messagesReceiver: resReceiver.data.result.data
      });
    })).catch((error) => {
      console.log(error);
    });
  }

  handleSubmit = (e) => {
    e.preventDefault();
    const { title, content, selectedValue } = this.state;
    const message = {
      title: title,
      content: content,
      receiverName: selectedValue
    };
    
    if (title === '' || content === '' || receiverName === '') {
    alert('빈칸을 모두 채워주세요');
    return;
    }
    
    axios
      .post('/api/messages', message,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
          },
        }
      )
      .then(() => {
        alert('메세지 전송에 성공했습니다!');
        this.setState({isModalOpen: false})
        window.location.href='/messages/postbox'
      })
      .catch((error) => {
        console.error(error);
      });
  };
  
  //키보드 입력이 정지되고 1초뒤 회원정보 불러오기 실행
  handleChange = (event) => {
    const { value } = event.target;
    this.setState({ selectedValue: value });
  
    // 이전 타이머 취소
    if (this.state.timerId) {
      clearTimeout(this.state.timerId);
    }
  
    // 1초 후에 searchName 함수 실행
    const timerId = setTimeout(() => {
      if (this.state.selectedValue) {
        this.searchName();
      }
    }, 1000);
    this.setState({ timerId });
  };
  
    //회원정보 불러오기
    searchName = () => {
      const { selectedValue } = this.state;
    
      axios.get(`http://localhost:8080/api/messages/findName/${selectedValue}`)
      .then((res) => {
        const names = res.data.result.data.map(obj => obj.name);
        this.setState({ findName : names })
      }).catch((error) => {
      });
    }

    //회원정보 선택 후 배열 초기화
    handleSelect = (item) => {
      this.setState({ selectedValue: item });
      this.setState({ findName: [] });
    }

  render() {
    const { title, content } = this.state;
    return (
      <div className="messagelist-wrap">  
      <button className="DM-btn" type="button" onClick={() => this.setState({isModalOpen: true})}>쪽지 보내기</button>
          <div className="modal" style={{ display: this.state.isModalOpen ? 'block' : 'none' }}>
            <h2>새 쪽지</h2>
            <hr/>
            <div id='wrap'>
            <label htmlFor='selectedValue'></label>
            <form onSubmit={this.handleSubmit}>
            <input
                type='text'
                placeholder='받는 사람의 닉네임을 입력해주세요'
                id='receiverName'
                value={this.state.selectedValue}
                onChange={this.handleChange}
              />
              {this.state.findName.length > 0 && (
                <div className="findName-wrap">
                  {this.state.findName.map((item, index) => (
                    <div key={index} onClick={() => this.handleSelect(item)}>
                      {item}
                    </div>
                  ))}
                </div>
              )}
            <label htmlFor='title'></label>
            <input
              placeholder='쪽지 제목을 입력하세요'
              type='text'
              id='title'
              value={title}
              onChange={(e) => this.setState({title: e.target.value})} />
            <label htmlFor='content'></label>
            <textarea
              placeholder='쪽지 내용을 입력하세요'
              id='content'
              value={content}
              onChange={(e) => this.setState({content: e.target.value})}/>
            <button type="submit">보내기</button>
          </form>
            </div>
            <button className="close-btn" onClick={() => this.setState({isModalOpen: false})}>닫기</button>
          </div>
        <h1 className="messages-title">받은 쪽지</h1>
      <div className="messages-wrap">
          {this.state.messagesReceiver.map((message) => (
            <div className="messages" key={message.id}>
              <Link to={`/messages/postbox/receiver/${message.id}`}>
                <pre className="listTitle">{message.title}</pre>
                <pre className="listContent">{message.content}</pre>
                <pre className="listName"><b>From</b> {message.senderName}</pre>
              </Link>
            </div>
          ))}
      </div>
        <h1 className="messages-title">보낸 쪽지</h1>
        <div className="messages-wrap">
          {this.state.messagesSender.map((message) => (
            <div className="messages" key={message.id}>
              <Link to={`/messages/postbox/sender/${message.id}`}>
                <div className="listTitle">{message.title}</div>
                <pre className="listContent">{message.content}</pre>
                <pre className="listName"><b>To</b> {message.receiverName}</pre>
              </Link>
            </div>
          ))}
      </div>
      </div>
    );
  }
}

export default MessageList;