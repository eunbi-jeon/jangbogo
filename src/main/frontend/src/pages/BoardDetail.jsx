import React, { Component } from "react";
import axios from "axios";
import { Link } from 'react-router-dom'
import bestBtn1 from '../img/bestBtn1.png';
import bestBtn2 from '../img/bestBtn.png';
import '../css/boardDetail.css';
import { useSelector } from "react-redux";

class BoardDetail extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isClicked: false,
      question: [],
      answer:[],
      answerContent: "",
      member:[],
      showReplyForm: false,
      replyContent:"",
      isClick: false,
      isVoted: false,
      isModify: false,
      board_id:null,
      region:''
    };
  }
  

  handleInputChange = (event) => {
    this.setState({ replyContent: event.target.value });
  };
  handleTextareaChange = (event) => {
    this.setState({ answerContent: event.target.value });
  };
  handleReplyButtonClick = (parentId) => {
    this.setState({ showReplyForm: true, selectedReplyId: parentId });
  };

  // 게시글 삭제
  questionDeleteClick = () => {
    const { id } = this.props.match.params;
    const token = localStorage.getItem("accessToken");
    const result = window.confirm("게시글을 삭제 하시겠습니까?");
    if (result) {
      axios
        .get(`http://localhost:8080/board/delete/${id}`, {
          headers: {
            Authorization: "Bearer " + token,
          },
        })
        .then((res) => {
          window.location.href = `/board/list`;
        })
        .catch((error) => { 
          console.error(error);
        });
    }
  }


  //댓글 삭제     
  answerDeleteClick = (id) => {
    const token = localStorage.getItem("accessToken");
    const result = window.confirm("댓글을 삭제 하시겠습니까?");
    if (result) {
      axios
        .get(`http://localhost:8080/answer/delete/${id}`, {
          headers: {
            Authorization: "Bearer " + token,
          },
        })
        .then((res) => {
          window.location.reload(); // 페이지 리프래시
        })
        .catch((error) => { 
          console.error(error);
        });
    }
  }
  
  
  handleEditClick = (reply) => {
    this.setState({
      isModify: true,
      selectedReplyId: reply.id,
      replyContent: reply.content
    });
  }
  
  
  //추천, 추천취소
  handleClick = () => {
    
    this.setState({ isClicked: !this.state.isClicked }, () => {

      const confirmMessage = this.state.isClicked
        ? "추천을 하시겠습니까?"
        : "추천을 취소 하시겠습니까?";
      const result = window.confirm(confirmMessage);
      

      if (result) {
        console.log("사용자가 확인을 눌렀습니다.");
        
        const { id } = this.props.match.params;
        const token = localStorage.getItem("accessToken");

        axios
        .put(`http://localhost:8080/board/${id}/vote`, {}, {
          headers: {
            Authorization: "Bearer " + token,
          },
        })
        .then((res) => {
          window.location.href = `/board/detail/${id}`;
        })
        .catch((error) => {
          console.error(error);
        });
    } else {
      this.setState({ isClicked: !this.state.isClicked }); // 클릭 이전 이미지 상태로 변경
    }
  });
};

componentDidMount() {
  const { id } = this.props.match.params;
  const { board_id } = this.props.match.params;
  const { region } = this.props.match.params;
      
  this.setState({
          board_id: board_id,
          region: region
  });
  
  const token = localStorage.getItem("accessToken");

  axios
    .get(`http://localhost:8080/board/detail/${id}`, {
      headers: {
        Authorization: "Bearer " + token,
      },
    })
    .then((res) => {
      if (res.data.content != null) {
        this.setState({ question: [res.data] }, () => {
          console.log(this.state.question); 
        });
        this.setState({ answer: [res.data.answerList] }, () => {
          console.log(this.state.answer);
        });
      }
    })
    .catch((error) => {
      console.error(error);
    });
}


componentDidMount() {
  const { id } = this.props.match.params;
  const token = localStorage.getItem("accessToken");
  
  axios
    .get(`http://localhost:8080/board/detail/${id}`, {
      headers: {
        Authorization: "Bearer " + token,
      },
    })
    .then((res) => {
      const memberId = res.data.member.id;
      const targetId = memberId; // 찾고자 하는 id 값
      const voter = res.data.question.voter;
        this.setState({ 
          question: [res.data.question], 
          member: [res.data.member], 
          memberId: memberId,
          answer: [res.data.question.answerList],
          isVoted: voter.find(obj => obj.id === targetId)
        });
    })
    .catch((error) => {
      console.error(error);
    });
}

