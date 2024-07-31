import {StompSessionProvider, useSubscription} from "react-stomp-hooks";
import {meterData} from "../types";
import {useState} from "react";

export function MainPage(){

    function getData(){
        fetch('/api/getdatatosocket').then()
    }

    return(
        <div>
            {/*<StompSessionProvider url={"http://localhost/websocket" } onConnect={getData}>*/}
            <StompSessionProvider url={"/websocket"} onConnect={getData}>
                <SubscribingComponent/>
            </StompSessionProvider>
        </div>
    )
}

function SubscribingComponent() {
    const [metersData, setMetersData] = useState<meterData[]|null>(null);

    useSubscription("/topic/meters", (message) => setMetersData(JSON.parse(message.body)));


    const getRealStr = function (data: number): string {
        if (data !== undefined) {
            return (data.toFixed(2).toString())
        } else return('0')
    }

    const getIntStr = function (data: number): string {
        if (data !== undefined) {
            return (data.toFixed(0).toString())
        } else return('0')
    }

    return (
        <div className='flex flex-wrap mx-auto py-2 xl:max-w-screen-xl'>
            {metersData &&
                metersData.map((item:meterData)=>(
                    <div key={item.id} className='accent-neutral-700 flex flex-col
                                                w-full sm:w-[49%] sm:min-w-[600px]
                                               border box-border rounded mb-3 mx-auto hover:shadow
                                               text-sm sm:text-lg '>
                        <div className={`h-1 rounded-t ${item.online ? 'bg-[#046a38]' : 'bg-[#e1251b]'}`}></div>
                        <div className='flex text-left bg-neutral-200'>
                            <div className='w-1/4 text-right'>Position:</div>
                            <div className='pl-1 font-semibold'>{item.position}</div>
                        </div>
                        <div className='flex text-left bg-neutral-200'>
                            <div className='w-1/4 text-right'>Title English:</div>
                            <div className='pl-1 font-semibold'>{item.titleEn}</div>
                        </div>
                        <div className='flex text-left bg-neutral-200'>
                            <div className='w-1/4 text-right'>Cím Magyarul:</div>
                            <div className='pl-1 font-semibold'>{item.titleHu}</div>
                        </div>

                        <div className='flex py-1'>
                            <div className='flex w-1/3 sm:w-1/2 flex-col border-r box-border'>
                                <div className='font-semibold border-b mx-1'>Power</div>
                                <div className='flex border-b mx-1'>
                                    <div className='w-1/2 text-left pl-4 hidden sm:flex'>Active</div>
                                    <div className='w-1/5 text-left pl-1 sm:hidden'>P</div>
                                    <div className='w-4/5 sm:w-1/2 text-left'>{getRealStr(item.p)} kW</div>
                                </div>
                                <div className='flex border-b mx-1'>
                                    <div className='w-1/2 text-left pl-4 hidden sm:flex'>Reactive</div>
                                    <div className='w-1/5 text-left pl-1 sm:hidden'>Q</div>
                                    <div className='w-4/5 sm:w-1/2 text-left'>{getRealStr(item.q)} kvar</div>
                                </div>
                                <div className='flex border-b mx-1'>
                                    <div className='w-1/2 text-left pl-4 hidden sm:flex'>Apparent</div>
                                    <div className='w-1/5 text-left pl-1 sm:hidden'>S</div>
                                    <div className='w-4/5 sm:w-1/2 text-left'>{getRealStr(item.s)} kVA</div>
                                </div>

                                <div className='border-b mx-1 hidden sm:flex'>
                                    <div className="w-1/12 text-right">I1</div>
                                    <div className="w-3/12 text-left">: {getIntStr(item.i1)}A</div>
                                    <div className="w-1/12 text-right">I2</div>
                                    <div className="w-3/12 text-left">: {getIntStr(item.i2)}A</div>
                                    <div className="w-1/12 text-right">I3</div>
                                    <div className="w-3/12 text-left">: {getIntStr(item.i3)}A</div>
                                </div>

                                <div className='border-b mx-1 hidden sm:flex'>
                                    <div className="w-1/12 text-right">U1</div>
                                    <div className="w-3/12 text-left">: {getIntStr(item.u1)}V</div>
                                    <div className="w-1/12 text-right">U2</div>
                                    <div className="w-3/12 text-left">: {getIntStr(item.u2)}V</div>
                                    <div className="w-1/12 text-right">U3</div>
                                    <div className="w-3/12 text-left">: {getIntStr(item.u3)}V</div>
                                </div>

                                <div className='mx-1 hidden sm:flex'>
                                    <div className="w-1/12 text-right">U12</div>
                                    <div className="w-3/12 text-left ml-2">: {getIntStr(item.u12)}V</div>
                                    <div className="w-1/12 text-right">U23</div>
                                    <div className="w-3/12 text-left ml-2">: {getIntStr(item.u23)}V</div>
                                    <div className="w-1/12 text-right">U31</div>
                                    <div className="w-3/12 text-left ml-2">: {getIntStr(item.u31)}V</div>
                                </div>

                            </div>
                            <div className='flex w-2/3 sm:w-1/2 flex-col'>
                                <div className='font-semibold border-b mx-1'>Energy</div>
                                <div className='flex border-b mx-1'>
                                    <div className='w-1/2 text-left pl-3 hidden sm:flex'>Active import</div>
                                    <div className='w-1/3 text-left pl-1 sm:hidden'>{'Active ->'}</div>
                                    <div className='w-2/3 sm:w-1/2 text-left pl-1 sm:pl-0'>{getRealStr(item.ea)} kWh</div>
                                </div>
                                <div className='flex border-b mx-1'>
                                    <div className='w-1/2 text-left pl-3 hidden sm:flex'>Active export</div>
                                    <div className='w-1/3 text-left pl-1 sm:hidden'>{'Active <-'}</div>
                                    <div className='w-2/3 sm:w-1/2 text-left pl-1 sm:pl-0'>{getRealStr(item.ead)} kWh</div>
                                </div>
                                <div className='flex border-b mx-1'>
                                    <div className='w-1/2 text-left pl-3 hidden sm:flex'>Reactive import</div>
                                    <div className='w-1/3 text-left pl-1 sm:hidden'>{'Reactive ->'}</div>
                                    <div className='w-2/3 sm:w-1/2 text-left pl-1 sm:pl-0'>{getRealStr(item.er)} kvarh</div>
                                </div>
                                <div className='flex border-b mx-1'>
                                    <div className='w-1/2 text-left pl-3 hidden sm:flex'>Reactive export</div>
                                    <div className='w-1/3 text-left pl-1 sm:hidden'>{'Reactive <-'}</div>
                                    <div className='w-2/3 sm:w-1/2 text-left pl-1 sm:pl-0'>{getRealStr(item.eg)} kvarh</div>
                                </div>
                                <div className='flex mx-1'>
                                    <div className='w-1/3 sm:w-1/2 text-left pl-1 sm:pl-3'>Apparent</div>
                                    <div className='w-2/3 sm:w-1/2 text-left  pl-1 sm:pl-0'>{getRealStr(item.es)} kVAh</div>
                                </div>

                                <div className='hidden sm:flex text-sm text-left pl-4 pt-1 text-neutral-300'>
                                    {item.id}:Polling time: {item.pollTime/1000000}s
                                </div>
                            </div>
                        </div>

                    </div>
                ))
            }
        </div>
    );
}
