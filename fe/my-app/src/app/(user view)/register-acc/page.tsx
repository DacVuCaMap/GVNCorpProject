"use client"
import { Button } from "@/components/ui/button"
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form"
import { Input } from "@/components/ui/input"
import { zodResolver } from "@hookform/resolvers/zod"
import React, { useEffect, useState } from 'react'
import { useForm } from "react-hook-form"
import { z } from "zod"
import { ChevronLeft, ChevronRight, CircleCheck } from 'lucide-react';
import Link from "next/link"
import ClipLoader from "react-spinners/ClipLoader";
import postData from "@/pattern-api/postData"
import RegisterRequest from "@/request/RegisterRequest"

const formSchema = z.object({
  username: z.string().min(2, {
    message: "Username must be at least 2 characters.",
  }).max(50, {
    message: "Username max 50 characters"
  }),
  email: z.string().email({ message: "please enter a valid email address" }),
  password: z.string().min(6, {
    message: "Password must at least 6 charaters"
  }).max(20, {
    message: "Password only contains a maximum of 20 characters "
  }).regex(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{6,}$/, {
    message: "Password must contain at least one lowercase letter, one uppercase letter, one number"
  }),
  confirmPassword: z.string(),
  phone: z.string().regex(/^\d+$/, {
    message: "Phone number must contain only digits.",
  }),
}).refine((data) => data.password === data.confirmPassword, {
  message: "Passwords don't match",
  path: ["confirmPassword"], // path of error
});

export default function page() {
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      username: "",
      email: "",
      phone: "",
      password: "",
      confirmPassword: ""
    },
  })
  const [loading, setLoading] = useState(false);
  const [done, setDone] = useState(false);
  const [nofEmail, setNofEmail] = useState("namlsnam@gmail.com");
  const [disable,setDisable] = useState(false);
  const onSubmit = async (data: any) => {
    console.log("submit");
    console.log(data);
    setLoading(true);
    let url = process.env.NEXT_PUBLIC_API_URL + "/api/auth/register";
    let requestData: RegisterRequest = new RegisterRequest(data.email, data.password, data.username, "", data.phone)
    let rs = await postData(url, requestData, null);
    setLoading(false);
    setDone(true);
    if (rs == 409) {
      form.setError("email", {
        type: "manual",
        message: "Email already exists",
      });
    }
    setNofEmail(data.email);
    setDisable(true);

  }
  return (
    <div className='flex flex-col justify-center items-center h-full dark:bg-black'>
      <div className="relative w-1/2 mx-auto p-10 rounded-lg shadow-md dark:bg-stone-950 border-2">
        <h2 className="text-2xl font-bold mb-4">Register</h2>
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="flex-col space-y-4">
            <div className="flex space-x-4">
              <div className="flex-auto space-y-5">
                <FormField
                  control={form.control}
                  name="email"
                  render={({ field }) => (
                    <FormItem className="mt-0">
                      <FormLabel>Email</FormLabel>
                      <FormControl>
                        <Input placeholder="example@email.com..." {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="password"
                  render={({ field }) => (
                    <FormItem className="mt-0">
                      <FormLabel>Password</FormLabel>
                      <FormControl>
                        <Input autoComplete="new-password" type="password" placeholder="password" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="confirmPassword"
                  render={({ field }) => (
                    <FormItem className="mt-0">
                      <FormLabel>Confirm Password</FormLabel>
                      <FormControl>
                        <Input autoComplete="new-password" type="password" placeholder="re-type password" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

              </div>

              <div className="flex-auto space-y-5">
                <FormField
                  control={form.control}
                  name="username"
                  render={({ field }) => (
                    <FormItem className="mt-0">
                      <FormLabel>User Name</FormLabel>
                      <FormControl>
                        <Input autoComplete="user-name" placeholder="John Biden..." {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="phone"
                  render={({ field }) => (
                    <FormItem className="mt-0">
                      <FormLabel>Phone Number</FormLabel>
                      <FormControl>
                        <Input placeholder="phone number" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
            </div>
            <div className="flex items-center">
              <Link href={"/login"} className="flex items-center justify-center mr-auto hover:underline text-sky-500">
                <ChevronLeft /> Back Login
              </Link>
              <Button disabled={disable} className="ml-auto transition duration-300 hover:bg-violet-600 hover:text-white" type="submit">
                Continue <ChevronRight />
              </Button>
            </div>


          </form>
        </Form>
        {loading ? <div className="absolute bg-black h-full w-full top-0 left-0 bg-opacity-80 flex justify-center items-center">
          <ClipLoader color="rgba(219, 219, 219, 1)" />
        </div> : ""}
        {done ? <div className="absolute bg-black h-full w-full top-0 left-0  flex justify-center items-center">
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
        </div> : ""}
      </div>

    </div>
  )
}
