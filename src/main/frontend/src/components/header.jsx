import React from 'react';

export default function Header() {
    return (
        <div className='header-container'>
            <div className='header-wrap'>
                <div className='header-left-wrap'>
                {/* <Link to='/'> */}
                    <img src='https://www.themoviedb.org/assets/2/v4/logos/v2/blue_short-8e7b30f73a4020692ccca9c88bafe5dcb6f8a62a4c6bc55cd9ba82bb2cd95f6c.svg' 
                        alt='로고'
                        style={{width: "154px", height: "20px"}}/>
                {/* </Link> */}

                <ul>
                    <li>영화</li>
                    <li>Tv 프로그램</li>
                </ul>
                </div>
            </div>
        </div>
    )
}