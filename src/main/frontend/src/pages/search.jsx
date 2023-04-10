
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

   //가격정보 검색
   useEffect(() => {
    setpriceinfo([]);
    setcurrentpriceInfo(null);
    if (query) {
      const keyword = query;
      axios.get(`http://localhost:8080/api/price/${keyword}`)
        .then(response => {
          setpriceinfo(response.data);
          setcurrentpriceInfo(response.data[0])
        })
        .catch(error => {
        });
    }
  }, [query]);

  // n초 마다 정보 변경 
  useEffect(() => {
    const intervalId = setInterval(() => {
      const currentIndex = priceinfo.findIndex(item => item === currentpriceInfo);
      if (currentIndex === priceinfo.length - 1) {
        setcurrentpriceInfo(priceinfo[0]);
        console.log(currentpriceInfo);
      } else {
        setcurrentpriceInfo(priceinfo[currentIndex + 1]);
      }
    }, 5000);
    return () => clearInterval(intervalId);
  }, [currentpriceInfo, priceinfo, query]);

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
  

   

    return (
      <div className="search-container">
        <div className='price-search-wrap'>
          {currentpriceInfo && (
            <div key={currentpriceInfo.id} className='priceinfo-wrap'>
              <div className='price-item-item'>{currentpriceInfo.itemName}</div>
              <div className='price-item-name'>{currentpriceInfo.kindName}</div>
              <div className='price-item-rank'>{currentpriceInfo.rank}</div>
              <div className='price-item-price'>당일</div><b style={{fontSize:32}}>{currentpriceInfo.dpr1}</b>
              <div style={{marginRight:30}}>원</div>
            </div>
          )}
          </div>
      <div className="searchlist-top">
        <div className="countItem"> 검색 결과 ({items.length >= 99 ? '99+' : items.length})</div>
        <div className='sort-box'>

          <label htmlFor="sortOrder"></label>
          <select id="sortBy" name="sortBy" value={sortBy} onChange={handleSortByChange}>
            <option value="sim">관련도순</option>
            <option value="asc">낮은 가격순</option>
            <option value="date">최신순</option>
          </select>
        </div>
      </div>
        <div className='searchitem-wrap'>    
          {items.map(item => (
            <div key={item.link}>
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
          </div>
        ))}
        {items.length > 0  && (
        <button onClick={loadMore} className='findMorebtn'>더 보기</button>
        )}
      </div>
    
          
      </div>
  );

};
export default Search;
