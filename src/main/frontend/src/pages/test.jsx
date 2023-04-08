// 네이버 쇼핑 API 검색결과를 가져오는 함수
const fetchNaverShopping = async (query, sortBy, startNum) => {
  try {
    // 검색어를 인코딩하여 URL에 추가
    const encodedQuery = encodeURIComponent(query);
    let url = `/api/search?query=${encodedQuery}`;

    // 검색어가 있을 경우 카테고리를 '식품'으로 설정
    if (query) {
      url += "&category1=식품";
    }

    // 정렬 방식에 따라 URL에 추가
    if (sortBy === "asc") {
      url += "&sort=asc";
    } else if (sortBy === "sim") {
      url += "&sort=sim";
    } else if (sortBy === "date") {
      url += "&sort=date";
    }

    // startNum을 URL에 추가하여 검색결과 페이지를 지정
    url += `&start=${startNum}`;

    // API 요청하여 데이터 받아오기
    const response = await axios.get(url);
    const data = await response.data;

    // 카테고리가 '식품'인 데이터만 필터링
    const filteredData = data.filter((item) => item.category1 === "식품");

    // 정렬 방식에 따라 데이터 정렬
    const sortedData =
      sortBy === "asc"
        ? filteredData.sort((a, b) => a.lprice - b.lprice)
        : sortBy === "sim"
        ? filteredData.sort((a, b) => b.mallScore - a.mallScore)
        : filteredData.sort(
            (a, b) => new Date(b.pubDate) - new Date(a.pubDate)
          );

    // 검색결과 데이터 설정
    setItems(sortedData);
  } catch (error) {
    console.error(error);
  }
};
