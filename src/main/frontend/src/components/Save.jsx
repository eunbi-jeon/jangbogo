import React from "react";
import axios from "axios";
import { useHistory } from "react-router-dom";

function Save(props) {
  const history = useHistory();
  const item = props.item;
  const currentUser = props.currentUser;

  const saveItem = async (currentUser) => {
    try {
      const response = await axios.post(
        "/api/products",
        { 
          productId: item.productId, 
          title: item.title,
          link: item.link,
          image: item.image,
          mallName: item.mallName,
          lprice: item.lprice,
          currentUser: currentUser
        },
        {
          headers: {
            "Content-Type": "application/json;charset=UTF-8",
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken'),
          },
        }
      );
      alert("상품이 '찜한 품목'에 저장되었습니다!");
      return response.data.productId;
    } catch (error) {
      console.error(error);
      alert("상품이 저장되지 않았습니다. 잠시 후 다시 시도해주세요.");
      return false;
    }
  };

  const onClickSave = async (e) => {
    e.stopPropagation(); 
    if (!props.authenticated) {
       const confirmed = window.confirm("로그인이 필요한 서비스입니다. 로그인 페이지로 이동하시겠습니까?");
      if (confirmed) {
        history.push('/login');
      }
      
    } else {
        const savedItemId = await saveItem(currentUser);
      if (savedItemId) {
        const savedItems = JSON.parse(localStorage.getItem('savedItems')) || [];
        savedItems.push(savedItemId);
        localStorage.setItem('savedItems', JSON.stringify(savedItems));
      }
    }
  }
  return (
    <>
      <button className='save-btn' onClick={onClickSave}>보관하기</button>
    </>
  );
}

export default Save;
