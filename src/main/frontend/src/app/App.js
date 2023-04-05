import React, { Component } from 'react';

import { BrowserRouter, Switch, Route } from 'react-router-dom';
import Header from '../common/Header';
import Footer from '../common/Footer';
import Main from '../pages/Main';
import Signup from '../pages/user/Signup';
import Login from '../pages/user/Login';
import Mypage from '../pages/user/Mypage';
import ProfileModify from '../pages/user/ProfileModify';

import Search from '../pages/Search';
import ZzimItem from '../components/ZzimItem'
import Save from '../components/Save';

import OAuth2RedirectHandler from '../pages/user/OAuth2RedirectHandler';

import NotFound from '../common/NotFound';
import LoadingIndicator from '../common/LoadingIndicator';

import { getCurrentUser } from '../util/APIUtils';
import { ACCESS_TOKEN, REFRESH_TOKEN } from '../constants';
import PrivateRoute from '../common/PrivateRouter';


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
            <Header authenticated={this.state.authenticated} onLogout={this.handleLogout} onSearch={this.handleSearch} />
          </div>
          <div className="app-body">
            <Switch>
              <Route exact path="/" component={Main}></Route>    
              <PrivateRoute path="/mypage" authenticated={this.state.authenticated} currentUser={this.state.currentUser}
                component={Mypage}></PrivateRoute>
              <PrivateRoute path="/setting/profile" authenticated={this.state.authenticated} currentUser={this.state.currentUser}
              component={ProfileModify}></PrivateRoute>
              <Route path="/login"
                render={(props) => <Login authenticated={this.state.authenticated} {...props} />}></Route>
              <Route path="/signup"
                render={(props) => <Signup authenticated={this.state.authenticated} {...props} />}></Route>
              <Route path="/oauth2/redirect" component={OAuth2RedirectHandler}></Route>  
              <Route path="/search" 
              			render={(props) => <Search query={this.state.query} authenticated={this.state.authenticated} currentUser={this.state.currentUser}/>}></Route>
              <PrivateRoute path="/save" 
              			render={(props)=><Save authenticated={this.state.authenticated} currentUser={this.state.currentUser}/>}></PrivateRoute>
              <Route path="/aa" 
              			render={(props)=><ZzimItem authenticated={this.state.authenticated} currentUser={this.state.currentUser}/>}></Route>
 

              <Route component={NotFound}></Route>
            </Switch>
          </div>  
            </BrowserRouter>
            <div className="app-bottom-box">
            <Footer />
            </div>
        </div>
               
      );
    }
  }
  export default App ;
