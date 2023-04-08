import React from "react";
import axios from "axios";


function Save({item, authenticated}) {

  
        const saveItem = async () => {
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
 
  const handleSaveButtonClick = async () => {

    if(authenticated===true){
        const savedItemId = await saveItem();
        if (savedItemId) {
          const savedItems = JSON.parse(localStorage.getItem('savedItems')) || [];
          savedItems.push(savedItemId);
          localStorage.setItem('savedItems', JSON.stringify(savedItems));
        }
    }else{
        const confirmed = window.confirm("로그인이 필요한 서비스입니다. 로그인 페이지로 이동하시겠습니까?");
        if (confirmed) {
        props.history.push('/login');
        }
      }
  };

  return (
    <>
      <button 
      style={{padding:10, 
              borderRadius:3, 
              backgroundColor:'tomato',
              color:'white',
              fontWeight:'bold',
              position: 'absolute',
              right: 10}} onClick={handleSaveButtonClick}>보관하기</button>
    </>
  );
}

export default Save;