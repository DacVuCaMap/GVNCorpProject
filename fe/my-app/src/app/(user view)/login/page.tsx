"use client"
import { Button } from '@/components/ui/button';
import postData from '@/pattern-api/postData';
import LoginRequest from '@/request/LoginRequest';
import { Eye, EyeOff, Moon, Sun } from 'lucide-react';
import Link from 'next/link';
import React, { useState } from 'react'
import Image from 'next/image'
import { Checkbox } from '@/components/ui/checkbox';
import { AlertDemo } from '@/components/ui/AlertDemo';

export default function page() {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [isVisible, setVisible] = useState(false);
  const [remember, setRemember] = useState(false);
  const [alertOpen, setAlertOpen] = useState(false);
  const [messErr, setMessErr] = useState("");
  const [titleErr, setTitleErr] = useState("Login failed !");
  const [nameErr, setNameErr] = useState(false);
  const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log(userName, password);
    if (userName.length<5) {
      setNameErr(true);
      setAlertOpen(true);
      setMessErr("Email is not invalid")
      return;
    }
    setNameErr(false);
    const url = process.env.NEXT_PUBLIC_API_URL;
      const loginRequest: LoginRequest = new LoginRequest(userName, password);
      const header = { withCredentials: true }
      const response = await postData(url + "/api/auth/login", loginRequest, header);
      // login failed
      if (!response) {
        console.log("vao day", response)
        setMessErr("Server is stop");
        setAlertOpen(true);
        return;
      }
      if (response.status === 400) {
        setMessErr(response.data);
        setAlertOpen(true);
      }
      //success chuyen trang 
  }


  return (
    <div className='flex flex-col justify-center items-center h-full dark:bg-black'>
      {alertOpen && <AlertDemo setOpen={setAlertOpen} mess={messErr} title={titleErr} />}
      <div className="w-2/5 mx-auto p-10 rounded-lg shadow-md dark:bg-stone-950 border-2">
        <h2 className="text-2xl font-bold mb-4">Login</h2>
        <form onSubmit={handleLogin} className="space-y-4">
          <div>
            <label htmlFor="username" className={`block ${nameErr ? 'text-red-600' : ''}  text-sm font-medium text-gray-700`}>Username</label>
            <input
              type="text"
              id="username"
              name="username"
              autoComplete="username"
              onChange={(e) => setUserName(e.target.value)}
              className={`mt-1 px-4 py-2 block w-full rounded-md border-gray-300 shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm`}
              placeholder="Enter your username"
              required
            />
          </div>
          <div>
            <label htmlFor="password" className="block text-sm font-medium text-gray-700">Password</label>
            <div className="relative">
              <input
                type={isVisible ? "text" : "password"}
                id="password"
                name="password"
                autoComplete="current-password"
                onChange={(e) => setPassword(e.target.value)}
                className="mt-1 px-4 py-2 pr-10 block w-full rounded-md border-gray-300 shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                placeholder="Enter your password"
                required
              />
              <Button type="button" onClick={() => setVisible(!isVisible)} size="icon" className='h-9 absolute inset-y-0 right-0 flex items-center px-3 bg-transparent text-gray-400 hover:text-gray-500 focus:outline-none'>
                {!isVisible ? <Eye className="absolute h-[1.2rem] w-[1.2rem] rotate-90 scale-0 transition-all dark:rotate-0 dark:scale-100" />
                  : <EyeOff className="absolute h-[1.2rem] w-[1.2rem] rotate-90 scale-0 transition-all dark:rotate-0 dark:scale-100" />}
              </Button>
            </div>
          </div>
          <div className="flex justify-between items-center">
            <div className="flex items-center">
              <Checkbox onClick={() => setRemember(!remember)} />
              <h2 className="ml-2">Remember me</h2>
            </div>
            <Link href="#" className="hover:underline text-sky-500">Forgot password?</Link>
          </div>
          <button
            type="submit"
            className="w-full bg-indigo-600 py-2 px-4 border border-transparent rounded-md shadow-sm font-medium hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
          >
            Sign in
          </button>
          <div className="relative flex items-center">
            <div className="border-t border-gray-300 flex-grow"></div>
            <div className="mx-4 text-sm text-gray-500">Or</div>
            <div className="border-t border-gray-300 flex-grow"></div>
          </div>
          <button
            type="submit"
            className="flex items-center justify-center w-full py-2 px-4 border border-black  border-transparent rounded-md shadow-sm font-medium hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 dark:border-white"
          >
            <Image src="/icons8-google.svg" alt="google icon" width={30} height={30} className="mr-2" />
            Sign in with Google
          </button>

        </form>
      </div>
      <div className='p-10'>
        <h2>Create a Account ? <Link href={'/register'} className='hover:underline text-sky-500'>Click here</Link></h2>
      </div>

    </div>
  )
}
