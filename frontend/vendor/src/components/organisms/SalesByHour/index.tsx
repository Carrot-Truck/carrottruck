import { SalesByHourContainer } from "./style";
import {
  Chart as ChartJS,
  LinearScale,
  CategoryScale,
  BarElement,
  PointElement,
  LineElement,
  Legend,
  Tooltip,
  LineController,
  BarController,
} from "chart.js";
import { Chart } from "react-chartjs-2";

interface ISalesByHour {
  totalOrders: number;
  totalSales: number;
  startHour: number;
}

interface ISalesByHourProps {
  salesByHour: ISalesByHour[];
}

function SalesByHour({ salesByHour }: ISalesByHourProps) {
  ChartJS.register(
    LinearScale,
    CategoryScale,
    BarElement,
    PointElement,
    LineElement,
    Legend,
    Tooltip,
    LineController,
    BarController
  );

  const length = salesByHour.length;
  const DATA_COUNT = salesByHour[length - 1].startHour - salesByHour[0].startHour + 1;
  const labels = [];
  const ordersData = [];
  const salesData = [];
  let index = 0;
  for (let i = salesByHour[0].startHour; i < salesByHour[0].startHour + DATA_COUNT; i++) {
    labels.push(i);
    if (index < salesByHour.length && salesByHour[index].startHour === i) {
      ordersData.push(salesByHour[index].totalOrders);
      salesData.push(salesByHour[index].totalSales);
      index++;
    } else {
      ordersData.push(0);
      salesData.push(0);
    }
  }

  const data = {
    labels: labels,
    datasets: [
      {
        type: "bar" as const,
        label: "매출",
        data: salesData,
        borderColor: "rgb(255, 161, 38)",
        backgroundColor: "rgba(255, 161, 38, 0.5)",
        yAxisID: "saleY",
      },
      {
        type: "line" as const,
        label: "판매량",
        data: ordersData,
        borderColor: "rgb(34, 160, 234)",
        backgroundColor: "rgba(34, 160, 234, 0.5)",
        fill: false,
        yAxisID: "orderY",
      },
    ],
  };

  const options = {
    responsive: true,

    scales: {
      saleY: {
        ticks: {
          callback: function (value: any) {
            return `${Number.parseInt(value) / 10000}만`;
          },
        },
      },
      orderY: {
        ticks: {
          display: false,
          beginAtZero: true,
        },
        grid: {
          drawBorder: false,
          display: false,
        },
      },
    },
  };

  return (
    <SalesByHourContainer>
      <Chart type="bar" data={data} options={options} />
    </SalesByHourContainer>
  );
}

export default SalesByHour;
