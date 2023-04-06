import { useSelector } from 'react-redux';
import CartItem from './CartItem';
import './CartItemList.scss';

const ControlBar = () => {
  const totalCheckboxisChecked = useSelector(state => state.cart.isCheckAll);
  const dispatch = useDispatch();

  const checkboxHandler = event => {
    dispatch(cartActions.checkAllHandler(event.target.checked));
  };

  const deleteItemhandler = () => {
    dispatch(cartActions.deleteItems());
  };
}
const ZzimList = () => {
  const ZzimList = useSelector(state => state.cart.itemList);

  return (
    <>

    <div className="cartItemList">
      <div className="cartCheck">
        <div className="checkBoxWrapper">
          <input
            id="checkAll"
            type="checkbox"
            checked={totalCheckboxisChecked}
            onChange={checkboxHandler}
          />
          <label htmlFor="checkAll">
            <div>
              <img src="/images/iconCheckWhite.png" alt="iconCheck" />
            </div>
            전체선택
          </label>
        </div>
        <button className="selectedItemDeleteBtn" onClick={deleteItemhandler}>
          선택 삭제
        </button>
      </div>
      <ul>
        {!ZzimList.length && (
          <p className="emptyCartMessage">맘에 드는 품목을 저장하세요!</p>
        )}

        {ZzimList.map(item => {
          return <CartItem key={item.id} itemList={item} />;
        })}
      </ul>
    </div>
    </>
    )
  
  
};

  export default ZzimList;