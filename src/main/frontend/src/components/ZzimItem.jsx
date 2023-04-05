import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ZzimList from './ZzimList';
import "../css/saveitem.css"

function ZzimItem(props) {
  const [products, setProducts] = useState([]);	
 

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
  }, []);
  return (
    <>
      
      <div id='product-container' >
        {products.map((product) => (
          <ZzimList key={product.link} {...product}/>
        ))}
      </div>

    </>
  );
}

export default ZzimItem;
