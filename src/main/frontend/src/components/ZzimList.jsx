import React, { useState } from "react";
import axios from "axios";
import "../css/savelist.css";

function SaveList({link, id, productId, title, image, lprice, mallName, removeItem}){

  const [zzim, setZzim] = useState([]);

	const numberWithCommas = (price)=> {
	    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	
	};
  const boldText = (text) => {
    return text.replaceAll(title, `<b>${title}</b>`);
  };

  const onClickGoToSite=()=>{
    window.open(link,'_blank');
  }
 const handleRemoveFavorite = async () => {
    try {
      await axios.delete(`/api/products/${id}`),
      {
        headers: {
          "Content-Type": "application/json;charset=UTF-8",
          'Authorization': 'Bearer ' + localStorage.getItem('accessToken'),
        },
      };
      
      removeItem(id);
    
    } catch (error) {
      console.error(error);
      aleart('상품 삭제 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
    }
  };

  // 찜 리스트 추가
const addToZzim = useCallback((product) => {
  setZzim((zzim) => {
    // 중복체크
    const finded = zzim.find((product) => product.id === item.id);
    const { link, id, productId, title, image, lprice, mallName} = item;


    if (finded === undefined) {
      // 새로 데이터를 return
      return [...zzim, { link, id, productId, title, image, lprice, mallName, count: 1 }];     }
  
    else {
      return zzim.map((product) => {
        if (product.id === id) {
          return {
            link, id, productId, title, image, lprice, mallName,
            count: product.count + 1,
          };
        } else {
          return product;
        }
      });
    }
  });
}, []);

  // product 제거
  // 중복 오류나서 함수이름 변경 removeItem -> removeZzimItem
  const removeZzimItem = useCallback((id) => {
    setZzim((zzim) => {
      return zzim.filter((product) => product.id !== id);
    });
  }, []);

  // product 전체 제거
  const removeAll = useCallback(() => {
    setZzim([]);
  }, []);


  return (
    <>
    
    <div className="savelist-top">
    <div className="countItem"> 찜한 품목({product.count})</div>
    <div className="delete">
      <button className="deletebtn" onClick={handleRemoveFavorite}>삭제</button></div>
    </div>
    <div className="listline"></div>
    <div className="save-container">
      
      <ul>    
        {zzim.map(product => (
          <li key={product.link}>
          const { count } = product;
            <div className='save-item' onClick={()=>onClickGoToSite()} >
              <div className='search-item-left'>
              <input type="checkbox" checked={isChecked} onChange={(e) => setIsChecked(e.target.checked)} />
                <img src={product.image} alt={product.title} />
              </div>
              <div className='save-item-info'>
                <div className="mallName">{product.mallName}</div>
                <div className='title' dangerouslySetInnerHTML={{ __html: `${boldText(product.title)}`}}></div>
                <div className='price'>
                  {numberWithCommas(product.lprice)}
                  <span className='unit'>원</span>
                                 </div>
            </div>
          </div>
        </li>
      ))}
    </ul>
  </div>
  </>
);

};
export default SaveList;