handleSaveEditClick = (id) => {
  const token = localStorage.getItem("accessToken");
  console.log("answer id ``````````````" + id)
  axios
    .put(
      `http://localhost:8080/answer/modify/${id}`,
      { content: this.state.replyContent },
      {
        headers: {
          Authorization: "Bearer " + token,
        },
      }
    )
    .then((res) => {
      console.log("res~~~~~~~~~~~~~~~~~~~~~" + res)
      window.confirm("댓글 수정 완료")
      window.location.reload();
    })
    .catch((error) => {
      console.error(error);
    });
};


  handleCancelEditClick = () => {
    this.setState({ isModify: false, selectedReplyId: null, replyContent: "" });
  };

  handleSubmitAnswer = (parentId) => {
    const { id } = this.props.match.params;
    const token = localStorage.getItem("accessToken");
    const result = window.confirm("댓글을 저장 하시겠습니까?.");
        if(result){
          axios
          .post(
            `http://localhost:8080/board/answer/create/${id}`,
            { content: this.state.answerContent, parentId: this.state.parentId},
            {
              headers: {
                Authorization: "Bearer " + token,
              },
            }
          )
          .then((res) => {
            window.location.href = `/board/detail/${id}`;
    
          })
          .catch((error) => {
            console.error(error);
          });
          
      };
    }


  render() {
    const { isVoted } = this.state;
    const { showReplyForm } = this.state; 
    return (
      <div>
        {isVoted ? (
                  <div>
                    <div className="board-detail-wrap">
        {/* 내용 */}
        {this.state.question.map((board) => (
          <table className="detailTable" key={board.id}>
            <tbody>
            <tr><td className='detailSubject' colSpan={3}>{board.subject}</td></tr>
            <tr><td colSpan={3}><hr className='hrLine'></hr></td></tr>
            <tr><td className='detailNickName'><span>{board.name.name}</span>
                 <span className='detailCreateAt'>{new Date(board.createAt).toLocaleDateString()}</span> </td>
                 <td className='detailReadCount'>조회수 {board.readCount}</td>
                 <td className='detailBestCount'>추천 수 {board.voter ? board.voter.length : 0}</td></tr>
                 <td className="detailmodifyBtn"><span className='modifyBtn'><Link to={`/board/modify/${board.id}`}>수정</Link></span><span className='deleteBtn' onClick={this.questionDeleteClick}>삭제</span></td>

             <tr><td colSpan={3}><hr className='hrLine2'></hr></td></tr>

              <tr><td className='detailContent' colSpan={3}>{board.content}</td></tr>
            </tbody>
            </table>
        ))}
        
              <div className='best'>
                <div style={{marginBottom:10}}>추천하기</div>
                  <div><img src={bestBtn2} className='bestBtn' onClick={this.handleClick} /></div>
                  <div>          
              </div>       
                  
      
    </div>
    {this.state.answer.map((ans) => (
  <div>
    <table className='replyBox'>
      {ans.map((reply) => (
        <tr key={reply.id}>
          <td className='replyNickName'>{reply.name.name}</td>
          <td className='replyContent'>
  {this.state.isModify && this.state.selectedReplyId === reply.id ? (
    <form>
      <input
        type="text"
        value={this.state.answerContent}
        onChange={(e) => this.handleInputChange(e)}
      />
      <button onClick={() => this.handleSaveEditClick(reply.id)}>
        저장
      </button>
      <button onClick={() => this.handleCancelEditClick(reply.id)}>취소</button>
    </form>
  ) : (
    <>
      {reply.content}
      <span className='replyCreateAt'>
        {new Date(reply.createAt).toLocaleDateString()}
      </span>
    </>
  )}
</td>
          <td>
            {!this.state.showReplyForm && (
              <button onClick={() => this.handleReplyButtonClick(reply.id)}>
                댓글 달기
              </button>
            )}
            {this.state.showReplyForm && reply.id === this.state.selectedReplyId && (
              <form>
                <input
                  type="text"
                  value={this.state.answerContent}
                  onChange={this.handleInputChange}
                />
                <button onClick={() => this.handleSubmitAnswer(reply.id)}>
                  댓글 작성
                </button>
              </form>
            )}
          </td>
          <td className='modifyBtn' onClick={() => this.handleEditClick(reply)}>
            수정
          </td>
          <td className='deleteBtn' onClick={() => this.answerDeleteClick(reply.id)}>
            삭제
          </td>
        </tr>
      ))}
    </table>
  </div>
))}
              <div className="answer-wrap">
        <textarea
          className="answer-text"
          onChange={this.handleTextareaChange}
          value={this.state.answerContent}
        ></textarea>
        <button className="answer-btn" onClick={this.handleSubmitAnswer}>답변 달기</button>
        </div>

                    </div>
                  </div>) 
                  : /*  여기부터는 false 값일때 출력들 */ 
                  (<div>
        <div className="board-detail-wrap">
        {/* 내용 */}
        {this.state.question.map((board) => (
          <table className="detailTable" key={board.id}>
            <tbody>
            <tr><td className='detailSubject' colSpan={3}>{board.subject}</td></tr>
            <tr><td colSpan={3}><hr className='hrLine'></hr></td></tr>
            <tr><td className='detailNickName'><span>{board.name.name}</span>
                 <span className='detailCreateAt'>{new Date(board.createAt).toLocaleDateString()}</span> </td>
                 <td className='detailReadCount'>조회수 {board.readCount}</td>
                 <td className='detailBestCount'>추천 수 {board.voter ? board.voter.length : 0}</td>
                 </tr> <td className="detailmodifyBtn"><span className='modifyBtn'><Link to={`/board/modify/${board.id}`}>수정</Link></span><span className='deleteBtn' onClick={this.questionDeleteClick}>삭제</span></td>


             <tr><td colSpan={3}><hr className='hrLine2'></hr></td></tr>

              <tr><td className='detailContent' colSpan={3}>{board.content}</td></tr>
            </tbody>
            </table>
        ))}
        
              <div className='best'>
                <div style={{marginBottom:10}}>추천하기</div>
                <img src={bestBtn1} className='bestBtn' onClick={this.handleClick} />

      
    </div>
    {this.state.answer.map((ans) => (
  <div>
    <table className='replyBox'>
      {ans.map((reply) => (
        <tr key={reply.id}>
          <td className='replyNickName'>{reply.name.name}</td>
          <td className='replyContent'>
  {this.state.isModify && this.state.selectedReplyId === reply.id ? (
    <form>
      <input
        type="text"
        value={this.state.answerContent}
        onChange={(e) => this.handleInputChange(e)}
      />
      <button onClick={() => this.handleSaveEditClick(reply.id)}>
        저장
      </button>
      <button onClick={() => this.handleCancelEditClick(reply.id)}>취소</button>
    </form>
  ) : (
    <>
      {reply.content}
      <span className='replyCreateAt'>
        {new Date(reply.createAt).toLocaleDateString()}
      </span>
    </>
  )}
</td>
          <td>
            {!this.state.showReplyForm && (
              <button onClick={() => this.handleReplyButtonClick(reply.id)}>
                댓글 달기
              </button>
            )}
            {this.state.showReplyForm && reply.id === this.state.selectedReplyId && (
              <form>
                <input
                  type="text"
                  value={this.state.answerContent}
                  onChange={this.handleInputChange}
                />
                <button onClick={() => this.handleSubmitAnswer(reply.id)}>
                  댓글 작성
                </button>
              </form>
            )}
          </td>
          <td className='modifyBtn' onClick={() => this.handleEditClick(reply)}>
            수정
          </td>
          <td className='deleteBtn' onClick={() => this.answerDeleteClick(reply.id)}>
            삭제
          </td>
        </tr>
      ))}
    </table>
  </div>
))}
              <div className="answer-wrap">
        <textarea
          className="answer-text"
          onChange={this.handleTextareaChange}
          value={this.state.answerContent}
        ></textarea>
        <button className="answer-btn" onClick={this.handleSubmitAnswer}>답변 달기</button>
        </div></div>

                    </div>)          
        }
      </div>
    )
  }
}
export default BoardDetail;