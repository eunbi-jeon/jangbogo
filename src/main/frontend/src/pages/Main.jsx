import React, { useState, useEffect } from 'react';
import axios from 'axios';

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

  return (
    <div>
      {currentItem && (
        <div>
          <p> {currentItem.item_name}</p>
         
          <p>품종명: {currentItem.kind_name}</p>

          <p>단위: {currentItem.unit}</p>
          <p>day1: {currentItem.day1}</p>
          <p>dpr1: {currentItem.dpr1}</p>

          <p>day3: {currentItem.day3}</p>
          <p>dpr3: {currentItem.dpr3}</p>
        </div>
      )}
    </div>
  );
}

export default Main;