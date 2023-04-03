import React, { useState } from "react";
import axios from "axios";
import "../css/savelist.css";
import closeImg from "../img/close.png"


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
    <div className="save-container">
      <div className='save-item'>
        <div style={{display:"none"}}>{productId}</div>
        <div className='save-item-left'>
          <img src={image} alt={title} title={title} />
        </div>
        <div className="save-item-center"  onClick={()=>onClickGoToSite()}>
              <div><span style={{fontWeight:"bolder", color:"#ED712E",}}>[{mallName}]</span>  <span dangerouslySetInnerHTML={{ __html: `${boldText(title)}`}}></span></div>
              <div className='price'>
                    {numberWithCommas(lprice)}
                    <span className='unit'>원</span>
              </div>
          </div>
          <div className='save-item-right'>
                <img src={closeImg} alt='삭제' className='delete-img' onClick={handleRemoveFavorite}/>
          </div>
                 
          {errorMessage && <div>{errorMessage}</div>}
      </div>
    </div>
  );
};



export default SaveList;
