import { useState, useEffect, useContext } from "react";
import { useHistory } from "react-router-dom";
import axios from "axios";
import '../css/boardcreate.css'
const BoardCreate = ({ match }) => {
  const [subject, setSubject] = useState("");
  const [content, setContent] = useState("");
  const [board_id, setBoardId] = useState("");
  const [region, setRegion] = useState("");

  const history = useHistory();

  useEffect(() => {
    const { board_id } = match.params;
    const { region } = match.params;

    setBoardId(board_id);
    setRegion(region);
    console.log("board_id:" + board_id);
    console.log("region:" + region);
  }, [match.params]);

  const handleSubmit = (e) => {
    e.preventDefault();
    const token = localStorage.getItem("accessToken");
    axios
      .post(
        "http://localhost:8080/board/create/" + board_id,
        { subject, content ,region},
        {
          headers: {
            Authorization: "Bearer " + token,
          },
        }
      )
      .then((response) => {
        alert("글 등록 완료");
        history.push(`/board/list/${board_id}/${region ? region : ""}`);
      })
      .catch((error) => {
        alert("오류");
        console.error(error);
      });
  };
      const handleTitleChange = (e) => {
        setSubject(e.target.value);
      }
    
      const handleContentChange = (e) => {
        setContent(e.target.value);
      }
console.log("regionteeeeeee"+region);

return (
    
<div className='createWrap'>
<form className='board-create-form'>
     <input type="hidden" name="region" value={region} /> 
    <input type="text" value={subject} onChange={handleTitleChange} placeholder='제목을 입력해주세요' />
    <textarea value={content} onChange={handleContentChange} />
    <button className='board-btn' onClick={handleSubmit}>글 쓰기</button>
    </form>
</div>
);
}

export default BoardCreate;