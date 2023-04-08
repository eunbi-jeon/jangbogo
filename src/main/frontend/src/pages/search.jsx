import React, {useState, useEffect} from 'react';
import '../css/search.css'
import axios from 'axios';
import Save from '../components/Save';


function numberWithCommas(pNumber) {
  return pNumber.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function Search(props) {
  const query = props.query;
  const authenticated = props.authenticated;
  const [items, setItems] = useState([]);
  const [sortBy, setSortBy] = useState('sim');
  const [start, setStart] = useState(1);
  const [display, setDisplay] = useState(99);


  const boldText = (text) => {
    return text.replaceAll(query, `<b>${query}</b>`);
  };

  const fetchData = async () => {
    if (query) {
      try {
        const encodedQuery = encodeURIComponent(query);
        let url = `/api/search?query=${encodedQuery}&start=${start}&display=${display}`;
        if (query) {
          url += '&category1=식품';
        }
        if (sortBy === 'asc') {
          url += '&sort=asc';
        } else if (sortBy === 'sim') {
          url += '&sort=sim';
        } else if (sortBy === 'date') {
          url += '&sort=date';
        }
        const response = await axios.get(url);
        const data = await response.data;
        const filteredData = data.filter(item => item.category1 === '식품');
        const sortedData = sortBy === 'asc' ? 
        filteredData.sort((a, b) => a.lprice - b.lprice) 
        : sortBy === 'sim' ? filteredData.sort((a, b) => b.mallScore - a.mallScore) 
        : filteredData.sort((a, b) => new Date(b.pubDate) - new Date(a.pubDate));        
        
        if (start === 1) {
          setItems(sortedData);
        } else {
          setItems(prevItems => [...prevItems, ...sortedData]);
        }

        if (sortedData.length === 0) {
          alert("검색 결과가 없습니다.");
        }
      
      } catch (error) {
        console.error(error);
        setItems([]);
        alert("검색 중 오류가 발생했습니다. 다시 시도해주세요.");
      }
    } else {
      setItems([]);
    }
  };

  useEffect(() => {
    fetchData();
  }, [query, sortBy, start, display]);

  const loadMore=()=>{
    setStart(start => start + display);
  }

  const onClickItem = (item, e) => {
    if (!e || !e.target || e.target.nodeName !== "BUTTON") {
      window.open(item.link, "_blank");
    } else {
      e.preventDefault();
    }
  };

  const handleSortByChange = (e) => {
    const selectedValue = e.target.value;
   
      setSortBy(selectedValue);
      setStart(1);
      setItems([]); 
    }

    return (
      <>
      <div className="search-container">
      <div className="searchlist-top">
        <div className="countItem"> 검색 결과 ({items.length >= 99 ? '99+' : items.length})</div>
        <div className='sort-box'>
          <label htmlFor="sortOrder"></label>
          <select id="sortBy" name="sortBy" value={sortBy} onChange={handleSortByChange}>
            <option value="sim">관련도순</option>
            <option value="asc">낮은가격순</option>
            <option value="date">최신순</option>
          </select>
        </div>
      </div>
      <div className="listline"></div>
        <ul>    
          {items.map(item => (
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
        {items.length > 0  && (
        <button onClick={loadMore} className='findMorebtn'>더 보기</button>
        )}
      </ul>
    
          
    </div>
    </>
  );

};
export default Search;