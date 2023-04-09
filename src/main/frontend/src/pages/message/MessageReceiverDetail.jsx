import React, {Component} from 'react';
import axios from 'axios';
import {withRouter} from 'react-router-dom';

import "../../css/messageDetail.css";
import '../../css/messageForm.css';

class MessageDetail extends Component {
        constructor(props) {
            super(props);
            this.state = {
              message : {},
              isModalOpen:false,
              title:'',
              content:'',
              receiverName:''
            };
        }
     
        async deleteMessage(id) {
          try {
            const accessToken = localStorage.getItem("accessToken");
            await axios.delete(`/api/messages/receiver/${id}`, {
              headers: {
                Authorization: `Bearer ${accessToken}`
              }
          });
          } catch (error) {
            throw new Error('쪽지 삭제에 실패하였습니다. 관리자에게 문의하세요');
          }
        }
      
        async onRemove(event) {
          const {id} = this.props.match.params; // MessageList Link 컴포넌트 경로에서 ID값을 추출하기 위해
          // 삭제버튼 클릭시 확인창 띄우기
          if (window.confirm("정말 쪽지를 삭제하시겠습니까?")) {
            try {
              await this.deleteMessage(id); // id값을 deleteMessage() 메서드에 전달
              alert("쪽지가 삭제되었습니다.");
              //삭제 후 리스트 페이지로 이동
              this.props.history.push("/messages/postbox/receiver");
              
            } catch (error) {
              alert(error && error.message);
            }
          } else {
            alert("쪽지 삭제를 취소합니다.");
            // 삭제 취소 후 페이지 유지
          }
        }
        

        componentDidMount() {  
            const {id} = this.props.match.params; //MessageList Link 컴포넌트 경로에서 ID값을 추출하기 위해
            const accessToken = localStorage.getItem("accessToken");

            axios
            //받은 쪽지 디테일 정보
                .get(`/api/messages/receiver/${id}`, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                })
                .then(res => {
                    this.setState({ message : res.data.result.data});
                    this.setState({ receiverName : res.data.result.data.senderName});
                })
                .catch(err => {
                    console.log(err);
                });
              }

              //쪽지보내기
              handleSubmit = (e) => {
                e.preventDefault();
                const { title, content, receiverName } = this.state;
                const message = {
                  title: title,
                  content: content,
                  receiverName: receiverName
                };

                console.log(receiverName);
                
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

              render() {
                const { message } = this.state;
                const { title, content } = this.state;

                return (
                  <div className='message-detail-wrap'>
                    {/* Object.keys(message).length가 0보다 큰 경우(즉, message 객체가 비어있지 않은 경우)에는 message 객체의 내용을 출력*/}
                    {Object.keys(message).length > 0 ? (
                      <div className='message-detail' key={message.id}>
                        <div className='message-receiver'><b>{message.senderName}</b>에게 받은 쪽지</div>
                        <div>
                        <div className='message-title'>{message.title}</div><br/>
                        <hr/>
                          <div className='message-content'>{message.content}</div><br/>
                          <hr/>
                          <div>{message.createat}</div><br/>
                        </div>
                        <div className='message-btn-wrap'>
                          <button onClick={(event) => this.onRemove(event)}>삭제</button>
                          <button type="button" onClick={() => { window.location.href = '/messages/postbox/receiver'; }}>받은 쪽지 목록</button>
                          <button type="button" onClick={() => this.setState({isModalOpen: true})}>새 쪽지</button>
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
                                value={this.state.receiverName}
                                name='receiverName'
                                readOnly />
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
                        </div>
                      </div>
                    ) : (
                        //  그렇지 않은 경우에는 "데이터를 불러오는 중입니다."라는 문구를 출력 
                      <div>데이터를 불러오는 중입니다.</div>
                    )}
                  </div>
                );
              }
}

export default withRouter(MessageDetail);