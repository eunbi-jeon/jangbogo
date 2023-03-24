import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Header from './components/Header';
import Main from './components/Main';
import Login from './components/Login';
import Signup from './components/Signup';
import NotFound from './components/NotFound';


function App() {
    return (
        <div className='App'>
            <BrowserRouter>
                <Header />
                <Routes>
                    <Route path='/' element={<Main />}></Route>
                    <Route path='/login' element={<Login />}></Route>
                    <Route path='/signup' element={<Signup />}></Route>
                    {/* 규칙에 적용되지 않는 나머지 모든 경로 처리 */}
                    <Route path='*'element={<NotFound />}></Route>
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;