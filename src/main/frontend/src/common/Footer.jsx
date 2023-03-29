import React, { Component } from "react";

import footerLogo from "../img/logo-gray.png"
import '../css/footer.css'

class Footer extends Component {
    render() {
        return (
            <div className="footer-container">
                <div className="footer-box">
                    <img src={footerLogo} alt='로고' className='footer-img' />
                    <div className="footer-des-box">
                    <pre className="footer-des"><b>상호명</b> : 카트왕 장보고  |  <b>대표</b> : 화이팀  |  <b>사업자등록번호</b> : 123-45-678910
                    <br/>
                    <b>문의</b> : jangbogo@gmail.com</pre>
                    </div>
                </div>
            </div>
        )
    }
}

export default Footer;