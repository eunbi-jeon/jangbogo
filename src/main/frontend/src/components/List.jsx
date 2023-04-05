import React, { useState, useEffect } from 'react';
import axios from 'axios';
import SaveList from './SaveList';


function List() {
  const [products, setProducts] = useState([]);	

  useEffect(() => {
    async function fetchProducts() {
      const response = await axios.get('/api/products');
      setProducts(response.data);
      console.log(response.data);
    }
    fetchProducts();
  }, []);
  console.log(products);
  return (
    <>

      <div id="product-container">
        {products.map((product) => (
          <ZzimList key={product.link} {...product}/>
        ))}
      </div>
    </>
  );
}

export default List;
