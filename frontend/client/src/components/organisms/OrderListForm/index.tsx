import { useEffect, useState } from 'react';
import { OrderListFormContainer } from './styled';
import OrderListItem from 'components/atoms/OrderListItem';
import { getOrders } from 'api/order';
import { useNavigate } from 'react-router-dom';
import { AxiosError, AxiosResponse } from 'axios';
import Button from 'components/atoms/Button';

function OrderListForm() {
  const navigate = useNavigate();

  const [orderItems, setOrderItems] = useState([]);

  const handleSuccess = (response: AxiosResponse) =>{
    console.log(response.data);
    setOrderItems(response.data.data.orderItems);
  }

  const handleFail = (error: AxiosError) => {
    console.log(error)
    alert('내역 조회 중 문제가 생겼습니다.\n관리자에게 문의하세요.');
    navigate('/');
  }

  const writeReview = (orderId: number) => {
    // TODO: 리뷰를 작성할 수 있는 페이지로 이동한다.
    // 해당페이지에서는 리뷰가 존재하면 review 를 보여주고(삭제가능), 리뷰가 없을 경우 리뷰를 작성할 수 있도록 한다.
    navigate('/mypage/review', {state : {orderId: orderId}});
  }

  useEffect(()=>{
    getOrders(handleSuccess, handleFail);
  }, []);

  return (
    <OrderListFormContainer>
      {orderItems.map((orderItem : any) =>
        (orderItem.status === 'PROCESSING' || orderItem.status === 'PENDING') && (
          <div className="waiting">
            <p className="nowTitle gray">예상 조리시간: {orderItem.expectTime}분</p>
            <p className="gray">{orderItem.createdTime}</p>
            <div className="orderDetail">
              <p>주문 항목</p>
              {orderItem.orderMenuItems.map((orderMenuItem:any) => (
                <div className="orderDetailMenu">
                  <p>
                    {orderMenuItem.menuName} X{orderMenuItem.quantity}
                  </p>
                  <p>{orderMenuItem.price}원</p>
                </div>
              ))}
              <div className="totalPrice">
                <p>총 결제 금액</p>
                <p>{orderItem.totalPrice}원 </p>
              </div>
            </div>
          </div>
        ) 
      )}
      <div className="title">
        <p>완료된 주문</p>
      </div>
      {orderItems.some((orderItem: any) => orderItem.status === 'COMPLETE') ? (
        orderItems.map((orderItem: any) =>
          orderItem.status === 'COMPLETE' && (
            <div key={orderItem.orderId}>
              <OrderListItem
                orderId={orderItem.orderId}
                totalPrice={orderItem.totalPrice}
                createdTime={orderItem.createdTime}
                orderMenuItems={orderItem.orderMenuItems.map((item: any) => ({
                  id: item.id,
                  quantity: item.quantity,
                  menuName: item.menuName,
                }))}
              />
              <Button
                size='s'
                text='리뷰'
                color='Primary'
                radius='m'
                handleClick={() => writeReview(orderItem.orderId)}
              />
            </div>
          )
        )
      ) : (
        <div className='emptyDiv'> 텅 </div>
      )}
    </OrderListFormContainer>
  );
}

export default OrderListForm;
