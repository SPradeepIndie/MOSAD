import * as React from 'react';
import { LineChart } from '@mui/x-charts/LineChart';

export default function LineGraph({ dataSet ,xaxis}) {
  return (
    <LineChart
      xAxis={[{ data: xaxis }]}
      series={dataSet}
      sx={{width:'70%'}}
      height={300}
    />
  );
}
