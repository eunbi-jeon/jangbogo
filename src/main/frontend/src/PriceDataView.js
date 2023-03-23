import React, { useEffect, useState } from 'react';
import axios from 'axios';

function App() {
  const [prices, setPrices] = useState([]);

  useEffect(() => {
    axios.get('http://www.kamis.or.kr/service/price/xml.do?action=dailyPriceByCategoryList&p_cert_key=1e5a1d54-9b74-4b76-858d-bb13e17176b2&p_cert_id=22.euneu@gmail.com&p_product_cls_code=01&p_returntype=json&p_country_code=1101&p_regday=2023-03-22&p_convert_kg_yn=Y')
      .then(response => {
        const { data: { data } } = response;
        setPrices(data);
      })
      .catch(error => console.log(error))
  }, []);

  return (
    <div>
      {prices.map(price => (
        <div key={price.itemcode}>
          품목명: {price.item_name}, 품목코드: {price.itemcode}, 품종명: {price.kind_name}, 품종코드: {price.kindcode}, 단위: {price.unit}, 조회일자: {price.day1}, 조회일자 가격: {price.dpr1}
        </div>
      ))}
    </div>
  );
}

export default App;