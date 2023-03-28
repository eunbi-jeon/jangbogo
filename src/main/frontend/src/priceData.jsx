import axios from 'axios';

function Price() {
  const [data, setData] = useState(null);

  useEffect(() => {
    axios.get('/price')
      .then(item => setData(item.data))
      .catch(error => console.error(error));
  }, []);

  if (!data) return <div>Loading...</div>;

  return (
    <div>
      {data.item.map((item, index) => (
        <div key={index}>
          <h3>{item.item_name}</h3>
          <p>품목코드: {item.item_code}</p>
          <p>단위: {item.unit}</p>
          <p>조회일자: {item.day1}, 가격: {item.dpr1}</p>
          <p>1일전 일자: {item.day2}, 가격: {item.dpr2}</p>
          <p>1주일전 일자: {item.day3}, 가격: {item.dpr3}</p>
        </div>
      ))}
    </div>
  );
}
