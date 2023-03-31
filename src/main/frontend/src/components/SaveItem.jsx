import React, { useState, useEffect } from 'react';
import axios from 'axios';
import SaveList from './SaveList';
import "../css/saveitem.css"

function SaveItem() {
  const [products, setProducts] = useState([]);	
  useEffect(() => {
    async function fetchProducts() {
      const response = await axios.get('/api/products');
      setProducts(response.data);
    }
    fetchProducts();
  }, []);
  
  return (
    <>
      
      <div id='product-container' style={{border:"1.3px solid black",}}>
        {products.map((product) => (
          <SaveList key={product.link} {...product}/>
        ))}
      </div>

    </>
  );
}

export default SaveItem;
