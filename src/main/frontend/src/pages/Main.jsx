import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../css/main.css';

import icon100 from '../img/priceInfo_icon/100.png';
import icon200 from '../img/priceInfo_icon/200.png';
import icon300 from '../img/priceInfo_icon/300.png';
import icon400 from '../img/priceInfo_icon/400.png';
import icon500 from '../img/priceInfo_icon/500.png';
import icon600 from '../img/priceInfo_icon/600.png';
import { LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid ,ResponsiveContainer } from 'recharts';




function Main() {
  const [items, setItems] = useState([]);
  const [currentItem, setCurrentItem] = useState(null);
  

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


  // 그래프 data
  const dpr3Value = currentItem && currentItem.dpr3 ? parseInt(currentItem.dpr3.replace(/,/g, ""), 10) : 0;
  const dpr2Value = currentItem && currentItem.dpr2 ? parseInt(currentItem.dpr2.replace(/,/g, ""), 10) : 0;
  const dpr1Value = currentItem && currentItem.dpr1 ? parseInt(currentItem.dpr1.replace(/,/g, ""), 10) : 0;

  const m = currentItem && currentItem.dpr1 - currentItem && currentItem

  const data = [
    {
      name: '일주일 전',
      가격: dpr3Value,
    },
    {
      name: '하루 전',
      가격: dpr2Value,
    },
    {
      name: '기준일',
      가격: dpr1Value,
    },

  ];


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
      <div className='section1'>
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
              <div ><span className='itemName'>{currentItem.itemName}</span></div>
            </div>
          </div>
          <div className='info_box'>
            <div className='unit' ><span> 등급 ({currentItem.rank}) /</span> <span>단위({currentItem.unit})</span></div>
            <ResponsiveContainer width="90%" height="70%" margin="3%">
              <LineChart
                width={150}
                height={100}
                data={data}
                margin={{
                  top: 30,
                  right: 10,
                  left: 15,
                  bottom: 0,
                }} 
                >
                // 그래프 점선 
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" padding={{ left: 40, right: 40 }}  tick={{ fontSize: 14, fontWeight: 'bold' }} tickMargin={5} axisLine={{ stroke: '#ccc', strokeWidth: 1.5 }} />
                <YAxis tickFormatter={(value) => '₩' + new Intl.NumberFormat('ko-KR').format(value)} domain={['auto', 'auto']} 
                tick={{ fontSize: 14, fontWeight: 'bold' }} tickCount={3} tickMargin={5} axisLine={{ stroke: '#ccc', strokeWidth: 1.5 }}
                padding={{ top: 20, bottom: 20 }}/>
                <Tooltip formatter={(value) => new Intl.NumberFormat('ko-KR').format(value) + '원'} />
                <Line type="monotone" dataKey="가격" stroke="tomato" activeDot={{ r: 8 }} strokeWidth={2}  />
              </LineChart>
            </ResponsiveContainer>
            <div className='infoDate'>{currentItem.day1 && <p>{new Date().getFullYear()}. {currentItem.day1.substring(4, 6)}. {currentItem.day1.substring(7, 9)}.</p>}</div>
          </div>
        </div>


      )}
</div>
      <div className='section2'>
        <div className='question'> 어떤 상품 찾으세요?</div>
        <div className='priceInfoCategory'>
          <ul>
            <li> <div className='iconIMG'><img src={icon100} alt="식량작물 아이콘"></img></div><div>식량작물</div></li>
            <li> <div className='iconIMG'><img src={icon200} alt="채소류 아이콘"></img></div><div>채소류</div></li>
            <li> <div className='iconIMG'><img src={icon300} alt="특용작물 아이콘"></img></div><div>특용작물</div></li>
            <li> <div className='iconIMG'><img src={icon400} alt="과일류 아이콘"></img></div><div>과일류</div></li>
            <li> <div className='iconIMG'><img src={icon500} alt="축산물 아이콘"></img></div><div>축산물</div></li>
            <li> <div className='iconIMG'><img src={icon600} alt="수산물 아이콘"></img></div><div>수산물</div></li>
            
          </ul>
        </div>
      </div>

    </div>
    
  );


}

export default Main;