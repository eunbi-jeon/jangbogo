import React, { useState } from "react";
import axios from "axios";
import "../css/savelist.css";



function SaveList({link, id, productId, title, image, lprice, mallName, onRemoveFavorite}){
	const [errorMessage, setErrorMessage] = useState('');
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
      await axios.delete(`/api/products/${id}`);
      onRemoveFavorite(id);
    
    } catch (error) {
      console.error(error);
      setErrorMessage('상품 삭제 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
    }
  };
  return (
    <>
    
    <div className="savelist-top">
    <div className="countItem"> 찜한 품목()</div>
    <div className="delete">
      <button className="deletebtn" onClick={handleRemoveFavorite}>삭제</button></div>
    </div>
    <div className="listline"></div>
    <div className="search-container">
      
      <ul>    
        {items.slice(0, 30).map(item => (
          <li key={item.link}>

            <div className='search-item' onClick={()=>onClickGoToSite()} >
              <div className='search-item-left'>
              <input type="checkbox" checked={isChecked} onChange={(e) => setIsChecked(e.target.checked)} />
                <img src={item.image} alt={item.title} />
              </div>
              <div className='search-item-info'>
                <div className="mallName">{item.mallName}</div>
                <div className='title' dangerouslySetInnerHTML={{ __html: `${boldText(item.title)}`}}></div>
                <div className='price'>
                  {numberWithCommas(item.lprice)}
                  <span className='unit'>원</span>
                                 </div>
            </div>
            <div className='search-itemDto-right'>
                
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
