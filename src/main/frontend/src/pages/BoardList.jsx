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
        };
    }

    componentDidMount() {
        const token = localStorage.getItem('accessToken');
        axios
            .get('http://localhost:8080/board/list', {
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
        const { questions } = this.state;
        return (
            <div>
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
                            {questions && questions.map((question) => (
                                <>
                                    <tr key={question.id}
                                        onClick={() => this.handlePostClick(question.id)}>
                                        <td className="listCount">{question.id}</td>
                                        <td className="listSubject">
                                            <Link to={`/board/detail/${question.id}`}>
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
                        <Link to="/board/create" style={{color:'white'}}>글 쓰기</Link>
                    </button>
                </div>
            </div>
        );
    }
}

export default BoardList;
