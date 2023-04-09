import React, { Component } from 'react';

import { BrowserRouter, Switch, Route } from 'react-router-dom';
import Header from '../common/Header';
import Footer from '../common/Footer';
import Main from '../pages/Main';
import Signup from '../pages/user/Signup';
import Login from '../pages/user/Login';
import Mypage from '../pages/user/Mypage';
import ProfileModify from '../pages/user/ProfileModify';

import BoardList from '../pages/BoardList';
import BoardDetail from '../pages/BoardDetail';
import BoardCreate from '../pages/BoardCreate';

import FindPassword from '../pages/user/FindPassword';


import MessageList from '../pages/message/MessageSenderList';
import MessageReceiverDetail from '../pages/message/MessageReceiverDetail';
import MessageSenderDetail from '../pages/message/MessageSenderDetail';
import MessageForm from '../pages/message/MessageForm';


import Search from '../pages/Search';
import ZzimItem from '../components/ZzimHandler'
import Save from '../components/Save';

import OAuth2RedirectHandler from '../pages/user/OAuth2RedirectHandler';

import NotFound from '../common/NotFound';
import LoadingIndicator from '../common/LoadingIndicator';

import { getCurrentUser } from '../util/APIUtils';
import { ACCESS_TOKEN, REFRESH_TOKEN } from '../constants';
import PrivateRoute from '../common/PrivateRouter';

import '../css/root.css'


class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      authenticated: false,
      currentUser: null,
      loading: true,
      query:''
    }
    this.handleSearch=this.handleSearch.bind(this);
    this.loadCurrentlyLoggedInUser = this.loadCurrentlyLoggedInUser.bind(this);
    this.handleLogout = this.handleLogout.bind(this);
    }
    
    handleSearch(query){
      this.setState({query});
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
      if(this.state.loading) {
        return <LoadingIndicator />
      }
      
      return (
        <div className="app">
            <BrowserRouter>
          <div className="app-top-box">
            <Header authenticated={this.state.authenticated} currentUser={this.state.currentUser} onLogout={this.handleLogout} onSearch={this.handleSearch} />
          </div>
          <div className="app-body">
            <Switch>
              <PrivateRoute path="/mypage" authenticated={this.state.authenticated} currentUser={this.state.currentUser} component={Mypage}></PrivateRoute>
              <PrivateRoute path="/setting/profile" authenticated={this.state.authenticated} currentUser={this.state.currentUser} component={ProfileModify}></PrivateRoute>
              <Route path="/login" render={(props) => <Login authenticated={this.state.authenticated} {...props} />}></Route>
              
              <PrivateRoute path="/messages/postbox/receiver/:id" authenticated={this.state.authenticated} currentUser={this.state.currentUser} component={MessageReceiverDetail}></PrivateRoute>  
              <PrivateRoute path="/messages/postbox/sender/:id" authenticated={this.state.authenticated} currentUser={this.state.currentUser} component={MessageSenderDetail}></PrivateRoute>  
              <PrivateRoute path="/messages/postbox" authenticated={this.state.authenticated} currentUser={this.state.currentUser}  component={MessageList}></PrivateRoute>
              <PrivateRoute path="/messages" authenticated={this.state.authenticated} currentUser={this.state.currentUser} component={MessageForm}></PrivateRoute>
      
              <Route path="/signup" render={(props) => <Signup authenticated={this.state.authenticated} {...props} />}></Route>
              <Route path="/oauth2/redirect" component={OAuth2RedirectHandler}></Route>

              <PrivateRoute path="/board/detail/:board_id/:id" authenticated={this.state.authenticated} currentUser={this.state.currentUser} component={BoardDetail}></PrivateRoute>
              <PrivateRoute path="/board/create/:board_id/:region?" authenticated={this.state.authenticated} currentUser={this.state.currentUser} component={BoardCreate}></PrivateRoute>
              <PrivateRoute path="/board/modify/:board_id/:id/:region?" authenticated={this.state.authenticated} currentUser={this.state.currentUser} component={BoardCreate}></PrivateRoute>
              <PrivateRoute path="/board/list/:board_id/:region?" authenticated={this.state.authenticated} currentUser={this.state.currentUser} component={BoardList}></PrivateRoute>
              <Route path="/search" render={(props) => <Search query={this.state.query} authenticated={this.state.authenticated} currentUser={this.state.currentUser} />} />
              <PrivateRoute path="/save" render={(props)=><Save authenticated={this.state.authenticated} currentUser={this.state.currentUser}/>}></PrivateRoute>
              <Route path="/myfav" render={(props)=><ZzimItem authenticated={this.state.authenticated} currentUser={this.state.currentUser}/>}></Route>

              <Route path="/password/find" component={FindPassword}></Route>
              <Route exact path="/" render={(props) => <Main currentUser={this.state.currentUser}/>}></Route> 
              <Route component={NotFound}></Route>
            </Switch>
          </div>
          <div className="app-bottom-box">
            <Footer />
          </div>
            </BrowserRouter>
        </div>      
      );
    }
  }
  export default App ;
