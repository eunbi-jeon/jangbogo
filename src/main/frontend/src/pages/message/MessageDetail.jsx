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

        componentDidMount() {  
            const {id} = this.props.match.params; //MessageList Link 컴포넌트 경로에서 ID값을 추출하기 위해
            const accessToken = localStorage.getItem("accessToken");

            axios
                .get(`/api/messages/receiver/${id}`, {
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
                return (
                  <div>
                    {/* Object.keys(message).length가 0보다 큰 경우(즉, message 객체가 비어있지 않은 경우)에는 message 객체의 내용을 출력*/}
                    {Object.keys(message).length > 0 ? (
                      <div key={message.id}>
                        <h4>{message.sender}</h4>
                        <p></p>
                        <div>
                          <div> Content: {message.content}</div><br/>
                          <div> Sender: {message.senderName}</div><br/> 
                          <div>{message.createat}</div><br/>
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