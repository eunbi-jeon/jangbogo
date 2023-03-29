import React, { Component } from 'react';
import { Link } from 'react-router-dom';

import '../../css/myPage.css';

class Mypage extends Component {
    constructor(props) {
        super(props);
        console.log(props);
    }

    render() {
        return (
            <div className="profile-container">
                <div className="container">
                    <div className="profile-info">
                        <div className="profile-avatar">
                            { 
                                this.props.currentUser.information.imageUrl ? (
                                    <img className='profile-img' src={this.props.currentUser.information.imageUrl} alt={this.props.currentUser.information.name}/>
                                ) : (
                                    <div className="text-avatar">
                                        <span>{this.props.currentUser.information.name && this.props.currentUser.information.name[0]}</span>
                                    </div>
                                )
                            }
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
                                <li>작성글 1</li>
                                <li>작성글 2</li>
                                <li>작성글 3</li>
                                <li>작성글 4</li>
                                <li>작성글 5</li>
                            <button className='more-btn'>더보기</button>
                        </div>
                        <div className='etc-box'>
                            <div className='box-title'>내가 작성한 댓글</div>
                                <li>작성 댓글 1</li>
                                <li>작성 댓글 2</li>
                                <li>작성 댓글 3</li>
                                <li>작성 댓글 4</li>
                                <li>작성 댓글 5</li>
                            <button className='more-btn'>더보기</button>
                        </div>
                        <div className='etc-box'>
                            <div className='box-title'>내가 즐겨찾는 물품</div>
                            <div className='fav-box'>
                                <span className='fav-site'>물품 1</span>
                                <span className='fav-site'>물품 2</span>
                                <span className='fav-site'>물품 3</span>
                                </div>
                        </div>
                        <div className='etc-box'>
                            <div className='box-title'>내가 즐겨찾는 품목</div>
                            <div className='fav-box'>
                                <span className='fav-item'>품목 1</span>
                                <span className='fav-item'>품목 2</span>
                                <span className='fav-item'>품목 3</span>
                                </div>
                        </div>
                    </div>
                </div>    
            </div>
        );
    }
}

export default Mypage