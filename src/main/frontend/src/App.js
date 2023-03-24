import React, { useEffect, useState } from 'react';
import fetchData from './fetchData';

function App() {
  const [data, setData] = useState([]);

  useEffect(() => {
    async function getData() {
      const result = await fetchData();
      setData(result);
    }
    getData();
  }, []);

  return (
    <div>
      <h1>API 데이터</h1>
      {data.map((item, index) => (
        <div key={index}>
          <h2>{item.item_name}</h2>
          <p>Item Code: {item.itemcode}</p>
          <p>Kind Name: {item.kind_name}</p>
          <p>Kind Code: {item.kindcode}</p>
          <p>Unit: {item.unit}</p>
          <p>Day: {item.day1}</p>
          <p>Price: {item.dpr1}</p>
        </div>
      ))}
    </div>
  );
}

export default App;
