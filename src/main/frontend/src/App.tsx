import React, {useState} from 'react';
import './App.css';
import {PAGE} from './types'
import {MainPage} from "./pages/MainPage";
import {MetersConfig} from "./pages/MetersConfig";
import {LogsPage} from "./pages/LogsPage";
import logo from './img/KOMETA_logo_negative_500.png'

function App() {
  const[page, setPage] = useState(PAGE.main)
  return (
    <div className="App text-lg font-sans text-neutral-700">
      <div className='flex flex-col mx-auto max-w-full min-h-screen'>   {/*overflow-y-scroll*/}

        <header className='bg-[#046a38]'>
          <div className='flex h-20 mx-auto xl:max-w-screen-xl '>

            <div className='flex flex-auto  box-border'>
              <div className='box-border w-48'>
                <img className='py-2.5' src={logo} alt='Kometa logo'/>
              </div>

            </div>
            <div className='flex flex-col w-4/6'>
              <div className='flex flex-auto justify-end'>
                <div className='flex-auto text-white text-4xl uppercase text-left  box-border '>
                  Meters collector
                </div>
                {/*<div className=''>Login</div>*/}
              </div>
              <div className='flex h-1/3 text-left text-white text-lg uppercase'>
                <div className='w-1/4'>
                  <a className='cursor-pointer'
                          onClick={() => setPage(PAGE.main)}
                  >Main</a>
                </div>
                <div className='w-1/4'>
                  <a className='cursor-pointer'
                          onClick={() => setPage(PAGE.config)}
                  >Settings</a>
                </div>
                <div className='w-1/4'>
                  <a className='cursor-pointer'
                          onClick={() => setPage(PAGE.logs)}
                  >Logs</a>
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
          <div className=' my-1 mx-auto xl:max-w-screen-xl text-white text-lg text-left'>
            KOMETA 99 ZRT
          </div>
        </footer>
      </div>
    </div>
  );
}

export default App;
