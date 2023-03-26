import { Route, Routes } from 'react-router-dom';
import Main from './pages/Main';
import Login from './pages/Login';
import Signup from './pages/Signup';
import Mypage from './pages/Mypage';
import NotFound from './pages/NotFound';


function App() {
    return (
        <div className='App'>
            <Routes>
                <Route path='/' element={<Main />}></Route>
                <Route path='/login' element={<Login />}></Route>
                <Route path='/signup' element={<Signup />}></Route>
                <Route path='/mypage' element={<Mypage />}></Route>
                {/* 규칙에 적용되지 않는 나머지 모든 경로 처리 */}
                <Route path='*'element={<NotFound />}></Route>
            </Routes>
        </div>
    );
}

export default App;