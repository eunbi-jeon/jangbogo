import React, { Component } from 'react';
import { Link } from 'react-router-dom'
import axios from 'axios';
import '../css/boardList.css';

class BoardList extends Component {

    constructor(props) {
        super(props)

        this.state = {
            questions: [],
            currentPage: 1,
            totalPages: null,
            error: null,
        }
    }

    componentDidMount() {
     {
            const token = localStorage.getItem('accessToken');
            axios.get('http://localhost:8080/board/list', {
                headers: {
                    Authorization: 'Bearer ' + token
                }
            })
                .then((res) => {
                    const questions = res.data;
                    this.setState({questions})
                })
                .catch(error => {
                    console.error(error);
                });
        }
    }
    
    handlePostClick = (questionId) => {

        sessionStorage.setItem('scrollPosition', window.scrollY)
        this.props.history.push(`/question/${questionId}`);
    }


    render() {
        console.log(this.state.questions);
        return (

            <div>
                <div className="boardWrap">
                    <h1>게시판</h1>
                    <span className='hrLine1'><hr></hr></span>
                    <table className='boardList'>
  <thead>
    <tr>
      <th className='listCount2'>번호</th>
      <th className='listSubject2'>글제목</th>
      <th className='bestCount2'>추천 수</th>
      <th className='readCount2'>조회 수</th>
    </tr>
  </thead>
  <tbody>
    {this.state.questions.map((question) => (
      <React.Fragment key={question.id}>
        <tr onClick={() => this.handlePostClick(question.id, question.readCount)}>
          <td className='listCount'>{question.id}</td>
          <td className='listSubject'>
            <Link to={`/question/detail/${question.id}`}>{question.subject}</Link>
            <span className='replyCount'>{question.answer.length}</span>
          </td>
          <td className='bestCount'>{question.voter.length}</td>
          <td className='readCount'>{question.readCount}</td>
        </tr>
        <tr className='hrLine3' />
      </React.Fragment>
    ))}
  </tbody>
</table>
                    <span className='hrLine1'><hr ></hr></span>
                    <button className='boardCreate'><Link to='/question/create'>글 쓰기</Link></button>
                </div>
            </div>
        );
    }
}

export default BoardList;