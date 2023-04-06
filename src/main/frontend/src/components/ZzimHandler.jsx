import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ZzimList from './ZzimList';
import "../css/saveitem.css"

function ZzimItem(props) {
  const [products, setProducts] = useState([]);	
  const [checked, setChecked] = useState([]);

  useEffect(() => {
    async function fetchProducts() {
      const response = await axios.get('/api/products', {
        headers: {
          "Content-Type": "application/json;charset=UTF-8",
          'Authorization': 'Bearer ' + localStorage.getItem('accessToken'),
        },
      });
      setProducts(response.data);
      console.log(response.data);
    }
    fetchProducts();
  }, []);

  const removeItem = (id) => {
    setProducts(products.filter((product) => product.id !== id));
  };

  async function handleRemoveFavorite() {
    try {
      await Promise.all(
        checked.map((id) =>
          axios.delete(`/api/products/${id}`, {
            headers: {
              "Content-Type": "application/json;charset=UTF-8",
              Authorization: "Bearer " + localStorage.getItem("accessToken"),
            },
          })
        )
      );
      checked.forEach(removeItem);
      setChecked([]);
      alert("선택하신 품목이 삭제되었습니다.");
    } catch (error) {
      console.error(error);
      alert("품목 삭제 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
    }
  };

  return (
    <>
      <div className="savelist-top">
        <div className="countItem"> 찜한 품목()</div>
        <div className="delete">
          <button className="deletebtn" onClick={handleRemoveFavorite}>삭제</button>
        </div>
      </div>
      <div className="listline"></div>
      <ul>    
        {products.map(product => (
          <ZzimList key={product.id} id={product.id} link={product.link} title={product.title} image={product.image} lprice={product.lprice} mallName={product.mallName}/>
        ))}
      </ul>
    </>
  );
}

export default ZzimItem;
