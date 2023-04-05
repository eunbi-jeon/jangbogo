import React, { Component } from "react";
import axios from "axios";
import { Link } from 'react-router-dom'
import bestBtn1 from '../img/bestBtn1.png';
import bestBtn2 from '../img/bestBtn.png';
import '../css/boardDetail.css';

class BoardDetail extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isClicked: false,
      question: {},
      answers: [],
    };
  }

  componentDidMount() {

    const {id} = this.props.match.params;
    const token = localStorage.getItem("accessToken");
    console.log('게시글 ID111111:', id);
    axios
      .get(`http://localhost:8080/board/detail/${id}`, {
        headers: {
          Authorization: "Bearer " + token,
        },
      })
      .then((res) => {
         if (res.data && res.data.content) { // res.data.content가 비어있지 않을 때만 실행
          this.setState({ question: res.data.content });
        }
      })
      .catch((error) => {
        console.error(error);
      });
  }

  handleClick = () => {
    this.setState({ isClicked: !this.state.isClicked }, () => {
      const confirmMessage = this.state.isClicked
        ? "추천을 하시겠습니까?"
        : "추천을 취소 하시겠습니까?";
      const result = window.confirm(confirmMessage);
      if (result) {
        // 확인 버튼을 눌렀을 때의 동작
        console.log("사용자가 확인을 눌렀습니다.");
      } else {
        // 취소 버튼을 눌렀을 때의 동작
        console.log("사용자가 취소를 눌렀습니다.");
        this.setState({ isClicked: !this.state.isClicked }); // 클릭 이전 이미지 상태로 변경
      }
    });
  };

  render() {
    const { question } = this.state;
    if (!question || Object.keys(question).length === 0) {
      return <div>                
        <div className='boarDetailWrap'>
      {/* 내용 */}
      <table className="detailTable">
          <tr><td className='detailSubject' colSpan={3}>제목</td></tr>
          <tr><td colSpan={3}><hr className='hrLine'></hr></td></tr>
          <tr><td className='detailNickName'><span>닉네임</span><span className='wall'>/</span><span className='detailCreateAt'>작성날짜</span> </td><td className='detailReadCount'>조회수</td><td className='detailBestCount'>추천수</td></tr>
          <tr><td colSpan={3}><hr className='hrLine2'></hr></td></tr>
          <tr><td className='detailContent' colSpan={3}>내요요용</td></tr>
      </table>
      <div className='best'>
          <div>추천수</div><div><img src={this.state.isClicked? bestBtn2 : bestBtn1}  className='bestBtn' onClick={this.handleClick}/> </div>
      </div>
      <button className='goBackBtn' onClick={() => this.props.history.goBack()}>돌아가기</button>
      <hr className='hrLine'></hr>
      <div>전체 댓글 <span className='replyCount'>10</span></div>
      {/* 댓글 */}
      <table className='replyBox'>
          <tr>
          <td className='replyNickName'>댓글 닉네임<span className='replyCreateAt'>작성날짜</span></td>
          <td className='replyContent' colSpan={3}>댓글내요요용</td>
          <td className='modifyBtn'>수정</td>
          <td className='deleteBtn'>삭제</td>
          </tr>
      </table>
      <hr className='hrLine2'></hr>
  </div></div>;
    }

    // 실제 값 출력부분
    return (
      <div>
        {/* 내용 */}
        <table>
            <tr><td className='detailSubject' colSpan={3}>{question.subject}</td></tr>
            <tr><td className='detailNickName'><hr className='hrLine'></hr></td></tr>
            <tr><td className='detailContent'><span>{question.name}</span><span className='wall'>/</span><span className='detailCreateAt'>{question.createAt}</span></td><td className='detailReadCount'>{question.readCount}</td><td className='detailBestCount'>{question.bestCount}</td></tr>
            <tr><td colSpan={3}><hr className='hrLine2'></hr></td></tr>
            <tr><td className='detailContent' colSpan={3}>{question.content}</td></tr>
        </table>
        <div className='best'>
          <div>추천수</div><div><img src={this.state.isClicked? bestBtn2 : bestBtn1}  className='bestBtn' onClick={this.handleClick}/> </div>
      </div>
        <hr className='hrLine'></hr>
        {this.state.answers.map((answer) => (
          <div>
        <table className='replyBox'>
        <tr key={answer.id}>
          <td className='replyNickName'>{answer.name}</td>
          <td className='replyContent'>{answer.content}<span className='replyCreateAt'>{answer.createAt}</span></td>
          <td className='modifyBtn'><Link to={`/answer/modify/${answer.id}`}>수정</Link></td>
          <td className='deleteBtn'><Link to={`/answer/delete/${answer.id}`}>삭제</Link></td>
      </tr>
        </table>
        <hr className='hrLine2'></hr>
        </div>
      ))}
      </div>
  )
            }
          }
export default BoardDetail;