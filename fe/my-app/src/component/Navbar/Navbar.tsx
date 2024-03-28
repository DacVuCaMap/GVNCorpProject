"use client"
import React from 'react'
import './navbar.css'
import { ModeToggle } from '@/components/ModeToggle'

export default function Navbar() {

  return (
    <div>
      <nav className="bg-white dark:bg-gray-950">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            {/* Logo */}
            <div className="flex-shrink-0">
              <h1 className='text-xl font-bold'>GVNCorp</h1>
            </div>
            {/* Menu */}
            <div className="hidden md:block">
              <div className="ml-10 flex items-baseline space-x-4">
                <a href="#" className=" hover:text-white">Home</a>
                <a href="#" className=" hover:text-white">About</a>
                <a href="#" className=" hover:text-white">Services</a>
                <a href="#" className=" hover:text-white">Contact</a>
              </div>
            </div>
            {/* ModeToggle outside of <a> tag */}
            <ModeToggle />
          </div>
        </div>
      </nav>
    </div>
  )
}
