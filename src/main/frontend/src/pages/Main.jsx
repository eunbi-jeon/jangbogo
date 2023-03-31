import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../css/main.css';

import item_icon from '../img/item_icon/item_111.png';

function Main() {
  const [items, setItems] = useState([]);
  const [currentItem, setCurrentItem] = useState(null);

  useEffect(() => {
    axios.get('http://localhost:8080/')
      .then(response => {
        setItems(response.data);
        setCurrentItem(response.data[0]);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

// n초 마다 정보 변경
  useEffect(() => {
    const intervalId = setInterval(() => {
      const currentIndex = items.findIndex(item => item === currentItem);
      if (currentIndex === items.length - 1) {
        setCurrentItem(items[0]);
      } else {
        setCurrentItem(items[currentIndex + 1]);
      }
    }, 5000);

    return () => clearInterval(intervalId);
  }, [currentItem, items]);


 // item_code에 따른 메시지 출력 함수
 const getItemCodeMessage = (itemCode) => {
    if (itemCode >= 100 && itemCode < 200) {
      return '식량작물';
    } else if (itemCode >= 200 && itemCode < 300) {
      return '채소류';
    } else if (itemCode >= 300 && itemCode < 400) {
      return '특용작물';
    } else if (itemCode >= 400 && itemCode < 500) {
      return '과일류';
    } else if (itemCode >= 500 && itemCode < 600) {
      return '축산물';
    } else if (itemCode >= 600 && itemCode < 700) {
      return '수산물';
    } else {
      return '';
    }
  };


  return (
    <div className='PriceInfoContainer'>

    {currentItem && (
      <div>
        <div>
        <img src={item_icon}></img>
        {getItemCodeMessage(currentItem.item_code) && (
            <p>{getItemCodeMessage(currentItem.item_code)}</p>
          )}
        </div>
          <div className='itemName'> {currentItem.item_name}</div>
         
          <p>품종명: {currentItem.kind_name}</p>

          <p>단위: {currentItem.unit}</p>
          <p>day1: {currentItem.day1.substring(4, 9)}</p>

          <p>dpr1: {currentItem.dpr1}</p>

          <p>day3: {currentItem.day3.substring(6, 11)}</p>
          <p>dpr3: {currentItem.dpr3}</p>
        </div>
      )}
    </div>
  );
}

export default Main;
