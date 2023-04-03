import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../css/main.css';
import icon100 from '../img/priceInfo_icon/100.png';
import icon200 from '../img/priceInfo_icon/200.png';
import icon300 from '../img/priceInfo_icon/300.png';
import icon400 from '../img/priceInfo_icon/400.png';
import icon500 from '../img/priceInfo_icon/500.png';
import icon600 from '../img/priceInfo_icon/600.png';




function Main() {
  const [items, setItems] = useState([]);
  const [currentItem, setCurrentItem] = useState(null);
  const images = require.context('../img/priceInfo_icon', true);

  useEffect(() => {
    axios.post('http://localhost:8080/')
    axios.get('http://localhost:8080/')
      .then(response => {
        setItems(response.data);
        setCurrentItem(response.data[0]);
        console.log(response);
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
      return {
        message: '식량작물',
        image: icon100
      };
    } else if (itemCode >= 200 && itemCode < 300) {
      return {
        message: '채소류',
        image: icon200
      };
    } else if (itemCode >= 300 && itemCode < 400) {
      return {
        message: '특용작물',
        image: icon300
      };
    } else if (itemCode >= 400 && itemCode < 500) {
      return {
        message: '과일류',
        image: icon400
      };
    } else if (itemCode >= 500 && itemCode < 600) {
      return {
        message: '축산물',
        image: icon500
      };
    } else if (itemCode >= 600 && itemCode < 700) {
      return {
        message: '수산물',
        image: icon600
      };
    } else {
      return {
        message: '축산물',
        image: icon500
      };
    }
  };


  return (
    <div className='Container'>

      {currentItem && (
        <div className='PriceInfoBox'>
       <div>
          <div className='icon_box'>
              {getItemCodeMessage(currentItem.itemCode) && (
                <>
                  <div className='circle'>
                    <img className='icon_img' src={getItemCodeMessage(currentItem.itemCode).image} alt={getItemCodeMessage(currentItem.itemCode).message} />
                  </div>
                </>
              )}
                  <div className='itemCode'>
                  {getItemCodeMessage(currentItem.itemCode) && (
                    <>
                      {getItemCodeMessage(currentItem.itemCode).message}
                    </>)
                  }</div>
          </div>
          </div>
          <div className='info_box'>

            <div><p className='itemName'>{currentItem.itemName} ({currentItem.rank})</p></div>
            <p>단위({currentItem.unit})</p>
            <p>오늘 가격 : {currentItem.dpr1}원</p>
            {currentItem.day2 && <p> 어제 날짜: {currentItem.day2.substring(5, 10)}</p>}
            <p>어제 가격 :{currentItem.dpr2} 원 </p>
            {currentItem.day3 && <p>1주일 전 날짜: {currentItem.day3.substring(6, 11)}</p>}
            <p>1주일 전 가격: {currentItem.dpr3}원</p>
            <div className='infoDate'>{currentItem.day1 && <p>{currentItem.day1.substring(4, 6)}월 {currentItem.day1.substring(7, 9)}일 기준 가격정보</p>}</div>
          </div>
        </div>

      )}

    </div>

  );

}

export default Main;