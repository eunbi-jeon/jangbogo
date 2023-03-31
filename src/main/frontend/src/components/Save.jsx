
import React, {useState, useEffect} from 'react';
import { useLocation, } from 'react-router-dom';
import '../css/search.css'
import axios from 'axios';


function numberWithCommas(pNumber) {
    return pNumber.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
function Save() {
  const [items, setItems] = useState([]);
  const [errorMessage, setErrorMessage] = useState('');
  const [query, setQuery] = useState('');
  const location = useLocation();

 useEffect(()=>{
	  const query = new URLSearchParams(location.search).get("query");
	  setQuery(query || '');
 	}, [location.search]);
 	

  const searchItems = async (query) => {
    try {
      const encodedQuery = encodeURIComponent(query);
      
      let url = `/api/search?query=${encodedQuery}`;
      
      if (query) {
        url += '&category1=식품'; // query 값이 있을 때만 category1 파라미터를 추가
      }
      
      const response = await axios.get(url);
      const data = await response.data;
      const filteredData = data.filter(item => item.category1 === '식품');
      
      setItems(filteredData);
      
      if (filteredData.length === 0) {
        setErrorMessage("검색 결과가 없습니다.");
      } else {
        setErrorMessage("");
      }
    } catch (error) {
      console.error(error);
      setItems([]);
      setErrorMessage("검색 결과가 없습니다.");
    }
  };

  useEffect(() => {
    if (query) {
      searchItems(query);
    }
  }, [query]);


  const handleSave = async(item) => {
	
    try {
      const response = await axios.post('/api/save', item, {
        headers: {
          'Content-Type': 'application/json',
        },
      });
	   if (response.status === 200) {
	      alert('저장되었습니다!');
	      return response.data.id;
	    } else {
	      setErrorMessage('상품 저장 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
	    }
    }catch(error){
		console.error(error);
		setErrorMessage('상품이 저장되지 않았습니다. 잠시 후 다시 시도해주세요.');
	}
};

	
  return (
    <div>
      {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
      <ul>
        {items.map(item => (
          <li key={item.link}>
            <div className='search-itemDto'>
              <div className='search-itemDto-left'>
                <img src={item.image} alt={item.title} />
              </div>
              <div className='search-itemDto-center'>
                <div>{item.title}</div>
                <div className='price'>
                  {numberWithCommas(item.lprice)}
                  <span className='unit'>원</span>
                </div>
              </div>
              <div className='search-itemDto-right'>
                <button onClick={() => handleSave(item)}>저장 </button>
              </div>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );

};
export default Save;
