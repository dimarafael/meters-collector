import React, {useState} from 'react';
import './App.css';
import {PAGE} from './types'
import {MainPage} from "./pages/MainPage";
import {MetersConfig} from "./pages/MetersConfig";
import {LogsPage} from "./pages/LogsPage";
import logo from './img/KOMETA_logo_negative_500.png'
import {AiOutlineLineChart, AiOutlineSetting} from 'react-icons/ai'
import {BsSpeedometer} from  'react-icons/bs'
import {RxActivityLog} from 'react-icons/rx'

function App() {
  const[page, setPage] = useState(PAGE.main)
  return (
    <div className="App text-lg font-sans text-neutral-700">
      <div className='flex flex-col mx-auto max-w-full min-h-screen'>   {/*overflow-y-scroll*/}

        <header className='bg-[#046a38]'>
          <div className='flex h-20 mx-auto xl:max-w-screen-xl '>

            <div className='flex flex-auto  box-border'>
              <div className='box-border w-48'>
                <img className='py-2.5 pl-1 xl:pl-0' src={logo} alt='Kometa logo'/>
              </div>

            </div>
            <div className='flex flex-col w-4/6'>
              <div className='flex flex-auto justify-end'>
                <div className='flex-auto text-white uppercase sm:text-left box-border
                                text-base sm:text-4xl pt-5 sm:pt-0'>
                  Meters collector
                </div>
                {/*<div className=''>Login</div>*/}
              </div>
              <div className='hidden sm:flex h-1/3 text-left text-white text-lg uppercase'>
                <div className='w-1/4'>
                  <a className='cursor-pointer flex'
                          onClick={() => setPage(PAGE.main)}
                  ><BsSpeedometer className='text-2xl mr-1'/>Meters</a>
                </div>
                <div className='w-1/4'>
                  <a className='cursor-pointer flex'
                     href={`${window.location.protocol}//${window.location.hostname}:3000`}
                     target="_blank"
                  ><AiOutlineLineChart className='text-3xl mr-1'/>Charts</a>
                </div>
                <div className='w-1/4'>
                  <a className='cursor-pointer flex'
                          onClick={() => setPage(PAGE.config)}
                  ><AiOutlineSetting className='text-2xl mr-1'/>Settings</a>
                </div>
                <div className='w-1/4'>
                  <a className='cursor-pointer flex'
                          onClick={() => setPage(PAGE.logs)}
                  ><RxActivityLog className='text-2xl mr-1'/>Logs</a>
                </div>
                {/*<div className='w-1/6'></div>*/}
              </div>
            </div>
          </div>
        </header>
        <div className='flex-auto '>
          {page === PAGE.main && <MainPage/>}
          {page === PAGE.config && <MetersConfig/>}
          {page === PAGE.logs && <LogsPage/>}
        </div>
        <footer className='bg-[#aaac24]'>
          <div className=' my-1 mx-auto xl:max-w-screen-xl text-white text-lg text-left pl-1'>
            KOMETA 99 ZRT
          </div>
        </footer>
      </div>
    </div>
  );
}

export default App;
