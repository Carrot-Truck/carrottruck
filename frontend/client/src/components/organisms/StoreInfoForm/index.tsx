import { StoreInfoContainer } from './style';

function StoreInfoForm() {
  const foodTruckInfo = {
    foodTruck: {
      foodTruckId: 1,
      foodTruckName: '동현 된장삼겹',
      phoneNumber: '010-1234-5678',
      content: '된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭',
      originInfo: '돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)',
      isOpen: false,
      isLiked: true,
      prepareTime: 30,
      avgGrade: 4.5,
      likeCount: 132,
      reviewCount: 1324,
      distance: 123.123,
      address: '광주 광산구 장덕로 5번길 16',
      foodTruckImageUrl: 'imageUrl',
      selected: true,
      isNew: true,
      vendorName: '김동현',
      tradeName: '동현 된장삼겹',
      businessNumber: '123-45-23523'
    },
    menus: [
      {
        menuId: 1,
        menuName: '달콤짭짤한 밥도둑 된장 삼겹살 구이',
        menuPrice: 8900,
        menuDescription: '동현 된장삼겹의 시그니쳐. 오직 된장 삼겹살 구이만!',
        menuSoldOut: false,
        menuImageUrl: 'imageUrl'
      },
      {
        menuId: 2,
        menuName: '노른자 된장 삼겹살 덮밥',
        menuPrice: 6900,
        menuDescription: '감칠맛이 터져버린 한그릇 뚝딱 삼겹살 덮밥',
        menuSoldOut: false,
        menuImageUrl: 'imageUrl'
      }
    ],
    schedules: [
      {
        scheduleId: 1,
        address: '광주 광산구 장덕로5번길 16',
        dayOfWeek: 'MONDAY',
        startTime: '09:57',
        endTime: '14:57'
      },
      {
        scheduleId: 2,
        address: '광주 광산구 장덕로5번길 16',
        dayOfWeek: 'TUESDAY',
        startTime: '09:57',
        endTime: '14:57'
      },
      {
        scheduleId: 3,
        address: '',
        dayOfWeek: 'WEDNESDAY',
        startTime: '09:57',
        endTime: '14:57'
      }
    ]
  };
  return (
    <StoreInfoContainer>
      <div className="BusinessInfo">
        <div className="title">
          <p>영업정보</p>
          <p>상호명</p>
          <p>운영시간</p>
        </div>
        <div className="content">
          <p>{foodTruckInfo.foodTruck.foodTruckName}</p>
          {foodTruckInfo.schedules.map((schedule) => (
            <div className="time" key={schedule.scheduleId}>
              <p>
                {schedule.dayOfWeek} - {schedule.startTime} ~ {schedule.endTime}
              </p>
              <p>{schedule.address} </p>
            </div>
          ))}
        </div>
      </div>
      <div className="SellerInfo">
        <div className="title">
          <p>사업자 정보</p>
        </div>
        <div className="content">
          <div>
            <p>대표자명</p>
            <p>{foodTruckInfo.foodTruck.vendorName}</p>
          </div>
          <div>
            <p>상호명</p>
            <p>{foodTruckInfo.foodTruck.tradeName}</p>
          </div>
          <div>
            <p>사업자 등록번호</p>
            <p>{foodTruckInfo.foodTruck.businessNumber}</p>
          </div>
        </div>
      </div>
      <div className="OriginInfo">
        <div className="title">
          <p>원산지 표기</p>
        </div>
        <div className="content">{foodTruckInfo.foodTruck.originInfo}</div>
      </div>
    </StoreInfoContainer>
  );
}

export default StoreInfoForm;
