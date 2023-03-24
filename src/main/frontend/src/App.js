import React from 'react';
import {BrowserRouter, Routes, Route} from 'react-router-dom';
import Search from "./pages/Search";
import Header from './components/Header';
function App() {
    
  return (
    <div className="root-wrap"> 

  <BrowserRouter>
  <Header/>
    <Routes>
      <Route path="/search" element={<Search/>}/>
   </Routes>
  </BrowserRouter>
     </div>
   
  );
}

export default App;