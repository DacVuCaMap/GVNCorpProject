"use client"
import postData from '@/pattern-api/postData'
import React from 'react'

export async function getStaticPath({params}:{ params: { slug: string } }){
    let url = process.env.NEXT_PUBLIC_API_URL+"api/auth/get-active"
    let requestData = params.slug;
    let header = null;
    const data = await postData(url,requestData,header);
    console.log(data)
    return{
        props:{
            data
        }
    }
}

export default function page({data}:any) {
  return (
    <div> vao trang </div>
  )
}
