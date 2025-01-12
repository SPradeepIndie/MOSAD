import { Outlet } from 'react-router-dom';
import { Suspense } from 'react';

export default function StockPageLayout() {
  return (
    <Suspense>
    <Outlet/>
    </Suspense>
  );
}

