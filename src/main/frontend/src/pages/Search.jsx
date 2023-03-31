
import React, {useState, useEffect} from 'react';
import '../css/search.css'
import axios from 'axios';
import { useHistory } from 'react-router-dom';


function numberWithCommas(pNumber) {
    return pNumber.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function Search(props) {
  const history = useHistory();
  const [items, setItems] = useState([]);
  const [errorMessage, setErrorMessage] = useState('');
  const query = props.query;
  const [sortBy, setSortBy] = useState('관련도순');

  const boldText = (text) => {
    return text.replaceAll(query, `<b>${query}</b>`);
  };

  useEffect(() => {
    async function fetchData(){
      if (query) {
          try {
            const encodedQuery = encodeURIComponent(query);
       
            let url = `/api/search?query=${encodedQuery}`;
            
            if (query) {
              url += '&category1=식품'; // query 값이 있을 때만 category1 파라미터를 추가
            }
            
            
            if (sortBy === '낮은 가격순') {
              url += '&sort=asc';
            } else if (sortBy === '관련도순') {
                url += '&sort=sim';
            } else if (sortBy === '최신순'){
              url += '&sort=date';
            }
            const response = await axios.get(url);
            const data = await response.data;
            const filteredData = data.filter(item => item.category1 === '식품');
            const sortedData = sortBy === 'asc' ? filteredData.sort((a, b) => a.lprice - b.lprice) : sortBy === 'sim' ? filteredData.sort((a, b) => b.mallScore - a.mallScore) : filteredData.sort((a, b) => new Date(b.pubDate) - new Date(a.pubDate));
            setItems(sortedData);
            console.log(sortedData);
            
            if (sortedData.length === 0) {
              setErrorMessage("검색 결과가 없습니다.");
            } else {
              setErrorMessage("");
            }
  
          } catch (error) {
            console.error(error);
            setItems([]);
            setErrorMessage("검색 결과가 없습니다.");
          }
        } else {
          setItems([]);
         
        }
      }
      fetchData()
  }, [query, sortBy]);
  
  
  const onClickItem=(item)=>{
    history.push(`/search?query=${query}/${item.productId}`, {state:item});
    if (window.event && window.event.target.nodeName === "BUTTON") {
      handleSave(item);
      return ;
    }
    window.open(item.link, '_blank');
  } 
  
  const handleSortByChange = (event) => {
    const selectedValue = event.target.value;
    switch (selectedValue) {
      case "관련도순":
        setSortBy("sim");
        break;
      case "최신순":
        setSortBy("date");
        break;
      case "낮은가격순":
        setSortBy("asc");
        break;
      default: break;
    }
    setSortBy(selectedValue);
};
  

  const handleSave = async(item) => {
    const memberId = props.memberId;	
    try {
      const response = await axios.post('/api/products', item, {
        headers: {
          'Content-Type': 'application/json',
          'member_id': memberId,
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
      <>
      <div className="search-container">
        {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
        <div className='sort-box'>
          <label htmlFor="sortOrder"></label>
          <select id="sortBy" name="sortBy" value={sortBy} onChange={handleSortByChange}>
            <option value="sim">관련도순</option>
            <option value="asc">낮은 가격순</option>
            <option value="date">최신순</option>
          </select>
        </div>
        <ul>    
          {items.slice(0, 30).map(item => (
            <li key={item.link}>
              <div className='search-item' onClick={()=>onClickItem(item)} >
                <div className='search-item-left'>
                  <img src={item.image} alt={item.title} />
                </div>
                <div className='search-item-info'>
                  <div dangerouslySetInnerHTML={{ __html: `${boldText(item.title)}`}}></div>
                  <div className='price'>
                    {numberWithCommas(item.lprice)}
                    <span className='unit'>원</span>
                    <button onClick={() => handleSave(item)}> 보관하기 </button>
                </div>
              </div>
              <div className='search-itemDto-right'>
              </div>
            </div>
          </li>
        ))}
      </ul>
    </div>
    </>
  );

};
export default Search;
