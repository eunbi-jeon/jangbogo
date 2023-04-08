import { useState, useEffect, useContext } from "react";
import { useHistory } from "react-router-dom";
import axios from "axios";
import '../css/boardcreate.css'

const BoardCreate = ({ match }) => {
  const [subject, setSubject] = useState("");
  const [content, setContent] = useState("");
  const [board_id, setBoardId] = useState("");
  const [region, setRegion] = useState("");
  const [isEditMode, setIsEditMode] = useState(false);

 useEffect(() => {
    const { board_id } = match.params;
    const { region } = match.params;

    setBoardId(board_id);
    setRegion(region);
    console.log("board_id:" + board_id);
    console.log("region:" + region);
  }, [match.params]);

  const handleTitleChange = (e) => {
    setSubject(e.target.value);
    }
    
    const handleContentChange = (e) => {
    setContent(e.target.value);
    }

  useEffect(() => {
    // 수정 페이지일 경우, 기존 글 내용 불러오기
    if (match.params.id) {
      const token = localStorage.getItem("accessToken");
      axios.get(`http://localhost:8080/board/detail/${match.params.id}`, {
        headers: {
          Authorization: "Bearer " + token,
        },
      })
      .then((response) => {
        setSubject(response.data.question.subject);
        setContent(response.data.question.content);
        setIsEditMode(true);
      })
      .catch((error) => {
        console.error(error);
      });
    }
  }, [match.params.id]);


  const handleSubmit = (e) => {
    e.preventDefault(); //새로고침 방지
    const token = localStorage.getItem("accessToken");
  
    if (isEditMode) { // 수정 페이지일 경우, 수정 API 호출
      axios.put(`http://localhost:8080/board/modify/${match.params.id}`, { subject, content }, {
        headers: {
          Authorization: "Bearer " + token,
        }
      })
      .then(response => {
        alert("글 수정 완료");
        //글 수정 완료 후 리스트페이지로 이동 추가
        history.push("/board/list");
      })
      .catch(error => {
        // 에러 처리 로직
        alert("오류");
        console.error(error);
      });
    } else { // 새로운 글 작성일 경우, 글 등록 API 호출
      axios.post('http://localhost:8080/board/create', { subject, content }, {
        headers: {
          Authorization: "Bearer " + token,
        }
      }) 
      .then(response => {
        alert("글 등록 완료");
        //글 작성 완료 후 리스트페이지로 이동 추가
        history.push("/board/list");
      })
      .catch(error => {
        // 에러 처리 로직
        alert("오류");
        console.error(error);
      });
    }
  }

  return (
    <div className='createWrap'>
      <form className='board-create-form'>
        <input type="text" value={subject} onChange={handleTitleChange} placeholder='제목을 입력해주세요' />
        <textarea value={content} onChange={handleContentChange} />
        <button className='board-btn' onClick={handleSubmit}>
          {isEditMode ? '글 수정' : '글 쓰기'}
        </button>
      </form>
    </div>
  );
}

export default BoardCreate;
