import React, { Component } from 'react';
import { Route, Routes } from 'react-router-dom';

import { ACCESS_TOKEN, REFRESH_TOKEN } from '../constants';
import { getCurrentUser } from '../util/APIUtils';

import Header from '../components/Header';
import Main from '../pages/Main';
import Login from '../pages/Login';
import Signup from '../pages/Signup';
import Mypage from '../pages/Mypage';
import NotFound from '../pages/NotFound';

class App extends Component {
    constructor(props) {
      super(props);
      this.state = {
        authenticated: false,
        currentUser: null,
        loading: true
      }
  
      this.loadCurrentlyLoggedInUser = this.loadCurrentlyLoggedInUser.bind(this);
      this.handleLogout = this.handleLogout.bind(this);
    }

    loadCurrentlyLoggedInUser() {
        getCurrentUser()
        .then(response => {
          this.setState({
            currentUser: response,
            authenticated: true,
            loading: false
          });
        }).catch(error => {
          this.setState({
            loading: false
          });  
        });    
      }
    
      handleLogout() {
        localStorage.removeItem(ACCESS_TOKEN);
        localStorage.removeItem(REFRESH_TOKEN);
        this.setState({
          authenticated: false,
          currentUser: null
        });
        alert("로그아웃 했습니다.");
      }
    
      componentDidMount() {
        this.loadCurrentlyLoggedInUser();
      }
    
    render() {

        return (
            <div className='App'>
                <div className="app-top-box">
                    <Header authenticated={this.state.authenticated} onLogout={this.handleLogout} />
                </div>
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
}

export default App;