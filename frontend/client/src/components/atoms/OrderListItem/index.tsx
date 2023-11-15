import { OrderListItemWrapper } from './style';

interface IOrderListItemProps {
  orderId: number;
  totalPrice: number;
  createdTime: string;
  orderMenuItems: {
    id: number;
    quantity: number;
    menuName: string;
  }[];
}

function OrderListItem(props: IOrderListItemProps) {
  const { orderId, totalPrice, createdTime, orderMenuItems } = props;

  const movedetail = () => {};
  return (
    <OrderListItemWrapper onClick={movedetail}>
      <p>{createdTime}</p>
      {orderMenuItems.map((orderMenuItem) => (
        <p key={orderMenuItem.id}>
          {orderMenuItem.menuName} x{orderMenuItem.quantity}
        </p>
      ))}
      <p>결제금액: {totalPrice} 원</p>
    </OrderListItemWrapper>
  );
}

export default OrderListItem;
