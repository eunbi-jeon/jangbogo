import React, { useState } from "react";
import axios from "axios";
import "../css/savelist.css";

function SaveList({link, image, lprice, mallName, title, id}) {
  const [checked, setChecked] = useState([]);

  const numberWithCommas = (price) => {
    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  };
  
  const boldText = (text) => {
    return text.replaceAll(title, `<b>${title}</b>`);
  };
  
  const onClickGoToSite = () => {
    window.open(link,'_blank');
  };

  function handleCheck(id) {
    if (checked.includes(id)) {
      setChecked(checked.filter((c) => c !== id));
    } else {
      setChecked([...checked, id]);
    }
    
    if (!checked.includes(id)) {
      onClickGoToSite();
    }
  }

  return (
    <>
    <li key={link}>
      <div className="save-container">
        <div className='save-item' onClick={onClickGoToSite}>
          <div className='search-item-left'>
            <div>
              <input type="checkbox" checked={checked.includes(id)} onChange={() => handleCheck(id)} />
                </div>
                <div><img src={image} alt={title} /></div>
                </div>
                <div className='save-item-info'>
                  <div className="fav-mallName">{mallName}</div>
                  <div className='fav-title' dangerouslySetInnerHTML={{ __html: `${boldText(title)}`}}></div>
                  <div className='fav-price'>
                    {numberWithCommas(lprice)}
                    <span className='unit'>원</span>
                                  </div> 
                </div>
              </div> 
            </div>
         </li>
 
  </>
);

};
export default SaveList;
