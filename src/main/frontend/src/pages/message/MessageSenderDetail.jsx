import React, {Component} from 'react';
import axios from 'axios';
import {withRouter} from 'react-router-dom';

class MessageDetail extends Component {
        constructor(props) {
            super(props);

            this.state = {
              message : {}
            };
        }
     
        async deleteMessage(id) {
          try {
            const accessToken = localStorage.getItem("accessToken");
            await axios.delete(`/api/messages/sender/${id}`, {
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
              this.props.history.push("/messages/postbox/sender");
              
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
                .get(`/api/messages/sender/${id}`, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                })
                .then(res => {
                    this.setState({ message : res.data.result.data});
                    console.log(res.data.result.data);
                })
                .catch(err => {
                    console.log(err);
                });
              }
              render() {
                const { message } = this.state;
                console.log({ message });
                return (
                  <div id='wrap'>
                    {/* Object.keys(message).length가 0보다 큰 경우(즉, message 객체가 비어있지 않은 경우)에는 message 객체의 내용을 출력*/}
                    {Object.keys(message).length > 0 ? (
                      <div key={message.id}>
                        <h4>{message.receiverName} 가 {message.senderName} 에게 보낸 쪽지</h4>
                        <hr/>
                        <p></p>
                        <div>
                        <div> Title : {message.title}</div><br/>
                          <div> Content: {message.content}</div><br/>
                          <hr/>
                          <div>{message.createat}</div><br/>
                        </div>
                        <button id='delete-btn' onClick={(event) => this.onRemove(event)}>삭제</button>
                        <button type="button" onClick={() => { window.location.href = '/messages/postbox/sender'; }}>보낸 쪽지 목록</button>
                        <button type="button" onClick={() => { window.location.href = '/messages'; }}>새 쪽지</button>
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