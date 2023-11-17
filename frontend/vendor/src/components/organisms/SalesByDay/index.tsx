import SalesByDayItem from "components/atoms/SalesByDayItem";
import { SalesByDayContainer } from "./style";
import { Chart as ChartJS, Legend, Tooltip, ArcElement, registerables } from "chart.js";
import { Chart } from "react-chartjs-2";
import { useState } from "react";

interface ISalesByDay {
  totalOrders: number;
  totalSales: number;
  day: number;
}

interface ISalesByDayProps {
  salesByDay: ISalesByDay[];
}

const dayMapper: Record<number, string> = {
  1: "일",
  2: "월",
  3: "화",
  4: "수",
  5: "목",
  6: "금",
  7: "토",
};

function SalesByDay({ salesByDay }: ISalesByDayProps) {
  ChartJS.register(ArcElement, Tooltip, Legend, ...registerables);

  const [criteria, setCriteria] = useState<number>(1);

  const labels = [];
  const ordersData = [];
  const salesData = [];
  let index = 0;
  for (let i = 1; i <= 7; i++) {
    labels.push(dayMapper[i]);
    if (index < salesByDay.length && salesByDay[index].day === i) {
      ordersData.push(salesByDay[index].totalOrders);
      salesData.push(salesByDay[index].totalSales);
      index++;
    } else {
      ordersData.push(0);
      salesData.push(0);
    }
  }

  const bySalesData = {
    labels: labels,
    datasets: [
      {
        data: salesData,
        backgroundColor: [
          "rgba(255, 99, 132, 0.2)",
          "rgba(54, 162, 235, 0.2)",
          "rgba(255, 206, 86, 0.2)",
          "rgba(75, 192, 192, 0.2)",
          "rgba(153, 102, 255, 0.2)",
          "rgba(255, 159, 64, 0.2)",
          "rgba(221, 6, 187, 0.2)",
        ],
        borderColor: [
          "rgba(255, 99, 132, 1)",
          "rgba(54, 162, 235, 1)",
          "rgba(255, 206, 86, 1)",
          "rgba(75, 192, 192, 1)",
          "rgba(153, 102, 255, 1)",
          "rgba(255, 159, 64, 1)",
          "rgba(221, 6, 187, 1)",
        ],
        borderWidth: 1,
      },
    ],
  };

  const byOrderData = {
    labels: labels,
    datasets: [
      {
        data: ordersData,
        backgroundColor: [
          "rgba(255, 99, 132, 0.2)",
          "rgba(54, 162, 235, 0.2)",
          "rgba(255, 206, 86, 0.2)",
          "rgba(75, 192, 192, 0.2)",
          "rgba(153, 102, 255, 0.2)",
          "rgba(255, 159, 64, 0.2)",
          "rgba(221, 6, 187, 0.2)",
        ],
        borderColor: [
          "rgba(255, 99, 132, 1)",
          "rgba(54, 162, 235, 1)",
          "rgba(255, 206, 86, 1)",
          "rgba(75, 192, 192, 1)",
          "rgba(153, 102, 255, 1)",
          "rgba(255, 159, 64, 1)",
          "rgba(221, 6, 187, 1)",
        ],
        borderWidth: 1,
      },
    ],
  };

  return (
    <SalesByDayContainer>
      <div className="day-criteria">
        <div className={criteria === 1 ? "selected" : ""} onClick={() => setCriteria(1)}>
          매출액
        </div>
        <div className={criteria === 2 ? "selected" : ""} onClick={() => setCriteria(2)}>
          주문수
        </div>
      </div>
      <div className="day-doughnut-wrapper">
        <Chart
          type="doughnut"
          data={criteria === 1 ? bySalesData : byOrderData}
          options={{
            responsive: true,
            maintainAspectRatio: true,
          }}
        />
      </div>
      {salesByDay.map((data: ISalesByDay, index: number) => (
        <SalesByDayItem
          key={index}
          totalOrders={data.totalOrders}
          totalSales={data.totalSales}
          day={dayMapper[data.day]}
        />
      ))}
    </SalesByDayContainer>
  );
}

export default SalesByDay;
