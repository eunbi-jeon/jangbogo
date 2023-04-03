import axios from "axios";
import { useHistory } from "react-router-dom";

function Save(props) {
  const history = useHistory();
  const currentUser = props.currentUser.information;

  const item = props.item



  const saveItem = async () => {
    try {
      const response = await axios.post(
        "/api/products",
        { productId:item.productId, 
        title:item.title,
        link:item.link,
        image:item.image,
        mallName:item.mallName,
        lprice:item.lprice,
        name:currentUser.name },
        {
          headers: {
            "Content-Type": "application/json;charset=UTF-8",
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken'),
          },
        }
      );
       return response.data.productId;
    } catch (error) {
 	console.error(error); // 오류 메시지 출력
    	alert("상품이 저장되지 않았습니다. 잠시 후 다시 시도해주세요.");
 		 return false;
    }
  };

  const handleSaveButtonClick = async () => {
    if (currentUser !=null) {
     const savedItemId = await saveItem();
    console.log(currentUser.name);
    console.log(savedItemId);
    } else {
      const shouldLogin = window.confirm("로그인하시겠습니까?");
      if (shouldLogin) {
        history.push("/login");
      }
    }
  };


  return (
    <>
      <button onClick={handleSaveButtonClick}>보관하기</button>
    </>
  );
}

export default Save;
