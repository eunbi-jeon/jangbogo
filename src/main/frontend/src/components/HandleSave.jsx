import axios from "axios";
import { useState } from "react";

async function HandleSave(item) {
  const [Modal, setModal]=useState
  try {
    const response = await axios.post('/api/products', item, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    if (response.status === 200) {
      Modal('저장되었습니다!');
      return response.data.id;
    } else {
      setModal('상품 저장 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
    }
  } catch (error) {
    console.error(error);
    setModal('상품이 저장되지 않았습니다. 잠시 후 다시 시도해주세요.');
  }
}

export default HandleSave;
