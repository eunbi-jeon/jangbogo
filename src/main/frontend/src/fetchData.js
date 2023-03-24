import axios from 'axios';

async function fetchData() {
  const response = await axios.get('http://www.kamis.or.kr/service/price/xml.do', {
    params: {
      action: 'dailyPriceByCategoryList',
      p_cert_key: '1e5a1d54-9b74-4b76-858d-bb13e17176b2',
      p_cert_id: '22.euneu@gmail.com',
      p_returntype: 'json',
      p_product_cls_code: '01',
      p_item_category_code: '100',
      p_country_code: '1101',
      p_regday: '2023-03-22',
      p_convert_kg_yn: 'Y',
    },
  });
  return response.data;
}

export default fetchData;