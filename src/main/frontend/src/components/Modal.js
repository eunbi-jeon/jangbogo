//Modal.js

import React from "react";
import styled from "styled-components";

const Modal = ({ onClose} :any) => {

  return (
      <Background>
        <Content>
        <h3>관심 품목으로 등록되었습니다.</h3>
        <button type='button' onClick={onClose}>
          닫기
        </button>
         </ Content>
      </Background>
  );
};

export default Modal;

const Background = styled.div`
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  position: fixed;
  left: 0;
  top: 0;
  text-align: center;
`;

const Content = styled.div`
  height: 100%;
  width: 950px;
  margin-top: 70px;
  position: relative;
  overflow: scroll;
  background: #141414;
`;