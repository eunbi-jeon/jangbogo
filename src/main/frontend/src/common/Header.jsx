import React, {Component} from 'react';
import "../css/header.css"
import "../css/root.css"
import logoImg from "../img/logo.png"
import { Link, withRouter} from 'react-router-dom';
 
class Header extends Component {
  constructor(props){
    super(props);
    this.state={
      query:'',
    };
    this.handleInputChange=this.handleInputChange.bind(this);
    this.handleKeyPress=this.handleKeyPress.bind(this);
  }

  componentDidMount(){
    const query=new URLSearchParams(window.location.search).get('query');
    if(query){
      this.setState({query});
    }
  }

  handleInputChange = (e) => {
    const query=e.target.value;
    console.log(query);
    this.setState({query},()=>{
      this.forceUpdate();
    });

  };
  
  handleKeyPress = (e) => {
    if (e.key === "Enter") {
      const { query } = this.state;

      const { onSearch } = this.props;  
      if (query && typeof onSearch === 'function') { 
        onSearch(query);  
      }
      this.props.history.push(`/search?query=${query}`);
    }
  
  };
  render(){
  
  return (
    <div>
      <div className="header-container">
        <div className="header-wrap">
          <div className="header-left">
            <div>
            <Link to='/' 
            onClick={() => {this.setState({query: ''}); 
            this.props.history.push('/');}}>
                <img src={logoImg} alt='로고' className='logo-img' />
              </Link>
            </div>
            <div className="search-form">
              <input
                type="text"
                placeholder="검색어를 입력해주세요" 
                value={this.state.query}
                name='query' id='query'
                onChange={this.handleInputChange} 
                onKeyPress={this.handleKeyPress}
 
              />
           
            </div>
          </div>


          <div className="button">
          { this.props.authenticated ? (
                        <div className="button">
                        <button id="sign-up"><Link to='/mypage'>마이페이지</Link></button>
                        <button id="login" onClick={this.props.onLogout}>로그아웃</button>
                            </div>
                              ): (
                        <div className="button">
                        <button id="sign-up"><Link to='/singup'>회원가입</Link></button>
                        <button id="login"><Link to='/login'>로그인</Link></button>
                        </div>
                        )}
          </div>
        </div>
      </div>
      <div className="line"></div>
      <div className="header-menu">
        <div className="menu-wrap">
          <span className="category">전체 카테고리</span>
          <div className="menu-right">
            <span>우리지역</span>
            <span>문의 게시판</span>
          </div>
        </div>
      </div>
  
    </div>
  );
  }
}
export default withRouter(Header);