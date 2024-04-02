"use client"
import { ChevronLeft, CircleCheck } from 'lucide-react';
import Link from 'next/link';
import React from 'react'

export default function ActiveComp({ data }: { data: any }) {
  console.log("hello");
  console.log("data: ", data);
  return (
    <div>
      <div className="absolute bg-black h-full w-full top-0 left-0 flex justify-center items-center">
        <div className="relative w-1/2 mx-auto p-10 rounded-lg shadow-md dark:bg-stone-950 border-2">
          <div className="absolute bg-black h-full w-full top-0 left-0  flex justify-center items-center">
            <div className="flex flex-col justify-center items-center">
              <CircleCheck size={64} />
              <div className="flex mt-5">
                We have sent email to <h2 className="text-cyan-400 ml-1 font-bold">{nofEmail}</h2>
              </div>
              <h2>Please check your email</h2>
              <div>
                <Link href={"/login"} className="flex inline-block mt-20 items-center justify-center mr-auto hover:underline text-sky-500">
                  <ChevronLeft /> Back Login
                </Link>
              </div>

            </div>
          </div>

        </div>
      </div>
    </div>
  )
}
