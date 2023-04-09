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
            totalPage: null,
            error: null,
            board_id:0,
            region:props.currentUser.information.region
        };
    }

    componentDidMount() {
        this.fetchQuestions();
    }

    async fetchQuestions() {
        try {
            const { board_id } = this.props.match.params;
            const { region } = this.props.match.params;
            this.setState({
                board_id: board_id,
                region: region
            });
            
            const token = localStorage.getItem('accessToken');
            const res = await axios .get(`http://localhost:8080/board/list/${board_id}?region=${region}`, { //&page=${this.state.currentPage} 붙여!!!!!!!!!!!!!!!!!!
                            headers: {
                                Authorization: 'Bearer ' + token,
                            },
                        })
                        .then((res) => {
                            if (res.data) {
                                this.setState({ questions: res.data.content || [] });
                                this.setState({ totalPage : Math.ceil(res.data.content.length / 10)});
                            }
                        })
                        .catch((error) => {
                            console.error(error);
                        });
    
        } catch (error) {
            console.error(error);
        }
    }

    handlePostClick = (id) => {
        const token = localStorage.getItem('accessToken');
        axios
            .post(`http://localhost:8080/board/increment-read-count/${id}`, null, {
                headers: {
                    Authorization: 'Bearer ' + token,
                },
            })
            .then((res) => {
                const updatedQuestion = res.data;
                const updatedQuestions = this.state.questions.map((question) => {
                    if (question.id === updatedQuestion.id) {
                        return updatedQuestion;
                    }
                    return question;
                });
                this.setState({ questions: updatedQuestions });
            })
            .catch((error) => {
                console.error(error);
            });
    };
    
    
    handlePageClick(page) {
        this.setState({ currentPage: page }, () => {
          this.fetchQuestions();
        });
      }

    render() {
        const regions = [
            { id: 'seoul', value: '서울' },
            { id: 'gyeonggi', value: '경기' },
            { id: 'incheon', value: '인천' },
            { id: 'gangwon', value: '강원' },
            { id: 'chungcheong', value: '충청' },
            { id: 'daejeon', value: '대전' },
            { id: 'daegu', value: '대구' },
            { id: 'gwangju', value: '광주' },
            { id: 'busan', value: '부산' },
            { id: 'ulsan', value: '울산' },
            { id: 'jeonla', value: '전라' },
            { id: 'gyeongsang', value: '경상' },
            { id: 'jeju', value: '제주' }
          ];

        // const{board_id}=this.state;
        const region = regions.find((item) => item.id === this.state.region)?.value;
        const { questions, totalPage, currentPage } = this.state;

        const pageNumbers = [];
        for (let i = 1; i <= totalPage; i++) {
          pageNumbers.push(i);
        }


        return (
                <div className="boardWrap">
                    <h1><span>l</span>{region} 지역 게시판 입니다<span>l</span></h1>
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
                                             {/* <td className="listCount">{(currentPage - 1) * 10 + index + 1}</td> */}
                                        <td className="listCount">{index+1}</td>
                                        <td className="listSubject">
                                        <Link to={`/board/detail/${this.state.board_id}/${question.id}`}>
                                                {question.subject}
                                            </Link>
                                            <span className="replyCount">
                                                {question.answerList ? question.answerList.length : 0}
                                            </span>
                                        </td>
                                        <td className="bestCount">{question.voter.length}</td>
                                        <td className="readCount">{question.readCount}</td>
                                    </tr>
                                    <tr><td colSpan={4}><div className="hrLine3"></div></td></tr>
                                </>
                            ))}

                        </tbody>
                    </table>
                    {/* <div> 페이징 주석
                        {pageNumbers.map((page) => (
                            <span key={page} onClick={() => this.handlePageClick(page)}>
                                {page}
                            </span>
                        ))}
                    </div> */}
                    <button className="boardCreate">
                        <Link to={`/board/create/${this.state.board_id}/${this.state.region ? this.state.region : ''}`} style={{color:'white'}}>글 쓰기</Link>
                    </button>
                </div>
        );
    }
}

export default BoardList;