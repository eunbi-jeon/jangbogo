import React, {useState } from 'react'
import Modal from 'react-modal'
import './messagelist.css'

Modal.setAppElement('#root')

function App(){
  const [modalIsOpen,setModalIsOpen] = useState(false)
  return (
    <div className='App'>
      <button onClick={() => setModalIsOpen(true)}>Open modal</button>
      <Modal isOpen={modalIsOpen} 
      shouldCloseOnOverlayClick={false} 
      onRequestClose={() => setModalIsOpen(false)}
    
      // 모달창 외 클릭 시 클로즈 onRequestClose={() => setModalIsOpen(false)}
        style={
          {
            overlay: {
              backgroundColor: 'grey'
            },
            content: {
              color: 'orange'
            }
            
            }
          }
    >     
          {/* 반복되는 쪽지 출력 */}
          <h2>받은 쪽지</h2>
            <div className='message list'>
            <p>쪽지 내용입니다아아아아아 길게 늘어지지이이이이</p>
            <p>쪽지 보낸 사람</p>
            <p>2023.04.04 02:54</p>
            </div>
            <div>
              <p onClick={() => setModalIsOpen(false)}>X</p>
            </div>
      </Modal>
    </div>
  )
}

export default App