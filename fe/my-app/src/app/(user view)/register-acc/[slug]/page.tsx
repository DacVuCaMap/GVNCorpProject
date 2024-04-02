// page.tsx
import ActiveComp from '@/component/ActiveComp/ActiveComp';
import axios from 'axios';
import { notFound } from 'next/navigation';
import React from 'react';

export default async function Page({ params }: { params: { slug: string } }) {
  const url = process.env.NEXT_PUBLIC_API_URL + "/api/auth/get-active/" + params.slug;
  try {
    const res = await axios.post(url, {}, { headers: { "Content-type": 'text/plain' } });
    console.log(res.data); // In ra dữ liệu ở đây
    return <ActiveComp data={res.data} />;
  } catch (error) {
    console.error("Error fetching data: ", error);
    notFound();
  }
}