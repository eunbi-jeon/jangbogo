import React, { Component } from 'react';
import { Route, Switch } from 'react-router-dom';

import Header from '../common/Header';
import Footer from '../common/Footer';
import Main from '../pages/Main';
import Login from '../pages/user/Login';
import Signup from '../pages/user/Signup';
import Mypage from '../pages/user/Mypage';
import ProfileModify from '../pages/user/ProfileModify';
import BoardList from '../pages/BoardList';
import BoardDetail from '../pages/BoardDetail';


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
    if(this.state.loading) {
      return <LoadingIndicator />
    }

    return (
      <div className="app">
        <div className="app-top-box">
          <Header authenticated={this.state.authenticated} onLogout={this.handleLogout} />
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
            <PrivateRoute path="/board/list" authenticated={this.state.authenticated} currentUser={this.state.currentUser}
              component={BoardList}></PrivateRoute>
            <PrivateRoute path="/board/detail{id}" authenticated={this.state.authenticated} currentUser={this.state.currentUser}
              component={BoardDetail}></PrivateRoute>
            <Route component={NotFound}></Route>
          </Switch>
        </div>
        <div className="app-bottom-box">
          <Footer />
        </div>
      </div>
    );
  }
}

export default App;