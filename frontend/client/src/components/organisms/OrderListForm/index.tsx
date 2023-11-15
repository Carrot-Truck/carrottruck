import { OrderListFormContainer } from './styled';
import OrderListItem from 'components/atoms/OrderListItem';

function OrderListForm() {
  const orderItems = [
    {
      orderId: 1,
      status: 'COMPLETE',
      orderCnt: 0,
      totalPrice: 40000,
      createdTime: '2023-10-30 Mon 17:25',
      expectTime: '55',
      orderMenuItems: [
        {
          id: 1,
          menuId: 1,
          quantity: 1,
          menuName: '메뉴 이름1',
          price: 1000,
          menuOptionList: [2, 4]
        },
        {
          id: 2,
          menuId: 2,
          quantity: 1,
          menuName: '메뉴 이름2',
          price: 2000,
          menuOptionList: [1, 2]
        }
      ]
    },
    {
      orderId: 2,
      status: 'PROCESSING',
      orderCnt: 1,
      totalPrice: 15000,
      createdTime: '2023-11-01 Wed 17:35',
      expectTime: '05',
      orderMenuItems: [
        {
          id: 3,
          menuId: 2,
          quantity: 1,
          menuName: '메뉴 이름3',
          price: 3000,
          menuOptionList: [2, 3]
        },
        {
          id: 4,
          menuId: 4,
          quantity: 2,
          menuName: '메뉴 이름4',
          price: 3000,
          menuOptionList: [2, 3]
        }
      ]
    }
  ];
  return (
    <OrderListFormContainer>
      {orderItems.map((orderItem) =>
        orderItem.status === 'PROCESSING' ? (
          <div className="waiting">
            <p className="nowTitle gray">예상 조리시간: {orderItem.expectTime}분</p>
            <p className="gray">{orderItem.createdTime}</p>
            <div className="orderDetail">
              <p>주문 항목</p>
              {orderItem.orderMenuItems.map((orderMenuItem) => (
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
        ) : (
          <div></div>
        )
      )}
      <div className="title">
        <p>주문 내역</p>
      </div>
      {orderItems.map((orderItem) =>
        orderItem.status === 'COMPLETE' ? (
          <OrderListItem
            orderId={orderItem.orderId}
            totalPrice={orderItem.totalPrice}
            createdTime={orderItem.createdTime}
            orderMenuItems={orderItem.orderMenuItems.map((item) => ({
              id: item.id,
              quantity: item.quantity,
              menuName: item.menuName
            }))}
          />
        ) : (
          <div></div>
        )
      )}
    </OrderListFormContainer>
  );
}

export default OrderListForm;