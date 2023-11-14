import { OrderListFormContainer } from './styled';

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
        }
      ]
    }
  ];
  return (
    <OrderListFormContainer>
      <div className="title">
        <p>주문 내역</p>
      </div>
      {/* {orderItems.map((orderItem) => (

      ))} */}
    </OrderListFormContainer>
  );
}

export default OrderListForm;
