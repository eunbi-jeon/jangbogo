import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ZzimList from './ZzimList';
import "../css/saveitem.css"

function ZzimItem(props) {
  const [products, setProducts] = useState([]);
  const [checked, setChecked] = useState([]);
  const [checkedAll, setCheckedAll] = useState(false);

  
  useEffect(() => {
    async function fetchProducts() {
      const response = await axios.get('/api/products', {
        headers: {
          "Content-Type": "application/json;charset=UTF-8",
          'Authorization': 'Bearer ' + localStorage.getItem('accessToken'),
        },
      });
      setProducts(response.data);
    }

    fetchProducts();
  }, [checked]);


  async function handleRemoveZzim() {
    if (checked.length === 0) {
      alert("삭제할 항목을 선택해주세요.");
      return;
    }
    
    try {
      await Promise.all(
        checked.map((prodId) =>
          axios.delete(`/api/products/${prodId}`, {
            headers: {
              "Content-Type": "application/json;charset=UTF-8",
              Authorization: "Bearer " + localStorage.getItem("accessToken"),
            },
          })
        )
      );
      setChecked([]);
      setCheckedAll(false);
      setProducts(products.filter((product) => !checked.includes(product.id)));
      alert("선택하신 품목이 삭제되었습니다.");
    } catch (error) {
      console.error(error);
      alert("품목 삭제 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
    }
  };
  
  
  
const handleCheckAll = () => {
    if (checkedAll) {
      setChecked([]);
      setCheckedAll(false);
    } else {
      const ids = products.map((product) => product.id);
      setChecked(ids);
      setCheckedAll(true);
    }
 };

  const handleCheck = (prodId) => {
    if (checked.includes(prodId)) {
      setChecked(checked.filter((id) => id !== prodId));

    } else {
      setChecked([...checked, prodId]);
      console.log(prodId)
    }
  };

  return (
    <>
      <div className="savelist-top">
        <div className="countItem"> 찜한 품목({products.length})</div>
        <div className="delete">
          <button className="selectAllbtn" onClick={handleCheckAll}> 
          {checkedAll ? "전체 해제" : "전체 선택"}</button>
          <button className="deletebtn" onClick={handleRemoveZzim}>
            삭제
          </button>
        </div>
      </div>
      <div className="listline"></div>
      <ul>    
        {products.map(product => (
          <ZzimList
            key={product.id}
            id={product.id}
            link={product.link}
            title={product.title}
            image={product.image}
            lprice={product.lprice}
            mallName={product.mallName}
            checked={checked.includes(product.id)}
            onCheck={handleCheck}
          />
        ))}
      </ul>
    </>
  );
}

export default ZzimItem;