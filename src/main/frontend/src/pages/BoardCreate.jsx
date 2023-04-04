import React, { useState } from "react";
import axios from "axios";

const BoardCreate = () => {
  const [subject, setSubject] = useState("");
  const [content, setContent] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    axios
      .post("http://localhost:9000/board/create", {
        subject: subject,
        content: content,

      })
      .then((res) => {
        console.log(res.data);
        // 글쓰기 성공 시 페이지 이동
        window.location.href = "/";
      })
      .catch((err) => {
        alert("금지어가 제목이나 내용에 포함되어 있습니다.");
      });
  };

  return (
    <div>
      <div className="createWrap">
      <h2>글쓰기</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>제목</label>
          <input
            type="text"
            value={subject}
            onChange={(e) => setSubject(e.target.value)}
            required
          />
        </div>
        <div>
          <label>내용</label>
          <textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
          ></textarea>
        </div>
        <button type="submit">작성하기</button>
      </form>
      </div>
    </div>
  );
};
export default BoardCreate;