import React, { useEffect } from 'react';

function SearchItem() {
  useEffect(() => {
    const handleKeyPress = (e) => {
      if (e.key === 'Enter') {
        execSearch();
      }
    };
    const handleClose = () => {
      document.querySelector('#container').classList.remove('active');
    };
    const handleNavSeeClick = () => {
      document.querySelector('div.nav-see').classList.add('active');
      document.querySelector('div.nav-search').classList.remove('active');
      document.querySelector('#see-area').style.display = 'block';
      document.querySelector('#search-area').style.display = 'none';
    };
    const handleNavSearchClick = () => {
      document.querySelector('div.nav-see').classList.remove('active');
      document.querySelector('div.nav-search').classList.add('active');
      document.querySelector('#see-area').style.display = 'none';
      document.querySelector('#search-area').style.display = 'block';
    };
    
    document.querySelector('#see-area').style.display = 'block';
    document.querySelector('#search-area').style.display = 'none';
    document.querySelector('#query').addEventListener('keypress', handleKeyPress);
    document.querySelector('#close').addEventListener('click', handleClose);
    document.querySelector('.nav div.nav-see').addEventListener('click', handleNavSeeClick);
    document.querySelector('.nav div.nav-search').addEventListener('click', handleNavSearchClick);
    
    showProduct();
    
    return () => {
      document.querySelector('#query').removeEventListener('keypress', handleKeyPress);
      document.querySelector('#close').removeEventListener('click', handleClose);
      document.querySelector('.nav div.nav-see').removeEventListener('click', handleNavSeeClick);
      document.querySelector('.nav div.nav-search').removeEventListener('click', handleNavSearchClick);
    };
  }, []);

  return (
    <div>
      {/* 여기에 JSX 코드를 작성합니다 */}
    </div>
  );
}

export default SearchItem;