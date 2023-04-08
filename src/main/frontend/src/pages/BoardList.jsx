import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import '../css/boardList.css';

class BoardList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            questions: [],
            currentPage: 1,
            totalPages: null,
            error: null,
            board_id:0,
            region:''
        };
    }

    componentDidMount() {
        const { board_id } = this.props.match.params;
        const { region } = this.props.match.params;
        this.setState({
            board_id: board_id,
            region: region
          });
        console.log("board_id:"+board_id);
        console.log("region:"+region);
        console.log("this.stateboard_id:"+this.state.board_id);
        console.log("this.stateregion:"+this.state.region);
        const token = localStorage.getItem('accessToken');
        axios
        .get(`http://localhost:8080/board/list/${board_id}?region=${region}`, {
                headers: {
                    Authorization: 'Bearer ' + token,
                },
            })
            .then((res) => {
                if (res.data) {
                    this.setState({ questions: res.data.content || [] });
                }
            })
            .catch((error) => {
                console.error(error);
            });
    }

    handlePostClick = (id) => {
        console.log('게시글 ID:', id);
    };

    render() {
        console.log("testttttttttttt:"+this.state.board_id);
        const{board_id}=this.state;
        console.log("testttttttttttt:"+this.state.board_id);
        const{region}=this.state;
        const { questions } = this.state;
        return (
            <div><h2 >{region}</h2>
                <div className="boardWrap">
                    <span className="hrLine1">
                    </span>
                    
                    <table className="boardList">
                        <thead>
                            <tr>
                                <th className="listCount2">번호</th>
                                <th className="listSubject2">글제목</th>
                                <th className="bestCount2">추천 수</th>
                                <th className="readCount2">조회 수</th>
                            </tr>
                        </thead>
                        <tbody>
                            {questions && questions.map((question, index) => (
                                <>
                                    <tr key={question.id}
                                        onClick={() => this.handlePostClick(question.id)}>
                                        <td className="listCount">{index+1}</td>
                                        <td className="listSubject">
                                        <Link to={`/board/detail/${this.state.board_id}/${question.id}`}>
                                                {question.subject}
                                            </Link>
                                            <span className="replyCount">
                                                {question.answerList ? question.answerList.length: 0}
                                            </span>
                                        </td>
                                        <td className="bestCount">{question.voter}</td>
                                        <td className="readCount">{question.readCount}</td>
                                    </tr>
                                    <tr><td colSpan={4}><div className="hrLine3"></div></td></tr>
                                    </>
                                ))}
                                
                        </tbody>
                    </table>
                    <button className="boardCreate">
                        <Link to={`/board/create/${this.state.board_id}/${this.state.region ? this.state.region : ''}`} style={{color:'white'}}>글 쓰기</Link>
                    </button>
                </div>
            </div>
        );
    }
}

export default BoardList;
