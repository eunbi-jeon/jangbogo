import React, { useState, useEffect } from 'react';
import axios from 'axios';

function PriceData() {
  const [priceData, setPriceData] = useState([]);

  useEffect(() => {
    axios.get('/price')
      .then(response => {
        const { data } = response;
        const filteredData = data.data.item.map((item) => {
          return {
            itemcode: item.item_code,
            item_name: item.item_name,
            unit: item.unit,
            day1: item.day1,
            dpr1: item.dpr1,
            day2: item.day2,
            dpr2: item.dpr2,
            day3: item.day3,
            dpr3: item.dpr3
          };
        });
        setPriceData(filteredData);
      })
      .catch(error => {
        console.log(error);
      })
  }, []);

  return (
    <div>
      <h1>Price Data</h1>
      <table>
        <thead>
          <tr>
            <th>품목명</th>
            <th>품목코드</th>
            <th>단위</th>
            <th>조회일자</th>
            <th>조회일자 가격</th>
            <th>1일전 일자</th>
            <th>1일전 가격</th>
            <th>1주일전 일자</th>
            <th>1주일전 가격</th>
          </tr>
        </thead>
        <tbody>
          {priceData.map((data, index) => (
            <tr key={index}>
              <td>{data.item_name}</td>
              <td>{data.itemcode}</td>
              <td>{data.unit}</td>
              <td>{data.day1}</td>
              <td>{data.dpr1}</td>
              <td>{data.day2}</td>
              <td>{data.dpr2}</td>
              <td>{data.day3}</td>
              <td>{data.dpr3}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default PriceData;