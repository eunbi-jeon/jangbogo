import React, {useState, useEffect} from 'react';
import '../css/search.css'
import axios from 'axios';
import Save from '../components/Save';


function numberWithCommas(pNumber) {
    return pNumber.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function Search(props) {
  const query = props.query;
  const authenticated=props.authenticated;
  const [items, setItems] = useState([]);
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
             	alert("검색 결과가 없습니다.");
         	}
  
          } catch (error) {
            console.error(error);
            setItems([]);
            alert("검색 결과가 없습니다.");
          }
        } else {
          setItems([]);
         
        }
      }
      fetchData()
  }, [query, sortBy]);
  
  
  const onClickItem=(item, e)=>{
    if (!e || !e.target || e.target.nodeName !== "BUTTON") {
      window.open(item.link, "_blank");
    }else{
		e.preventDefault();
	}
  } 
  

	const handleSortByChange = (e) => {
	  const selectedValue = e.target.value;
	  
	  if (selectedValue === "관련도순") {
	    setSortBy("sim");
	  
	  } else if (selectedValue === "최신순") {
	    setSortBy("date");
	  
	  } else if (selectedValue === "낮은가격순") {
	    setSortBy("asc");
	  
	  }
	
    const onClickItem=(item, e)=>{
      if (!e || !e.target || e.target.nodeName !== "BUTTON") {
        window.open(item.link, "_blank");
      }else{
      e.preventDefault();
    }
    } 
    
    

};
  


    return (
      <>
      <div className="search-container">
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
              <div className='search-item' onClick={(e)=>onClickItem(item, e)} >
                <div className='search-item-left'>
                  <img src={item.image} alt={item.title} />
                </div>
                <div className='search-item-info'>
                  <div dangerouslySetInnerHTML={{ __html: `${boldText(item.title)}`}}></div>
                  <div className='price'>
                    {numberWithCommas(item.lprice)}
                    <span className='unit'>원</span>
                    <Save authenticated={authenticated} onClick={(e) => {e.stopPropagation()}} item={item} />
                   
                </div>
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