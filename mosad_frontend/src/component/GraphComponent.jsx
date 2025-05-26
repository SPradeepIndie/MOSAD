import * as React from 'react';
import { BarChart } from '@mui/x-charts/BarChart';
import PropTypes from 'prop-types';

export default function GraphComponent({ dataSet ,xaxis}) {
  return (
    <BarChart
      series={dataSet}
      height={290}
      xAxis={[{ data: xaxis, scaleType: 'band' }]}
      margin={{ top: 10, bottom: 30, left: 40, right: 10 }}
    />
  );
}

GraphComponent.propTypes={
  dataSet:PropTypes.array.isRequired,
  xaxis:PropTypes.array.isRequired,
}