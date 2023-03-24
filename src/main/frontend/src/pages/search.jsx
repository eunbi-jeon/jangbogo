/* eslint-disable react-hooks/rules-of-hooks */
import React, {useState} from 'react';
import SearchItem from '../components/SearchItem';
import axios from 'axios';

let targetId;


function numberWithCommas(price) {
    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

async function execSearch() {
	let query = $('#query').val();


    if (query == '') {
        alert('검색어를 입력해주세요');
        $('#query').focus();
        return;
    }
    
    try {
    const response = await axios.get(`/api/search?query=${query}`);
    $('#search-result-box').empty();

	    for (let i = 0; i < response.data.length; i++) {
	        let itemDto = response.data[i];
	        let tempHtml = await addHTML(itemDto);
	        $('#search-result-box').append(tempHtml);
	    }
	} catch (error) {
	    console.log(error);
	}
};


function addHTML(itemDto) {
	return `<div class="search-itemDto"> 
				<div class="search-itemDto-left"> 
					<img src="${itemDto.image}" alt=""> 
				</div> 
				<div class="search-itemDto-center"> 
					<div>${itemDto.title}</div> 
					<div class="price"> 
						${numberWithCommas(itemDto.lprice)} <span class="unit">원</span> 
					</div> 
				</div> 
				
				<div class="search-itemDto-right"> 
					<img src="images/icon-save.png" alt="" onclick='addProduct(${JSON.stringify(itemDto)})'> 
				</div> 
			</div>;`
}

async function addProduct(itemDto) {
	try {
	    const response = await axios.post('/api/products', itemDto);
	   
	    $('#container').addClass('active');
	    targetId = response.data.id;
	} catch (error) {
	    console.error(error);
	}
}

function showProduct() {

   
    axios.get('/api/products')
        .then(function (response) {
        
            $('#product-container').empty();
            $('#search-result-box').empty();
          
            for (let i = 0; i < response.data.length; i++) {
                let product = response.data[i];
                let tempHtml = addProductItem(product);
                $('#product-container').append(tempHtml);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

async function addProductItem(product) {
  // link, image, title, lprice, myprice 변수 활용하기
  try {
    const response = await axios.get(`/api/products/${product.id}`);
    const myprice = response.data.myprice;

    return `<div class="product-card" onclick="window.location.href='${product.link}'">
            <div class="card-header">
                <img src="${product.image}"
                     alt="">
            </div>
            <div class="card-body">
                <div class="title">
                    ${product.title}
                </div>
                <div class="lprice">
                    <span>${numberWithCommas(product.lprice)}</span>원
                </div>
                <div class="isgood ${product.lprice > myprice ? 'none' : ''}">
                    최저가
                </div>
            </div>
            </div>`;
  } catch (error) {
    console.error(error);
  }
}

async function setMyprice() {
  try {
   
    let myprice = $('#myprice').val();

    if (myprice == '') {
        alert('최저가를 입력해주세요');
        return;
    }

    const response = await axios.put(`/api/products/${targetId}`, {myprice});

    $('#container').removeClass('active');

    alert('성공적으로 등록되었습니다');

    window.location.reload();
  } catch (error) {
    console.error(error);
  }
}
export default search;