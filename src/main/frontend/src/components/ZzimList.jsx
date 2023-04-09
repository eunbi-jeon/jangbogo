import React, { useState } from "react";
import "../css/savelist.css";

function ZzimList({ link, image, lprice, mallName, title, id, checked, onCheck }) {

  const [isChecked, setIsChecked] = useState(!checked);


  const numberWithCommas = (price) => {
    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  };

  const boldText = (text) => {
    return text.replaceAll(title, `<b>${title}</b>`);
  };

  const onClickGoToSite = (e) => {
    if (!isChecked && e.target.tagName !== "INPUT") {
      window.open(link, '_blank');
    }
  };


  
  return (
    <div key={link}>
      <div className="save-container">
        <div className="save-item" onClick={onClickGoToSite}>
          <div className="search-item-left">
            <div className="checkbox">
            <input
              type="checkbox"
              checked={checked}
              onChange={() => onCheck(id)}
            />
            </div>
            <div className="img">
              <img src={image} alt={title} />
            </div>
          </div>
          <div className="save-item-info">
            <div className="fav-mallName">{mallName}</div>
            <div
              className="fav-title"
              dangerouslySetInnerHTML={{ __html: `${boldText(title)}` }}
            ></div>
            <div className="fav-price">
              {numberWithCommas(lprice)}
              <span className="unit">원</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ZzimList;