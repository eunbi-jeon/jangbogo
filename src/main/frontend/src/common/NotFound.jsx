import React, { Component } from 'react';
import { Link } from 'react-router-dom';

class NotFound extends Component {
    render() {
        return (
            <div className="page-not-found">
                <h1 className="title">
                    404
                </h1>
                <div className="not-found-desc">
                    원하시는 페이지를 찾을 수 없습니다.
                </div>
                <div className='not-found-sub-desc'>페이지의 주소가 잘못 입력되었거나,</div>
                <div className='not-found-sub-desc'>주소의 변경 혹은 삭제로 인해 사용할 수 없습니다.</div>
                <div className='not-found-sub-desc'>입력하신 주소가 맞는지 다시 한번 확인해주세요.</div>
                <Link to="/"><button className="go-back-btn" type="button">메인 화면으로 돌아가기</button></Link>
            </div>
        );
    }
}

export default NotFound;