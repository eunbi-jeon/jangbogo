import React, {Component} from 'react';
import axios from 'axios';
import {useParams} from 'react-router-dom';

class MessageDetail extends Component {
        constructor(props) {
            super(props);

            this.state = {
              message : []
            };
        }

        MessageDetail() {
            const {id} = this.props.match.params;
            const accessToken = localStorage.getItem("accessToken");

            axios
                .get(`/api/messages/receiver/{id}`, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                })
                .then(res => {
                    this.setMessage({ message : [res.data.result.data]});
                    console.log(res.data.result.data);
                })
                .catch(err => {
                    console.log(err);
                });
              }

    // return (
    //     <div>
    //     {this.data.map((message) => (
    //         <{message.id}
        
    //         <h2>{message.sender}</h2>
    //         <p></p>
    //         <div >
    //             <strong>Content:</strong>
    //             {message.content}<br/>
    //             <strong>Sender:</strong>
    //             {message.sender}<br/> {message.createBy}<br/>
    //         </div>
    //     ))
    //     </div>
    //     }
    // );
        
}

export default MessageDetail;