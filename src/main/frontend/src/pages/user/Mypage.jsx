import React, { Component } from 'react';
import { Link } from 'react-router-dom';

import axios from 'axios';

import { deleteThumb } from '../../util/APIUtils'
import '../../css/myPage.css';
import defaultimg  from '../../img/default-profile-img.png';

const path = './img'

class Mypage extends Component {
    constructor(props) {
        super(props);
        this.fileInputRef = React.createRef();
        this.state = { boards: [], answers: [] };
      }



    componentDidMount() {
        const token = localStorage.getItem('accessToken');

        const getboard = axios.get('http://localhost:8080/board/my', {
            headers: {
              Authorization: 'Bearer ' + token,
            },
          });

        const getanswer = axios.get('http://localhost:8080/answer/my', {
            headers: {
              Authorization: 'Bearer ' + token,
            },
          });

          axios.all([getboard, getanswer])
          .then(axios.spread((...responses) => {
            const board = responses[0];
            const answer = responses[1];
            if (board.data) {
              this.setState({ boards: board.data });
            }
            if (answer.data) {
              this.setState({ answers: answer.data });
            }
          }))
          .catch((error) => {
            console.error(error);
          });
    }
      
      handleFileInputChange = (event) => {
        const file = event.target.files[0];
        const formData = new FormData();
        formData.append("file", file);

        const accessToken = localStorage.getItem("accessToken");
        const config = {
          headers: { Authorization: `Bearer ${accessToken}` }
        };    

        axios
        .post("http://localhost:8080/auth/thumbnail/update", formData, config)
        .then((response) => {
            alert("프로필 사진이 변경되었습니다.");
            window.location.href = "/mypage";
        }).catch((error) => {
            alert((error && error.message) || '프로필 사진 변경에 실패하였습니다. 관리자에게 문의하세요');
            window.location.href = "/mypage"; 
        })

      };

      deleteThumbnail(event) {
        deleteThumb()
        .then((response) => {
            alert("프로필 사진이 삭제되었습니다.");
            window.location.href = "/mypage";
        }).catch((error) => {
            alert((error && error.message) || '프로필 사진 삭제에 실패하였습니다. 관리자에게 문의하세요');
            window.location.href = "/mypage"; 
        })
      };

    render() {
        return (
            <div className="profile-container">
                <div className="container">
                    <div className="profile-info">
                        <div className="profile-avatar">
                            { 
                                this.props.currentUser.information.imageUrl ? (
                                    <img className='profile-img' 
                                    src={this.props.currentUser.information.imageUrl}
                                    alt={this.props.currentUser.information.name}/>
                                ) : (
                                    <img className='profile-img' 
                                    src = {defaultimg}
                                    alt={this.props.currentUser.information.name}/>
                                )
                            }
                        </div>
                        <div className='img-form-box'>
                                <input
                                    type="file"
                                    name="file"
                                    accept="image/*"
                                    ref={this.fileInputRef}
                                    onChange={this.handleFileInputChange}
                                    style={{ display: "none" }}
                                    />
                                <button className='img-form-btn' onClick={() => this.fileInputRef.current.click()}>
                                프로필 사진 변경
                                </button>
                                <button label="이미지 제거" className='img-form-btn' onClick={this.deleteThumbnail}>프로필 사진 제거</button>
                        </div>
                        <div className="profile-name">
                           <h2>{this.props.currentUser.information.name}</h2>
                           <p className="profile-email">{this.props.currentUser.information.email}</p>
                        </div>
                        <button className='modify-btn'><Link to='/setting/profile'>회원정보 수정</Link></button>
                    </div>
                    <div className='profile-info-etc'>
                        <div className='etc-box'>
                            <div className='box-title'>내가 작성한 글</div>
                            {this.state.boards.map((board) => (
                                    <li><Link to={`/board/detail/${board.id}`}>
                                                {board.subject}
                                        </Link></li>
                                        ))}
                            <button className='more-btn'>더보기</button>
                        </div>
                        <div className='etc-box'>
                            <div className='box-title'>내가 작성한 댓글</div>
                            {this.state.answers.map((answer) => (
                                    <li><Link to={`/board/detail/${answer.id}`}>
                                                {answer.subject}
                                        </Link></li>
                                        ))}
                            <button className='more-btn'>더보기</button>
                        </div>
                    </div>
                </div>    
            </div>
        );
    }
}

export default Mypage